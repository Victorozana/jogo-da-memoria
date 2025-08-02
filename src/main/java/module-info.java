module com.example.jogomemoria {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.security.jgss;
    requires org.json;

    opens com.example.jogomemoria to javafx.fxml;
    exports com.example.jogomemoria;
}