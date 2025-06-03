package org.APPLI;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

import java.util.ArrayList;
import java.util.TreeMap;

import org.APPLI.controleur.Controleur;
import org.APPLI.modele.FileManager;
import org.APPLI.modele.Graphe;
import org.APPLI.modele.Sommet;
import org.APPLI.vue.HBoxRoot;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Utilise HBox comme conteneur de base
        Controleur controleur = new Controleur();
        HBoxRoot root = new HBoxRoot();

        // Crée une scène

        Scene scene = new Scene(root, 600, 400);
        File css = new File("css" + File.separator + "premiersStyles.css");
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

    
   
