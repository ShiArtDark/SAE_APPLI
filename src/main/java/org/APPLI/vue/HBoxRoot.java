package org.APPLI.vue;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.APPLI.controleur.Controleur;

public class HBoxRoot extends VBox {

    private Controleur controleur;

    public HBoxRoot() {
        super(10);


        controleur = new Controleur();


        HBox principale = new HBox(10);


        VBox leftPane = new VBox(controleur.getVilleListView(), controleur.getDistanceView());


        VBox rightPane = new VBox(controleur.getOutputView());

        principale.getChildren().addAll(leftPane, rightPane);


        this.getChildren().addAll(controleur.getMenuMenuBar(), principale);
    }

    public Controleur getControleur() {
        return controleur;
    }
}