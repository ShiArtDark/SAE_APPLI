package org.APPLI.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioMenuItem;


import org.APPLI.modele.Scenario;
import org.APPLI.modele.Graphe;
import org.APPLI.modele.Sommet;
import org.APPLI.vue.DistanceView;
import org.APPLI.vue.MenuMenuBar;
import org.APPLI.vue.VilleListView;
import org.APPLI.vue.OutputView;

import java.util.ArrayList;

/**
 * Contrôleur principal
 * Gère les interactions utilisateur depuis le menu et déclenche les actions logiques.
 */
public class Controleur implements EventHandler<ActionEvent> {

    // === Composants de la vue ===
    private VilleListView villeListView;
    private DistanceView distanceView;
    private MenuMenuBar menuMenuBar;
    private OutputView outputView;

    // === Scénario courant ===
    private Scenario currentScenario;

    // === Constructeur : instancie toutes les vues et configure les écouteurs ===
    public Controleur() {
        menuMenuBar = new MenuMenuBar();
        villeListView = new VilleListView();
        distanceView = new DistanceView();
        outputView = new OutputView();

        // === Listener pour la sélection de scénario depuis le menu ===
        menuMenuBar.setScenarioSelectionListener(scenario -> {
            this.handle(new ActionEvent(scenario, null));
        });

        // === Listener pour la sélection d'algorithme depuis le menu ===
        menuMenuBar.setAlgoSelectionListener((option, k) -> {
            executeAlgorithm(option, k);
        });
    }

    // === Méthode appelée lors d'un événement de sélection ===
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() instanceof Scenario scenario) {
            // Cas 1 : scénario directement passé depuis le listener
            System.out.println(">> handle() - Scénario sélectionné : " + scenario.getName());

            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);
            setScenario(scenario);

        } else if (event.getSource() instanceof RadioMenuItem item) {
            // Cas 2 : clic sur un item du menu
            Scenario scenario = (Scenario) item.getUserData();
            System.out.println(">> handle() - RadioMenuItem cliqué : " + scenario.getName());

            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);
            setScenario(scenario);
        }
    }

    // ========== PARTIE : (exécution des algorithmes) ==========

    /**
     * Enregistre le scénario sélectionné comme courant.
     */
    public void setScenario(Scenario scenario) {
        this.currentScenario = scenario;
    }

    /**
     * Exécute l'algorithme sélectionné (tri topologique ou meilleures solutions).
     * @param option le type d'algorithme (ex: FIRST ou MIN)
     * @param k nombre de solutions à générer (si -1 : tri topologique)
     */
    public void executeAlgorithm(int option, int k) {
        if (currentScenario == null) {
            outputView.displayError("Aucun scénario sélectionné");
            return;
        }

        Graphe graphe = currentScenario.getGraphe();

        try {
            if (option == -1) {
                // Meilleures solutions : tous les chemins pertinents
                executeMeilleuresSolutions(graphe, k);
            } else {
                // Tri topologique : un seul chemin optimisé
                executeTriTopologique(graphe, option);
            }
        } catch (Exception e) {
            outputView.displayError("Erreur lors de l'exécution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exécute l’algorithme de tri topologique selon l’option sélectionnée.
     */
    private void executeTriTopologique(Graphe graphe, int option) {
        ArrayList<Sommet> chemin = graphe.triTopologique(option);
        int distance = graphe.getDistance(chemin);

        String algoName = (option == Graphe.FIRST) ?
                "Tri Topologique - Premier élément" :
                "Tri Topologique - Distance minimale";

        outputView.displayTriTopologique(chemin, algoName, distance);
    }

    /**
     * Exécute le calcul des k meilleures solutions dans le graphe.
     */
    private void executeMeilleuresSolutions(Graphe graphe, int k) {
        ArrayList<ArrayList<Sommet>> solutions = graphe.calculeDesSolutions(k);
        outputView.displayMeilleuresSolutions(solutions, k);
    }

    // ========== GETTERS POUR LES VUES ==========

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
