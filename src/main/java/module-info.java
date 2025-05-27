module org.APPLI.Main {

    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires javatuples;

    opens org.APPLI to javafx.fxml; // ← Important pour FXML
    exports org.APPLI;              // ← Important pour que JavaFX accède à Main
}
