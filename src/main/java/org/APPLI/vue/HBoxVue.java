package org.APPLI.vue;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.APPLI.controleur.Controleur;
import org.APPLI.modele.Scenario;
import org.APPLI.modele.Sommet;


public class HBoxVue extends HBox {
    private static VilleListView chVilleListView;
    private static ScenarioSelector chSelector;
    private static DistanceView chDistanceView;
    private static Scenario chScenario;
    private static Sommet chSommet;

    public HBoxVue() {

        chSelector = new ScenarioSelector();
        chDistanceView = new DistanceView();



        //chVilleListView = new VilleListView();


        this.getChildren().addAll(chDistanceView,chVilleListView,chSelector);


    }


}