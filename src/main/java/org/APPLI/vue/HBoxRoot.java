package org.APPLI.vue;

import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import org.APPLI.controleur.Controleur;
import org.APPLI.vue.DistanceView;

public class HBoxRoot extends VBox {
    private static Controleur chControleur;
    public HBoxRoot() {
        super(30);
        chControleur = new Controleur();

        MenuMenuBar menuMenuBar = new MenuMenuBar();

        this.getChildren().addAll(menuMenuBar);


    }


    public static Label getScenario() {return new Label("Scenario");}
    public static Controleur getControleur() {return chControleur;}

}
