module pl.polsl.hdised.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.json;

    opens pl.polsl.hdised.gui to javafx.fxml;
    exports pl.polsl.hdised.gui;

    opens pl.polsl.hdised.gui.controllers to javafx.fxml;
    exports pl.polsl.hdised.gui.controllers;
    exports pl.polsl.hdised.gui.temperatureresponse;
    opens pl.polsl.hdised.gui.temperatureresponse to javafx.fxml;
}