package pl.polsl.hdised.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.hdised.gui.DTOs.*;
import pl.polsl.hdised.gui.exceptions.DatePickerException;
import pl.polsl.hdised.gui.exceptions.WrongRequestException;
import pl.polsl.hdised.gui.services.DatabaseService;
import pl.polsl.hdised.gui.services.StreamService;

import java.io.IOException;
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
    public ChoiceBox streamRefreshRateChoiceBox;
    //</editor-fold>
    boolean stopClicked = false;
    Thread streamListener;


    public void initialize(){
        fillAllChoiceBoxes();
    }
    private void fillAllChoiceBoxes() {
        ArrayList<String> deviceList = DatabaseService.getAllDevicesFromDatabase();
        ArrayList<String> locationList = DatabaseService.getAllLocationsFromDatabase();
        ObservableList<String> observableDeviceList = FXCollections.observableArrayList(deviceList);
        ObservableList<String> observableLocationList = FXCollections.observableArrayList(locationList);

        databaseDeviceChoiceBox.setItems(observableDeviceList);
        streamDeviceChoiceBox.setItems(observableDeviceList);
        databaseLocationChoiceBox.setItems(observableLocationList);
        streamLocationChoiceBox.setItems(observableLocationList);

        databaseDeviceChoiceBox.setValue(databaseDeviceChoiceBox.getItems().get(0));
        streamDeviceChoiceBox.setValue(streamDeviceChoiceBox.getItems().get(0));
        databaseLocationChoiceBox.setValue(databaseLocationChoiceBox.getItems().get(0));
        streamLocationChoiceBox.setValue(streamLocationChoiceBox.getItems().get(0));
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
    private void HideDatabaseSectionErrorLabel(){
        databaseSectionErrorLabel.setVisible(false);
    }
    private void UpdateAndShowDatabaseSectionErrorLabel(String message) {
        databaseSectionErrorLabel.setText(message);
        databaseSectionErrorLabel.setVisible(true);
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
            ArrayList<TemperatureResponseDTO> response = DatabaseService.getAllTemperaturesFromDatabase(
                    data.getDevice(),
                    data.getLocation(),
                    data.getStartDate(),
                    data.getFinishDate()
            );
            response.forEach(r ->{
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
                case "average" -> response.setValue(DatabaseService.getAverageTemperatureFromDatabase(
                        data.getDevice(),
                        data.getLocation(),
                        data.getStartDate(),
                        data.getFinishDate()
                ));
                case "minimal" -> response.setValue(DatabaseService.getMinimalTemperatureFromDatabase(
                        data.getDevice(),
                        data.getLocation(),
                        data.getStartDate(),
                        data.getFinishDate()
                ));
                case "maximal" -> response.setValue(DatabaseService.getMaximalTemperatureFromDatabase(
                        data.getDevice(),
                        data.getLocation(),
                        data.getStartDate(),
                        data.getFinishDate()
                ));
                default -> throw new WrongRequestException("Unknown request: " + request);
            }
            HandleSingeValueResponseFromDatabase(response, request);
        }
    }
    private void HandleMultipleValueResponseFromDatabase(ArrayList<TemperatureResponseDTO> response, String request) {
        initializeResultTableForMultipleValueResponse(databaseResultTable);
        addMultipleValueResponseToResultTable(databaseResultTable, response);
    }
    private void HandleSingeValueResponseFromDatabase(SingleValueDatabaseResponseDTO response, String valueType) {
        initializeDatabaseResultTableForSingleValueResponse(valueType);
        fillDatabaseResultTableWithSingleValueResponse(response);
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

    public void setQueryParametersClicked(ActionEvent actionEvent) {
        streamResultTable.getItems().clear();
        StreamRequestDTO data = getStreamRequestDTOFromJavaFXControls();
        try {
            StreamService.setStreamQueryParameters(data.getDevice(),data.getLocation());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendStreamRequestClicked(ActionEvent actionEvent) {
        stopClicked=false;
        StreamRequestDTO data = getStreamRequestDTOFromJavaFXControls();
        try {
            sendRequestWithDataToStreamAndHandleResponse(
                    getRequestFromChoiceBox(streamRequestChoiceBox),
                    data);
        } catch (WrongRequestException e) {
            throw new RuntimeException(e);
        }
    }
    private void HideStreamSectionErrorLabel(){
        streamSectionErrorLabel.setVisible(false);
    }
    private void UpdateAndShowStreamSectionErrorLabel(String message) {
        streamSectionErrorLabel.setText(message);
        streamSectionErrorLabel.setVisible(true);
    }
    private StreamRequestDTO getStreamRequestDTOFromJavaFXControls() {
        HideStreamSectionErrorLabel();
        StreamRequestDTO result = new StreamRequestDTO();
        result.setDevice(streamDeviceChoiceBox.getValue().toString());
        result.setLocation(streamLocationChoiceBox.getValue().toString());
        return result;
    }
    private void sendRequestWithDataToStreamAndHandleResponse(String request, StreamRequestDTO data) throws WrongRequestException {
        if(request.equals("all")) {
            initializeResultTableForMultipleValueResponse(streamResultTable);
            streamListener = getMultipleValueStreamListener(data);
            streamListener.start();
        }else {
            initializeStreamResultTableForSingleValueResponse(request);
            Thread taskThread = getSingleValueThreadListener(request, data);
            taskThread.start();
        }
    }

    private Thread getMultipleValueStreamListener(StreamRequestDTO data) {
        return new Thread(() -> {
            while (!stopClicked) {
                try {
                    Thread.sleep(getRefreshRateInMillisecondsFromJavaFXControls());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<TemperatureResponseDTO> response = StreamService.getAllTemperaturesFromStream();
                        response.forEach(r -> {
                            r.setDevice(data.getDevice());
                            r.setLocation(data.getLocation());
                        });
                        addMultipleValueResponseToResultTable(streamResultTable, response);
                    }
                });
            }
        });
    }

    private Thread getSingleValueThreadListener(String value, StreamRequestDTO data) {
        return new Thread(() -> {
            while (!stopClicked) {
                try {
                    Thread.sleep(getRefreshRateInMillisecondsFromJavaFXControls());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        SingleValueStreamResponseDTO response = new SingleValueStreamResponseDTO(
                                data.getDevice(),
                                data.getLocation(),
                                ""
                        );
                        switch (value) {
                            case "average" -> response.setValue(StreamService.getAverageTemperatureFromStream());
                            case "minimal" -> response.setValue(StreamService.getMinimalTemperatureFromStream());
                            case "maximal" -> response.setValue(StreamService.getMaximalTemperatureFromStream());
                        }
                        fillStreamResultTableWithSingleValueResponse(response);
                    }
                });
            }
        });
    }

    private long getRefreshRateInMillisecondsFromJavaFXControls() {
        String choiceBoxValue = (String) streamRefreshRateChoiceBox.getValue();
        return switch (choiceBoxValue) {
            case "100ms" -> 100;
            case "500ms" -> 500;
            case "2s" -> 2000;
            case "5s" -> 5000;
            default -> 1000;
        };
    }

    private void initializeStreamResultTableForSingleValueResponse(String valueType) {
        streamResultTable.getColumns().clear();
        ArrayList<TableColumn<SingleValueDatabaseResponseDTO, String>> columnDefinitions = new ArrayList<>();
        columnDefinitions.add(new TableColumn<>("Device"));
        columnDefinitions.add(new TableColumn<>("Location"));
        columnDefinitions.add(new TableColumn<>(valueType.substring(0, 1).toUpperCase() + valueType.substring(1)));
        streamResultTable.getColumns().addAll(columnDefinitions);
    }
    private void fillStreamResultTableWithSingleValueResponse(SingleValueStreamResponseDTO response) {
        try{Double.parseDouble(response.getValue());}catch(NumberFormatException e){
            response.setValue("empty");
        }
        ObservableList<SingleValueStreamResponseDTO> resultRowObservableList = FXCollections.observableArrayList();
        resultRowObservableList.add(response);
        ((TableColumn<SingleValueDatabaseResponseDTO, String>)streamResultTable.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("device"));
        ((TableColumn<SingleValueDatabaseResponseDTO, String>)streamResultTable.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("location"));
        ((TableColumn<SingleValueDatabaseResponseDTO, String>)streamResultTable.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("value"));
        streamResultTable.setItems(resultRowObservableList);
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
    private void initializeResultTableForMultipleValueResponse(TableView table) {
        table.getColumns().clear();
        ArrayList<TableColumn<TemperatureResponseDTO, String>> columnDefinitions = new ArrayList<>();
        columnDefinitions.add(new TableColumn<>("Date and Time"));
        columnDefinitions.add(new TableColumn<>("Device"));
        columnDefinitions.add(new TableColumn<>("Location"));
        columnDefinitions.add(new TableColumn<>("Temperature"));
        table.getColumns().addAll(columnDefinitions);
    }
    private void addMultipleValueResponseToResultTable(TableView table, ArrayList<TemperatureResponseDTO> response) {
        ObservableList<TemperatureResponseDTO> resultRowObservableList = FXCollections.observableArrayList();
        resultRowObservableList.addAll(response);
        ((TableColumn<TemperatureResponseDTO, String>)table.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        ((TableColumn<TemperatureResponseDTO, String>)table.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("device"));
        ((TableColumn<TemperatureResponseDTO, String>)table.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("location"));
        ((TableColumn<TemperatureResponseDTO, String>)table.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<>("temperature"));
        table.getItems().addAll(resultRowObservableList);
        table.scrollTo(table.getItems().size()-1);
    }

    public void stopClicked(ActionEvent actionEvent) {
        stopClicked = true;
    }
}