package main

import (
	"net/http"

	"github.com/gorilla/mux"
)

type MeasurementController struct {
	MeasurementService *MeasurementService
}

func NewMeasurementController(service *MeasurementService) *MeasurementController {
	return &MeasurementController{
		MeasurementService: service,
	}
}

func (controller *MeasurementController) StartRouter() {
	router := mux.NewRouter()
	router.HandleFunc("/api/v1/measurements/stop-producing", controller.StopProducing).Methods("POST")
	router.HandleFunc("/api/v1/measurements/start-producing-to-database", controller.StartProducingToDatabase).Methods("POST")
	router.HandleFunc("/api/v1/measurements/start-stream-producing", controller.StartStreamProducing).Methods("POST")
	http.ListenAndServe(":8085", router)
}

func (controller *MeasurementController) StopProducing(w http.ResponseWriter, r *http.Request) {
	controller.MeasurementService.StopProducing()
	w.WriteHeader(http.StatusOK)
	w.Write([]byte("Producing stopped"))
}

func (controller *MeasurementController) StartProducingToDatabase(w http.ResponseWriter, r *http.Request) {
	controller.MeasurementService.StartProducing("topic")
	w.WriteHeader(http.StatusOK)
	w.Write([]byte("Producing started"))
}

func (controller *MeasurementController) StartStreamProducing(w http.ResponseWriter, r *http.Request) {
	controller.MeasurementService.StartProducing("streamTopic")
	w.WriteHeader(http.StatusOK)
	w.Write([]byte("Producing started"))
}
