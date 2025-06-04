package org.APPLI.vue;

import java.io.File;
import java.io.IOException;

import org.APPLI.controleur.ChangeController;
import org.APPLI.modele.FileManager;
import org.APPLI.modele.Graphe;
import org.APPLI.modele.Scenario;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;

public class MenuMenuBar extends MenuBar {
    private ToggleGroup modeGroup;
    private ToggleGroup scegroup;
    private Menu scenarioMenu;

    public interface ScenarioSelectionListener {
        void onScenarioSelected(Scenario scenario);
    }

    public interface AlgoSelectionListener {
        void onAlgoSelected(int option, int k);
    }

    private ScenarioSelectionListener scenarioListener;
    private AlgoSelectionListener algoListener;

    public void setScenarioSelectionListener(ScenarioSelectionListener listener) {
        this.scenarioListener = listener;
    }

    public void setAlgoSelectionListener(AlgoSelectionListener listener) {
        this.algoListener = listener;
    }

    public MenuMenuBar() {
        
        try {
            // --- Menu Scénarios ---
            File scenarioFolder = new File("ressources/scenario");
            scegroup = new ToggleGroup();
            scenarioMenu = new Menu("Scénarios");

            File[] files = scenarioFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        String scenarioName = file.getName();

                        RadioMenuItem scenarioItem = new RadioMenuItem(scenarioName);
                        scenarioItem.setToggleGroup(scegroup);

                        scenarioItem.setOnAction(e -> {
                            try {
                                Scenario selectedScenario = FileManager.getScenario(scenarioName);
                                System.out.println("Scénario sélectionné : " + selectedScenario.getName());
                                if (scenarioListener != null) {
                                    scenarioListener.onScenarioSelected(selectedScenario);
                                }
                            } catch (Exception ex) {
                                System.out.println("Erreur chargement scénario : " + ex.getMessage());
                            }
                        });

                        scenarioMenu.getItems().add(scenarioItem);
                    }
                }
            }
            this.getMenus().add(scenarioMenu);

            // --- Menu Algorithmes ---
            Menu algoMenu = new Menu("Algorithmes");

            MenuItem triFirst = new MenuItem("Tri Topologique - Premier élément");
            MenuItem triDistance = new MenuItem("Tri Topologique - Distance minimale");
            MenuItem meilleuresSolutions = new MenuItem("Meilleures Solutions (k=5)");
            MenuItem meilleuresSolutionsCustom = new MenuItem("Meilleures Solutions (k personnalisé)");

            algoMenu.getItems().addAll(triFirst, triDistance, new SeparatorMenuItem(),
                    meilleuresSolutions, meilleuresSolutionsCustom);


            triFirst.setOnAction(e -> notifyAlgoSelected(Graphe.FIRST, 0));


            triDistance.setOnAction(e -> notifyAlgoSelected(Graphe.DISTANCE, 0));


            meilleuresSolutions.setOnAction(e -> notifyAlgoSelected(-1, 5));


            meilleuresSolutionsCustom.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("10");
                dialog.setTitle("Nombre de solutions");
                dialog.setHeaderText("Combien de solutions voulez-vous ?");
                dialog.setContentText("Entrez k :");

                dialog.showAndWait().ifPresent(result -> {
                    try {
                        int k = Integer.parseInt(result);
                        if (k > 0) {
                            notifyAlgoSelected(-1, k);
                        } else {
                            showAlert("Erreur", "k doit être un nombre positif");
                        }
                    } catch (NumberFormatException ex) {
                        showAlert("Erreur", "Veuillez entrer un nombre valide");
                    }
                });
            });

            getMenus().add(algoMenu);

            Menu mode = new Menu("Mode");
            modeGroup = new ToggleGroup();
            
            RadioMenuItem algo = new RadioMenuItem("Calcul de chemins");
            RadioMenuItem  createur = new RadioMenuItem("Créer scénario");
            algo.setToggleGroup(modeGroup);
            algo.setSelected(true);
            algo.setUserData(0);
            algo.setOnAction(new ChangeController());


            createur.setToggleGroup(modeGroup);
            createur.setUserData(1);
            createur.setOnAction(new ChangeController());
        
            

            mode.getItems().addAll(algo, createur);

            getMenus().add(mode);
        
                




        } catch (Exception e) {
            System.out.println("Erreur dans MenuMenuBar : " + e.getMessage());
        }

     
        
    }

    public void refreshScenarioSelection() throws IOException {
        scenarioMenu.getItems().clear();
        File dir = new File("ressources/scenario");
        
        for(File file : dir.listFiles()) {
            String scenarioName = file.getName();

            RadioMenuItem scenarioItem = new RadioMenuItem(scenarioName);
            scenarioItem.setToggleGroup(scegroup);

            scenarioItem.setOnAction(e -> {
                try {
                    Scenario selectedScenario = FileManager.getScenario(scenarioName);
                    System.out.println("Scénario sélectionné : " + selectedScenario.getName());
                    if (scenarioListener != null) {
                        scenarioListener.onScenarioSelected(selectedScenario);
                    }
                } catch (Exception ex) {
                    System.out.println("Erreur chargement scénario : " + ex.getMessage());
                }
            });

            scenarioMenu.getItems().add(scenarioItem);
        }
    }

    private void notifyAlgoSelected(int option, int k) {
        if (algoListener != null) {
            algoListener.onAlgoSelected(option, k);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}