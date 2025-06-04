package org.APPLI.controleur;

import java.util.ArrayList;

import org.APPLI.modele.Graphe;
import org.APPLI.modele.Scenario;
import org.APPLI.modele.ScenarioCreator;
import org.APPLI.modele.Sommet;
import org.APPLI.vue.DistanceView;
import org.APPLI.vue.HBoxRoot;
import org.APPLI.vue.MenuMenuBar;
import org.APPLI.vue.OutputView;
import org.APPLI.vue.VilleListView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;

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
            // System.out.println(">> handle() - Scénario sélectionné : " + scenario.getName());

            // Mise à jour des vues
            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);


            setScenario(scenario);

        } else if (event.getSource() instanceof RadioMenuItem item) {
            Scenario scenario = (Scenario) item.getUserData();
            // System.out.println(">> handle() - RadioMenuItem cliqué : " + scenario.getName());

            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);


            setScenario(scenario);

        } else if (event.getSource() instanceof RadioButton) {
            String scenario = (String )((Node) event.getSource()).getUserData();
            HBoxRoot.getCreator().setScenario(scenario);
            try {
                ScenarioCreator scenarioCreator = new ScenarioCreator();
                scenarioCreator.importTradeSheet(scenario);
                HBoxRoot.getCreator().getEdit().getEditMenu().setNumBox(scenarioCreator.getTradeList().size());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if( event.getSource() instanceof Button) {
            
            switch (((Node) event.getSource()).getAccessibleText()) {
                case "create" -> {
                    HBoxRoot.getCreator().getEdit().getCreateScenarioMenu().createNewScenario();
                    HBoxRoot.getCreator().getSelection().createSelectionMenu();
                }
                case "ajoutLigne" -> {
                    HBoxRoot.getCreator().getEdit().getEditMenu().addLine();

                    try {
                        HBoxRoot.getCreator().getTableTradeSheet().refresh();
                        ScenarioCreator scenarioCreator = new ScenarioCreator();
                        scenarioCreator.importTradeSheet(HBoxRoot.getCreator().getScenario());
                        HBoxRoot.getCreator().getEdit().getEditMenu().setNumBox(scenarioCreator.getTradeList().size());
                        
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "delButton" -> {
                    HBoxRoot.getCreator().getEdit().getEditMenu().removeLine();
                    try {
                         ScenarioCreator scenarioCreator = new ScenarioCreator();
                        scenarioCreator.importTradeSheet(HBoxRoot.getCreator().getScenario());
                        HBoxRoot.getCreator().getEdit().getEditMenu().setNumBox(scenarioCreator.getTradeList().size());
                        HBoxRoot.getCreator().getTableTradeSheet().refresh();
                        
                        
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    
                }
                           
            }

            try {
                
                HBoxRoot.getControleur().getMenuMenuBar().refreshScenarioSelection();
            } catch (Exception e) {
            }
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