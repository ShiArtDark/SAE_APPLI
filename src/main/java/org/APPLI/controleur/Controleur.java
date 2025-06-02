package org.APPLI.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.APPLI.modele.Scenario;
import org.APPLI.vue.DistanceView;
import org.APPLI.vue.MenuMenuBar;
import org.APPLI.vue.ScenarioContentView;
import org.APPLI.vue.VilleListView;

import java.util.ArrayList;

public class Controleur implements EventHandler<ActionEvent> {

    private VilleListView villeListView;
    private DistanceView distanceView;
    private MenuMenuBar menuMenuBar;
    private ScenarioContentView scenarioContentView;

    public Controleur() {
        menuMenuBar = new MenuMenuBar();
        scenarioContentView = new ScenarioContentView();
        villeListView = new VilleListView();
        distanceView = new DistanceView();


        menuMenuBar.setScenarioSelectionListener(scenario -> {
            this.handle(new ActionEvent(scenario, null));
        });

    }

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() instanceof Scenario scenario) {
            System.out.println(">> handle() - Scénario sélectionné : " + scenario.getName());


            scenarioContentView.afficherScenario(scenario);
            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);
        } else if (event.getSource() instanceof RadioMenuItem item) {

            Scenario scenario = (Scenario) item.getUserData();
            System.out.println(">> handle() - RadioMenuItem cliqué : " + scenario.getName());

            scenarioContentView.afficherScenario(scenario);
            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);
        }
    }
}
