module com.example.saejsplusclb {

    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires javatuples;

    opens org.APPLI to javafx.fxml;        // pour FXML
    opens org.APPLI.modele to javafx.base; // <-- ajoute cette ligne pour la réflexion JavaFX

    exports org.APPLI;                      // export pour accès public
}
