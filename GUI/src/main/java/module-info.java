module pl.polsl.hdised.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.polsl.hdised.gui to javafx.fxml;
    exports pl.polsl.hdised.gui;
    exports pl.polsl.hdised.gui.controllers;
    opens pl.polsl.hdised.gui.controllers to javafx.fxml;
}