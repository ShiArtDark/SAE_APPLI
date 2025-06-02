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

            sb.append("Distances :\n");
            scenario.getDistance().forEach((ville, distances) -> {
                sb.append("  ").append(ville).append(" : ");
                sb.append(distances.toString());
                sb.append("\n");
            });

            sb.append("\nMembres :\n");
            scenario.getMembres().forEach((ville, membres) -> {
                sb.append("  ").append(ville).append(" : ");
                if (membres != null && !membres.isEmpty()) {
                    sb.append(String.join(", ", membres));
                } else {
                    sb.append("aucun membre");
                }
                sb.append("\n");
            });

            this.setText(sb.toString());
        }
    }

}
