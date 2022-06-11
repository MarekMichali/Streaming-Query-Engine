package pl.polsl.hdised.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import pl.polsl.hdised.gui.controllers.OperationController;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        OperationController operationController = new OperationController();
        if(operationController.isConnectionGood()){
            showStage(stage);
        }else{
            showConnectionErrorAlert();
        }
    }

    private void showConnectionErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR,"Error connecting to the database.\nCheck server status and try again.");
        alert.setTitle("Error");
        alert.setHeaderText("");
        alert.show();
    }

    private void showStage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
        stage.setTitle("Stream Query Engine");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}