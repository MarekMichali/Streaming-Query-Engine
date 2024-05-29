package main

import (
	"context"
	"encoding/json"
	"fmt"
	"time"

	kafka "github.com/segmentio/kafka-go"
)

type MeasurementDto struct {
	CityName    string    `json:"cityName"`
	DeviceId    string    `json:"deviceId"`
	Date        time.Time `json:"date"`
	Unit        string    `json:"unit"`
	Temperature float64   `json:"temperature"`
}

func newKafkaWriter(kafkaURL, topic string) *kafka.Writer {
	return &kafka.Writer{
		Addr:     kafka.TCP(kafkaURL),
		Topic:    topic,
		Balancer: &kafka.LeastBytes{},
	}
}

func main() {
	// get kafka writer using environment variables.
	//kafkaURL := os.Getenv("kafkaURL")
	//topic := os.Getenv("topic")
	time.Sleep(60 * time.Second)
	writer := newKafkaWriter("kafka:9092", "topic")
	defer writer.Close()
	fmt.Println("start producing ... !!")
	measurement := MeasurementDto{
		CityName:    "Gliwice",
		DeviceId:    "dev01",
		Date:        time.Now(),
		Unit:        "Celsius",
		Temperature: 25.0,
	}

	jsonData, err := json.Marshal(measurement)
	if err != nil {
		fmt.Println(err)
		return
	}
	measurementByte := []byte(jsonData)
	for i := 0; ; i++ {
		key := fmt.Sprintf("Key-%d", i)
		msg := kafka.Message{
			Value: []byte(measurementByte),
		}
		err := writer.WriteMessages(context.Background(), msg)
		if err != nil {
			fmt.Println(err)
		} else {
			fmt.Println("produced", key)
		}
		time.Sleep(1 * time.Second)
	}
}
