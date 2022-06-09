package pl.polsl.hdised.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ViewController {

    OperationController operationController = new OperationController();

    public ChoiceBox databaseDeviceChoiceBox;
    public ChoiceBox databaseLocationChoiceBox;
    public DatePicker minDatePicker;
    public DatePicker maxDatePicker;
    public ChoiceBox streamDeviceChoiceBox;
    public ChoiceBox streamLocationChoiceBox;

    private double averageTemperatureFromDatabase;
    private double minimalTemperatureFromDatabase;
    private double maximalTemperatureFromDatabase;
    private ArrayList<Double> allTemperaturesFromDatabase;
    private double averageTemperatureFromStream;
    private double minimalTemperatureFromStream;
    private double maximalTemperatureFromStream;
    private ArrayList<Double> allTemperaturesFromStream;

    public void databaseGetAverageClicked(ActionEvent actionEvent) {
        averageTemperatureFromDatabase = operationController.getAverageTemperatureFromDatabase(
                databaseDeviceChoiceBox.getValue().toString(),
                databaseLocationChoiceBox.getValue().toString(),
                minDatePicker.getValue(),
                maxDatePicker.getValue());
    }

    public void databaseGetMinimalClicked(ActionEvent actionEvent) {
        minimalTemperatureFromDatabase = operationController.getMinimalTemperatureFromDatabase(
                databaseDeviceChoiceBox.getValue().toString(),
                databaseLocationChoiceBox.getValue().toString(),
                minDatePicker.getValue(),
                maxDatePicker.getValue());
    }

    public void databaseGetMaximalClicked(ActionEvent actionEvent) {
        maximalTemperatureFromDatabase = maximalTemperatureFromDatabase = operationController.getMaximalTemperatureFromDatabase(
                databaseDeviceChoiceBox.getValue().toString(),
                databaseLocationChoiceBox.getValue().toString(),
                minDatePicker.getValue(),
                maxDatePicker.getValue());
    }

    public void databaseGetAllClicked(ActionEvent actionEvent) {
        allTemperaturesFromDatabase = allTemperaturesFromDatabase = operationController.getAllTemperaturesFromDatabase(
                databaseDeviceChoiceBox.getValue().toString(),
                databaseLocationChoiceBox.getValue().toString(),
                minDatePicker.getValue(),
                maxDatePicker.getValue());
    }

    public void streamGetAverageClicked(ActionEvent actionEvent) {
        averageTemperatureFromStream = operationController.getAverageTemperatureFromStream(
                databaseDeviceChoiceBox.getValue().toString(),
                databaseLocationChoiceBox.getValue().toString(),
                minDatePicker.getValue(),
                maxDatePicker.getValue());
    }

    public void streamGetMinimalClicked(ActionEvent actionEvent) {
        minimalTemperatureFromStream = operationController.getMinimalTemperatureFromStream(
                databaseDeviceChoiceBox.getValue().toString(),
                databaseLocationChoiceBox.getValue().toString(),
                minDatePicker.getValue(),
                maxDatePicker.getValue());
    }

    public void streamGetMaximalClicked(ActionEvent actionEvent) {
        maximalTemperatureFromStream = operationController.getMaximalTemperatureFromStream(
                databaseDeviceChoiceBox.getValue().toString(),
                databaseLocationChoiceBox.getValue().toString(),
                minDatePicker.getValue(),
                maxDatePicker.getValue());
    }

    public void streamGetAllClicked(ActionEvent actionEvent) {
        allTemperaturesFromStream = operationController.getAllTemperaturesFromStream(
                databaseDeviceChoiceBox.getValue().toString(),
                databaseLocationChoiceBox.getValue().toString(),
                minDatePicker.getValue(),
                maxDatePicker.getValue());
    }

    public void fillAllChoiceBoxes(MouseEvent mouseEvent) {
        ObservableList<String> observableDeviceList = FXCollections.observableArrayList(operationController.getAllDevices());
        ObservableList<String> observableLocationList = FXCollections.observableArrayList(operationController.getAllLocations());

        databaseDeviceChoiceBox.setItems(observableDeviceList);
        streamDeviceChoiceBox.setItems(observableDeviceList);
        databaseLocationChoiceBox.setItems(observableLocationList);
        streamLocationChoiceBox.setItems(observableLocationList);
    }
}