package org.APPLI.vue;

import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.APPLI.modele.FileManager;
import org.APPLI.modele.Scenario;

import java.io.File;
import java.util.List;

public class ScenarioSelector extends VBox {

    private ComboBox<String> comboBox;

    public ScenarioSelector() {

    }


    public String getSelectedScenario() {
        return comboBox.getSelectionModel().getSelectedItem();
    }


    public ComboBox<String> getComboBox() {
        return comboBox;
    }
}

