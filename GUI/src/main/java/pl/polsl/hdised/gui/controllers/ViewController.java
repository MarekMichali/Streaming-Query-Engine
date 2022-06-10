package pl.polsl.hdised.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViewController{

    OperationController operationController = new OperationController();

    //<editor-fold desc="FXML">
    public Button databaseGetAverageButton;
    public Button databaseGetMinimalButton;
    public Button databaseGetMaximalButton;
    public Button databaseGetAllButton;
    public Button streamGetAverageButton;
    public Button streamGetMinimalButton;
    public Button streamGetMaximalButton;
    public Button streamGetAllButton;
    public ChoiceBox databaseDeviceChoiceBox;
    public ChoiceBox databaseLocationChoiceBox;
    public ChoiceBox streamDeviceChoiceBox;
    public ChoiceBox streamLocationChoiceBox;
    public DatePicker startDatePicker;
    public DatePicker finishDatePicker;
    //</editor-fold>

    private double averageTemperatureFromDatabase;
    private double minimalTemperatureFromDatabase;
    private double maximalTemperatureFromDatabase;
    private ArrayList<Double> allTemperaturesFromDatabase;

    private double averageTemperatureFromStream;
    private double minimalTemperatureFromStream;
    private double maximalTemperatureFromStream;
    private ArrayList<Double> allTemperaturesFromStream;

    public void initialize(){
        if(operationController.isConnectionGood()) {
            operationController.getAllDevicesAndAllLocationsFromDatabase();
            fillAllChoiceBoxes();
        }else{
            closeStageAndShowError();
        }
    }

    private void closeStageAndShowError() {

    }

    public void getValueFromDatabase(ActionEvent actionEvent) {
        String startDateString = getFormattedStringFromLocalDate(startDatePicker.getValue());
        String finishDateString = getFormattedStringFromLocalDate(finishDatePicker.getValue());

        Button source = (Button) actionEvent.getSource();
        if (databaseGetAverageButton.equals(source)) {
            averageTemperatureFromDatabase = operationController.getAverageTemperatureFromDatabase(
                    databaseDeviceChoiceBox.getValue().toString(),
                    databaseLocationChoiceBox.getValue().toString(),
                    startDateString,
                    finishDateString);
        } else if (databaseGetMinimalButton.equals(source)) {
            minimalTemperatureFromDatabase = operationController.getMinimalTemperatureFromDatabase(
                    databaseDeviceChoiceBox.getValue().toString(),
                    databaseLocationChoiceBox.getValue().toString(),
                    startDateString,
                    finishDateString);
        } else if (databaseGetMaximalButton.equals(source)) {
            maximalTemperatureFromDatabase = operationController.getMaximalTemperatureFromDatabase(
                    databaseDeviceChoiceBox.getValue().toString(),
                    databaseLocationChoiceBox.getValue().toString(),
                    startDateString,
                    finishDateString);
        } else if (databaseGetAllButton.equals(source)) {
            allTemperaturesFromDatabase = operationController.getAllTemperaturesFromDatabase(
                    databaseDeviceChoiceBox.getValue().toString(),
                    databaseLocationChoiceBox.getValue().toString(),
                    startDateString,
                    finishDateString);
        }
    }

    public void getValueFromStream(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        if (streamGetAverageButton.equals(source)) {
            averageTemperatureFromStream = operationController.getAverageTemperatureFromStream(
                    streamDeviceChoiceBox.getValue().toString(),
                    streamLocationChoiceBox.getValue().toString());
        } else if (streamGetMinimalButton.equals(source)) {
            minimalTemperatureFromStream = operationController.getMinimalTemperatureFromStream(
                    streamDeviceChoiceBox.getValue().toString(),
                    streamLocationChoiceBox.getValue().toString());
        } else if (streamGetMaximalButton.equals(source)) {
            maximalTemperatureFromStream = operationController.getMaximalTemperatureFromStream(
                    streamDeviceChoiceBox.getValue().toString(),
                    streamLocationChoiceBox.getValue().toString());
        } else if (streamGetAllButton.equals(source)) {
            allTemperaturesFromStream = operationController.getAllTemperaturesFromStream(
                    streamDeviceChoiceBox.getValue().toString(),
                    streamLocationChoiceBox.getValue().toString());
        }
    }

    private String getFormattedStringFromLocalDate(LocalDate localDate) {
        StringBuilder stringBuilder = new StringBuilder(localDate.toString());
        stringBuilder.append("%2000:00");
        return stringBuilder.toString();
    }

    private void fillAllChoiceBoxes() {
        ObservableList<String> observableDeviceList = FXCollections.observableArrayList(operationController.getAllDevices());
        ObservableList<String> observableLocationList = FXCollections.observableArrayList(operationController.getAllLocations());

        databaseDeviceChoiceBox.setItems(observableDeviceList);
        streamDeviceChoiceBox.setItems(observableDeviceList);
        databaseLocationChoiceBox.setItems(observableLocationList);
        streamLocationChoiceBox.setItems(observableLocationList);

        databaseDeviceChoiceBox.setValue(databaseDeviceChoiceBox.getItems().get(0));
        streamDeviceChoiceBox.setValue(streamDeviceChoiceBox.getItems().get(0));
        databaseLocationChoiceBox.setValue(databaseLocationChoiceBox.getItems().get(0));
        streamLocationChoiceBox.setValue(streamLocationChoiceBox.getItems().get(0));
    }
}