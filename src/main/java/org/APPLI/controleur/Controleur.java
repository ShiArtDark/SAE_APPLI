package org.APPLI.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.APPLI.modele.Scenario;
import org.APPLI.modele.Graphe;
import org.APPLI.modele.Sommet;
import org.APPLI.vue.DistanceView;
import org.APPLI.vue.MenuMenuBar;
import org.APPLI.vue.VilleListView;
import org.APPLI.vue.OutputView;

import java.util.ArrayList;

public class Controleur implements EventHandler<ActionEvent> {

    private VilleListView villeListView;
    private DistanceView distanceView;
    private MenuMenuBar menuMenuBar;
    private OutputView outputView;

    private Scenario currentScenario;

    public Controleur() {
        menuMenuBar = new MenuMenuBar();
        villeListView = new VilleListView();
        distanceView = new DistanceView();
        outputView = new OutputView();

        // Configuration du listener pour les scénarios
        menuMenuBar.setScenarioSelectionListener(scenario -> {
            this.handle(new ActionEvent(scenario, null));
        });

        // Configuration du listener pour les algorithmes
        menuMenuBar.setAlgoSelectionListener((option, k) -> {
            executeAlgorithm(option, k);
        });
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() instanceof Scenario scenario) {
            System.out.println(">> handle() - Scénario sélectionné : " + scenario.getName());

            // Mise à jour des vues
            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);


            setScenario(scenario);

        } else if (event.getSource() instanceof RadioMenuItem item) {
            Scenario scenario = (Scenario) item.getUserData();
            System.out.println(">> handle() - RadioMenuItem cliqué : " + scenario.getName());

            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);


            setScenario(scenario);
        }
    }

    // ========== PARTIE ALGORITHMES  ==========

    public void setScenario(Scenario scenario) {
        this.currentScenario = scenario;
    }

    public void executeAlgorithm(int option, int k) {
        if (currentScenario == null) {
            outputView.displayError("Aucun scénario sélectionné");
            return;
        }

        Graphe graphe = currentScenario.getGraphe();

        try {
            if (option == -1) {
                // Meilleures solutions
                executeMeilleuresSolutions(graphe, k);
            } else {
                // Tri topologique
                executeTriTopologique(graphe, option);
            }
        } catch (Exception e) {
            outputView.displayError("Erreur lors de l'exécution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void executeTriTopologique(Graphe graphe, int option) {
        ArrayList<Sommet> chemin = graphe.triTopologique(option);
        int distance = graphe.getDistance(chemin);

        String algoName = (option == Graphe.FIRST) ?
                "Tri Topologique - Premier élément" :
                "Tri Topologique - Distance minimale";

        outputView.displayTriTopologique(chemin, algoName, distance);
    }

    private void executeMeilleuresSolutions(Graphe graphe, int k) {
        ArrayList<ArrayList<Sommet>> solutions = graphe.calculeDesSolutions(k);
        outputView.displayMeilleuresSolutions(solutions, k);
    }

    // ========== ACCESSEURS ==========

    public VilleListView getVilleListView() {
        return villeListView;
    }

    public DistanceView getDistanceView() {
        return distanceView;
    }

    public MenuMenuBar getMenuMenuBar() {
        return menuMenuBar;
    }

    public OutputView getOutputView() {
        return outputView;
    }
}