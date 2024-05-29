package main

import (
	"fmt"

	kafka "github.com/segmentio/kafka-go"
)

func newKafkaWriter(kafkaURL string) *kafka.Writer {
	return &kafka.Writer{
		Addr: kafka.TCP(kafkaURL),
	}
}

func main() {
	fmt.Println("Listening on port 8085")
	writer := newKafkaWriter("kafka:9092")
	defer writer.Close()

	service := NewMeasurementService(NewMeasurementCreator(writer))
	controller := NewMeasurementController(service)
	controller.StartRouter()
}
