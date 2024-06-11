module com.example.dbusersmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.dbusersmanagement to javafx.fxml;
    exports com.example.dbusersmanagement;
}