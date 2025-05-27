package org.APPLI.vue;

import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ScenarioSelector extends VBox {

    private ComboBox<String> comboBox;

    public ScenarioSelector() {
        Label label = new Label("Choisir un scénario");
        comboBox = new ComboBox<>();
        comboBox.getItems().addAll("scenario_0.txt", "scenario_1.txt", "scenario_2.txt");
        comboBox.getSelectionModel().selectFirst();

        this.getChildren().addAll(label, comboBox);
        this.setSpacing(5);
    }

    public String getSelectedScenario() {
        return comboBox.getSelectionModel().getSelectedItem();
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }
}
