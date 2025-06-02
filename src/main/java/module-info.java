module org.APPLI.main {

    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires javafx.controls;
    requires javafx.base;

    opens org.APPLI to javafx.fxml; // ← Important pour FXML
    exports org.APPLI;              // ← Important pour que JavaFX accède à Main
}
