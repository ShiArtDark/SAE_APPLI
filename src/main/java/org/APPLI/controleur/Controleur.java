package org.APPLI.controleur;

import org.APPLI.modele.Scenario;
import org.APPLI.modele.ScenarioCreator;
import org.APPLI.vue.DistanceView;
import org.APPLI.vue.HBoxRoot;
import org.APPLI.vue.MenuMenuBar;
import org.APPLI.vue.ScenarioContentView;
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
            // System.out.println(">> handle() - Scénario sélectionné : " + scenario.getName());


            scenarioContentView.afficherScenario(scenario);
            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);
        } else if (event.getSource() instanceof RadioMenuItem item) {

            Scenario scenario = (Scenario) item.getUserData();
            // System.out.println(">> handle() - RadioMenuItem cliqué : " + scenario.getName());

            scenarioContentView.afficherScenario(scenario);
            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);
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
        }
    }
}
