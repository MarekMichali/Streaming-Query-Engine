package main

type MeasurementService struct {
	MeasurementCreator *MeasurementCreator
}

func NewMeasurementService(creator *MeasurementCreator) *MeasurementService {
	return &MeasurementService{
		MeasurementCreator: creator,
	}
}

func (ms *MeasurementService) StartProducing(topic string) {
	ms.MeasurementCreator.SetProduce(true)
	go func() {
		ms.MeasurementCreator.StartProducingMeasurementsToDatabase(topic)
	}()
}

func (ms *MeasurementService) StopProducing() {
	ms.MeasurementCreator.SetProduce(false)
}
