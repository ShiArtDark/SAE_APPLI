package org.APPLI.vue;

import javafx.scene.control.TextArea;
import org.APPLI.modele.Scenario;

public class ScenarioContentView extends TextArea {

    public ScenarioContentView() {
        this.setEditable(false);
        this.setPrefHeight(200);
    }

    public void afficherScenario(Scenario scenario) {
        if (scenario == null) {
            this.setText("Aucun scénario sélectionné");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Nom : ").append(scenario.getName()).append("\n\n");
            sb.append("Distances : ").append(scenario.getDistance().toString()).append("\n\n");
            sb.append("Membres : ").append(scenario.getMembres().toString()).append("\n\n");
            this.setText(sb.toString());
        }
    }
}
