package org.APPLI.controleur;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.APPLI.modele.Scenario;
import org.APPLI.vue.DistanceView;
import org.APPLI.vue.ScenarioContentView;
// Remplace par MenuMenuBar si besoin
import org.APPLI.vue.MenuMenuBar;        // ou ta classe de menu scénario
import org.APPLI.vue.VilleListView;

import java.util.ArrayList;

public class Controleur extends HBox {

    private VilleListView villeListView;
    private DistanceView distanceView;
    private MenuMenuBar menuMenuBar;      // ton menu avec les scénarios
    private ScenarioContentView scenarioContentView;  // vue pour afficher scénario sélectionné

    public Controleur() {

        menuMenuBar = new MenuMenuBar();
        scenarioContentView = new ScenarioContentView();
        villeListView = new VilleListView();
        distanceView = new DistanceView();

        menuMenuBar.setScenarioSelectionListener(scenario -> {
            System.out.println(">> Nouveau scénario sélectionné : " + scenario.getName());
            scenarioContentView.afficherScenario(scenario);
            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);
        });


        VBox leftPane = new VBox(villeListView, distanceView);
        VBox rightPane = new VBox(menuMenuBar, scenarioContentView);

        this.getChildren().addAll(leftPane, rightPane);
        this.setSpacing(10);
    }

}
