package org.APPLI;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

import java.util.ArrayList;
import java.util.TreeMap;

import org.APPLI.modele.FileManager;
import org.APPLI.modele.Graphe;
import org.APPLI.modele.Sommet;


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
        try {

            TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph("scenario_8.txt");
            Graphe graphe = new Graphe(map);
            ArrayList<Sommet> chemin = graphe.triTopologique();
            System.out.println(graphe.getEntrant());

            //System.out.println(chemin);
            System.out.println(graphe.GetDistance(chemin));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
        
    
   
