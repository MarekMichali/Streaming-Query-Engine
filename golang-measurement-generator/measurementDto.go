package main

import "time"

type MeasurementDto struct {
	CityName    string    `json:"cityName"`
	DeviceId    string    `json:"deviceId"`
	Date        time.Time `json:"date"`
	Unit        string    `json:"unit"`
	Temperature float64   `json:"temperature"`
}

func NewMeasurementDto(cityName string, deviceId string, date time.Time, unit string, temperature float64) *MeasurementDto {
	return &MeasurementDto{
		CityName:    cityName,
		DeviceId:    deviceId,
		Date:        date,
		Unit:        unit,
		Temperature: temperature,
	}
}
