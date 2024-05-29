package main

import (
	"context"
	"encoding/json"
	"fmt"
	"math/rand"
	"time"

	kafka "github.com/segmentio/kafka-go"
)

type MeasurementCreator struct {
	Produce bool
	Writer  *kafka.Writer
}

func NewMeasurementCreator(writer *kafka.Writer) *MeasurementCreator {
	return &MeasurementCreator{
		Produce: true,
		Writer:  writer,
	}
}

func (mc *MeasurementCreator) SetProduce(produce bool) {
	mc.Produce = produce
}

func (mc *MeasurementCreator) StartProducingMeasurementsToDatabase(topic string) {
	mc.Writer.Topic = topic
	for mc.Produce {
		measurementDto := mc.createMeasurement()
		fmt.Println(measurementDto.CityName)
		fmt.Println(measurementDto.DeviceId)
		fmt.Println(measurementDto.Date)
		fmt.Println(measurementDto.Unit)
		fmt.Println(measurementDto.Temperature)
		fmt.Println("----------------------------")

		jsonData, err := json.Marshal(measurementDto)
		if err != nil {
			fmt.Println(err)
			return
		}
		measurementByte := []byte(jsonData)
		msg := kafka.Message{
			Value: []byte(measurementByte),
		}
		err = mc.Writer.WriteMessages(context.Background(), msg)
		if err != nil {
			fmt.Println(err)
		}
		time.Sleep(500 * time.Millisecond)
	}
}

func (mc *MeasurementCreator) createMeasurement() *MeasurementDto {
	var city string
	switch rand.Intn(3) {
	case 0:
		city = "Gliwice"
	case 1:
		city = "Katowice"
	case 2:
		city = "Warszawa"
	default:
		city = "Wroclaw"
	}
	var deviceClass string
	switch rand.Intn(3) {
	case 0:
		deviceClass = "dev01"
	case 1:
		deviceClass = "dev02"
	case 2:
		deviceClass = "dev03"
	default:
		deviceClass = "dev04"
	}
	temperature := rand.Float64()*60 - 30
	return NewMeasurementDto(city, deviceClass, time.Now(), "Celsius", temperature)
}
