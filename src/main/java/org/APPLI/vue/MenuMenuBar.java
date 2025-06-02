package org.APPLI.vue;

import javafx.scene.control.*;
import org.APPLI.modele.Scenario;
import org.APPLI.modele.FileManager;

import java.io.File;

public class MenuMenuBar extends MenuBar {

    public interface ScenarioSelectionListener {
        void onScenarioSelected(Scenario scenario);
    }

    private ScenarioSelectionListener listener;

    public void setScenarioSelectionListener(ScenarioSelectionListener listener) {
        this.listener = listener;
    }

    public MenuMenuBar() {
        try {
            File scenarioFolder = new File("ressources/scenario");
            ToggleGroup scegroup = new ToggleGroup();
            Menu scenarioMenu = new Menu("Scénarios");

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
                                if (listener != null) {
                                    listener.onScenarioSelected(selectedScenario);
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
        } catch (Exception e) {
            System.out.println("Erreur dans MenuMenuBar : " + e.getMessage());
        }
    }
}
