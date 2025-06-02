package org.APPLI.vue;

import javafx.scene.layout.VBox;
import org.APPLI.controleur.Controleur;
import org.APPLI.modele.Scenario;

public class HBoxRoot extends VBox {
    private MenuMenuBar menuMenuBar;
    private HBoxVue hBoxVue;


    public HBoxRoot() {
        super(10);

        menuMenuBar = new MenuMenuBar();
        hBoxVue = new HBoxVue();


        menuMenuBar.setScenarioSelectionListener(scenario -> {
            hBoxVue.setScenario(scenario);
        });

        this.getChildren().addAll(menuMenuBar, hBoxVue);
    }


}
