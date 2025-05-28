
package org.APPLI.vue;

import javafx.scene.control.*;
        import org.APPLI.modele.FileManager;
import org.APPLI.modele.Scenario;

import java.io.File;

public class MenuMenuBar extends MenuBar {

    public MenuMenuBar() {
        try {
            File scenarioFolder = new File("ressources/scenario");
            ToggleGroup scegroup = new ToggleGroup();
            Menu scenarioMenu = new Menu("Scenarios");
            File[] files = scenarioFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    String scenarioName = file.getName();
                    System.out.println(scenarioName);
                    Scenario scenario = FileManager.getScenario(scenarioName);
                    RadioMenuItem scenarioItem = new RadioMenuItem(scenarioName);
                    scenarioItem.setUserData(scenario);
                    scenarioItem.setToggleGroup(scegroup);
                    scenarioMenu.getItems().add(scenarioItem);
                }
            }

            this.getMenus().add(scenarioMenu);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
