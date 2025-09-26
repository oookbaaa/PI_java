module com.example.benayedjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires itextpdf;
    requires org.controlsfx.controls;
    opens com.example.benayedjavafx.entities to javafx.base;



    opens com.example.benayedjavafx to javafx.fxml;
    exports com.example.benayedjavafx;
}