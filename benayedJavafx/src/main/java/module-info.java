module com.example.benayedjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.benayedjavafx to javafx.fxml;
    exports com.example.benayedjavafx;
}