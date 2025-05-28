package org.APPLI;


import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Utilise HBox comme conteneur de base
        HBox root = new HBox(); // ou new HBoxRoot() si tu as une classe perso

        // Crée une scène
        Scene scene = new Scene(root, 600, 400);
        File css=new File("css" + File.separator+"premiersStyles.css");
        scene.getStylesheets().add(css.toURI().toString());
        // Configuration de la fenêtre
        stage.setTitle("Appli");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);


    }
}
        

   
