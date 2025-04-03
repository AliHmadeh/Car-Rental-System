module com.example.hotelmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.hotelmanagementsystem to javafx.fxml;
    exports com.example.hotelmanagementsystem;
}