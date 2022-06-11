package pl.polsl.hdised.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.hdised.gui.DTOs.*;
import pl.polsl.hdised.gui.exceptions.DatePickerException;
import pl.polsl.hdised.gui.exceptions.WrongRequestException;

import java.util.ArrayList;

public class ViewController{
    //<editor-fold desc="Database Section Controls">
    public ChoiceBox databaseDeviceChoiceBox;
    public ChoiceBox databaseLocationChoiceBox;
    public DatePicker databaseStartDatePicker;
    public DatePicker databaseFinishDatePicker;
    public ChoiceBox databaseRequestChoiceBox;
    public Label databaseSectionErrorLabel;
    //</editor-fold>
    //<editor-fold desc="Stream Section Controls">
    public ChoiceBox streamDeviceChoiceBox;
    public ChoiceBox streamLocationChoiceBox;
    public ChoiceBox streamRequestChoiceBox;
    public Label streamSectionErrorLabel;
    //</editor-fold>
    //<editor-fold desc="Database Result Table">
    public TableView databaseResultTable;
    public TableColumn databaseResultTableStartDateColumn;
    public TableColumn databaseResultTableFinishDateColumn;
    public TableColumn databaseResultTableDeviceColumn;
    public TableColumn databaseResultTableLocationColumn;
    public TableColumn databaseResultTableValueColumn;
    //</editor-fold>
    //<editor-fold desc="Stream Result Table">
    public TableView streamResultTable;
    //</editor-fold>

    OperationController operationController = new OperationController();

    public void initialize(){
        operationController.getAllDevicesAndAllLocationsFromDatabase();
        fillAllChoiceBoxes();
    }
    private void fillAllChoiceBoxes() {
        ObservableList<String> observableDeviceList = FXCollections.observableArrayList(operationController.getDevices());
        ObservableList<String> observableLocationList = FXCollections.observableArrayList(operationController.getLocations());

        databaseDeviceChoiceBox.setItems(observableDeviceList);
        streamDeviceChoiceBox.setItems(observableDeviceList);
        databaseLocationChoiceBox.setItems(observableLocationList);
        streamLocationChoiceBox.setItems(observableLocationList);

        databaseDeviceChoiceBox.setValue(databaseDeviceChoiceBox.getItems().get(0));
        streamDeviceChoiceBox.setValue(streamDeviceChoiceBox.getItems().get(0));
        databaseLocationChoiceBox.setValue(databaseLocationChoiceBox.getItems().get(0));
        streamLocationChoiceBox.setValue(streamLocationChoiceBox.getItems().get(0));
    }

    public void sendStreamRequestClicked(ActionEvent actionEvent) {
        try {
            sendRequestWithDataToStream(getRequestFromChoiceBox(streamRequestChoiceBox),getStreamRequestDTOFromJavaFXControls());
        } catch (WrongRequestException e) {
            throw new RuntimeException(e);
        }
    }

    private StreamRequestDTO getStreamRequestDTOFromJavaFXControls() {
        HideStreamSectionErrorLabel();
        StreamRequestDTO result = new StreamRequestDTO();
        result.setDevice(streamDeviceChoiceBox.getValue().toString());
        result.setLocation(streamLocationChoiceBox.getValue().toString());
        return result;
    }

    public void sendDatabaseRequestClicked(ActionEvent actionEvent) {
        HideDatabaseSectionErrorLabel();
        try {
            sendRequestWithDataToDatabaseAndHandleResponse(getRequestFromChoiceBox(databaseRequestChoiceBox),getDatabaseRequestDTOFromJavaFXControls());
        } catch (WrongRequestException e) {
            throw new RuntimeException(e);
        } catch (DatePickerException e) {
            UpdateAndShowDatabaseSectionErrorLabel(e.getMessage());
        }
    }

    private void UpdateAndShowDatabaseSectionErrorLabel(String message) {
        databaseSectionErrorLabel.setText(message);
        databaseSectionErrorLabel.setVisible(true);
    }
    private void HideDatabaseSectionErrorLabel(){
        databaseSectionErrorLabel.setVisible(false);
    }
    private void UpdateAndShowStreamSectionErrorLabel(String message) {
        streamSectionErrorLabel.setText(message);
        streamSectionErrorLabel.setVisible(true);
    }
    private void HideStreamSectionErrorLabel(){
        streamSectionErrorLabel.setVisible(false);
    }

    private DatabaseRequestDTO getDatabaseRequestDTOFromJavaFXControls() throws DatePickerException {
        DatabaseRequestDTO result = new DatabaseRequestDTO();

        result.setDevice(databaseDeviceChoiceBox.getValue().toString());
        result.setLocation(databaseLocationChoiceBox.getValue().toString());

        if(databaseStartDatePicker.getValue() == null) throw new DatePickerException("Start date not provided.");
        if(databaseFinishDatePicker.getValue() == null) throw new DatePickerException("Finish date not provided.");
        if(databaseStartDatePicker.getValue().compareTo(databaseFinishDatePicker.getValue()) > 0)
            throw new DatePickerException("Finish date is chronologically earlier than start date.");

        result.setStartDate(databaseStartDatePicker.getValue().toString());
        result.setFinishDate(databaseFinishDatePicker.getValue().toString());

        return result;
    }

    private void sendRequestWithDataToDatabaseAndHandleResponse(String request, DatabaseRequestDTO data) throws WrongRequestException {
        if(request.equals("all")) {
            MultipleValueDatabaseResponseDTO response = operationController.getAllTemperaturesFromDatabase(
                    data.getDevice(),
                    data.getLocation(),
                    data.getStartDate(),
                    data.getFinishDate()
            );
            response.getValues().forEach(r ->{
                r.setDevice(data.getDevice());
                r.setLocation(data.getLocation());
            });
            HandleMultipleValueResponseFromDatabase(response, request);
        }else {
            SingleValueDatabaseResponseDTO response = new SingleValueDatabaseResponseDTO(
                    data.getDevice(),
                    data.getLocation(),
                    data.getStartDate(),
                    data.getFinishDate(),
                    ""
            );
            switch (request) {
                case "average" -> response.setValue(operationController.getAverageTemperatureFromDatabase(
                        data.getDevice(),
                        data.getLocation(),
                        data.getStartDate(),
                        data.getFinishDate()
                ).toString());
                case "minimal" -> response.setValue(operationController.getMinimalTemperatureFromDatabase(
                        data.getDevice(),
                        data.getLocation(),
                        data.getStartDate(),
                        data.getFinishDate()
                ).toString());
                case "maximal" -> response.setValue(operationController.getMaximalTemperatureFromDatabase(
                        data.getDevice(),
                        data.getLocation(),
                        data.getStartDate(),
                        data.getFinishDate()
                ).toString());
                default -> throw new WrongRequestException("Unknown request: " + request);
            }
            HandleSingeValueResponseFromDatabase(response, request);
        }
    }

    private void HandleSingeValueResponseFromDatabase(SingleValueDatabaseResponseDTO response, String valueType) {
        initializeDatabaseResultTableForSingleValueResponse(valueType);
        fillDatabaseResultTableWithSingleValueResponse(response);
    }

    private void fillDatabaseResultTableWithSingleValueResponse(SingleValueDatabaseResponseDTO response) {
        ObservableList<SingleValueDatabaseResponseDTO> resultRowObservableList = FXCollections.observableArrayList();
        resultRowObservableList.add(response);
        ((TableColumn<SingleValueDatabaseResponseDTO, String>)databaseResultTable.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("startDate"));
        ((TableColumn<SingleValueDatabaseResponseDTO, String>)databaseResultTable.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("finishDate"));
        ((TableColumn<SingleValueDatabaseResponseDTO, String>)databaseResultTable.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("device"));
        ((TableColumn<SingleValueDatabaseResponseDTO, String>)databaseResultTable.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<>("location"));
        ((TableColumn<SingleValueDatabaseResponseDTO, String>)databaseResultTable.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<>("value"));
        databaseResultTable.setItems(resultRowObservableList);
    }

    private void initializeDatabaseResultTableForSingleValueResponse(String valueType) {
        databaseResultTable.getColumns().clear();
        ArrayList<TableColumn<SingleValueDatabaseResponseDTO, String>> columnDefinitions = new ArrayList<>();
        columnDefinitions.add(new TableColumn<>("Start Date"));
        columnDefinitions.add(new TableColumn<>("Finish Date"));
        columnDefinitions.add(new TableColumn<>("Device"));
        columnDefinitions.add(new TableColumn<>("Location"));
        columnDefinitions.add(new TableColumn<>(valueType.substring(0, 1).toUpperCase() + valueType.substring(1)));
        databaseResultTable.getColumns().addAll(columnDefinitions);
    }

    private void HandleMultipleValueResponseFromDatabase(MultipleValueDatabaseResponseDTO response, String request) {
        initializeDatabaseResultTableForMultipleValueResponse();
        fillDatabaseResultTableWithMultipleValueResponse(response, request);
    }

    private void fillDatabaseResultTableWithMultipleValueResponse(MultipleValueDatabaseResponseDTO response, String request) {

        ObservableList<TemperatureResponseDTO> resultRowObservableList = FXCollections.observableArrayList();
        resultRowObservableList.addAll(response.getValues());
        ((TableColumn<MultipleValueDatabaseResponseDTO, String>)databaseResultTable.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        ((TableColumn<MultipleValueDatabaseResponseDTO, String>)databaseResultTable.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("device"));
        ((TableColumn<MultipleValueDatabaseResponseDTO, String>)databaseResultTable.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("location"));
        ((TableColumn<MultipleValueDatabaseResponseDTO, String>)databaseResultTable.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<>("temperature"));
        databaseResultTable.setItems(resultRowObservableList);
    }

    private void initializeDatabaseResultTableForMultipleValueResponse() {
        databaseResultTable.getColumns().clear();
        ArrayList<TableColumn<TemperatureResponseDTO, String>> columnDefinitions = new ArrayList<>();
        columnDefinitions.add(new TableColumn<>("Date and Time"));
        columnDefinitions.add(new TableColumn<>("Device"));
        columnDefinitions.add(new TableColumn<>("Location"));
        columnDefinitions.add(new TableColumn<>("Temperature"));
        databaseResultTable.getColumns().addAll(columnDefinitions);
    }

    private void sendRequestWithDataToStream(String request, StreamRequestDTO data) throws WrongRequestException {
        switch (request) {
            case "all" -> operationController.getAllTemperaturesFromStream(
                    data.getDevice(),
                    data.getLocation()
            );
            case "average" -> operationController.getAverageTemperatureFromStream(
                    data.getDevice(),
                    data.getLocation()
            );
            case "minimal" -> operationController.getMinimalTemperatureFromStream(
                    data.getDevice(),
                    data.getLocation()
            );
            case "maximal" -> operationController.getMaximalTemperatureFromStream(
                    data.getDevice(),
                    data.getLocation()
            );
            default -> throw new WrongRequestException("Unknown request: " + request);
        }

    }

    private String getRequestFromChoiceBox(ChoiceBox requestChoiceBox) throws WrongRequestException {
        String request = "";
        if(requestChoiceBox.getValue().toString().contains("all")) request = "all";
        else if(requestChoiceBox.getValue().toString().contains("average")) request = "average";
        else if(requestChoiceBox.getValue().toString().contains("minimal")) request = "minimal";
        else if(requestChoiceBox.getValue().toString().contains("maximal")) request = "maximal";
        else throw new WrongRequestException("Unknown request: " + request);
        return request;
    }
}