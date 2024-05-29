package main

import (
	"fmt"
	"os"

	kafka "github.com/segmentio/kafka-go"
)

func newKafkaWriter(kafkaURL string) *kafka.Writer {
	return &kafka.Writer{
		Addr: kafka.TCP(kafkaURL),
	}
}

func main() {
	kafkaURL := os.Getenv("KAFKA_SERVER")
	fmt.Println("Listening on port 8085")
	writer := newKafkaWriter(kafkaURL)
	defer writer.Close()

	service := NewMeasurementService(NewMeasurementCreator(writer))
	controller := NewMeasurementController(service)
	controller.StartRouter()
}
