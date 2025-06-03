package org.APPLI.vue;



import java.io.IOException;

import org.APPLI.controleur.Controleur;
import org.APPLI.modele.ScenarioCreator;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class CreateScenarioMenu extends GridPane {

    private TextField titleField;
    Label error;

    public CreateScenarioMenu() {
        
        Label title = new Label("Création d'un scénario");
        Label text = new Label("_Titre");
        error = new Label();
        text.setMnemonicParsing(true);

        titleField = new TextField();
        titleField.setPromptText("Entrer un titre");
        text.setLabelFor(titleField);

        Button apply = new Button("Créer");
        apply.setAccessibleText("create");
        apply.setOnAction(new Controleur());
        add(title, 0, 0);
        add(text, 0, 1);
        add(titleField, 1, 1);
        add(error, 3,1);
        add(apply, 3, 3);

    }

    public String getTitle() {
        return titleField.getText();
    }

    public void createNewScenario()  {
        try {
            error.setText("");
            ScenarioCreator scenarioCreator = new ScenarioCreator();
            scenarioCreator.createNewFile(titleField.getText()+".txt");
            titleField.clear();
            setVisible(false);
        } catch (IOException e) {
            error.setText(e.getMessage());
        }
    }
}
