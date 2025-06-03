package org.APPLI.vue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditCreateScenario extends VBox {
    
    private ToggleGroup editCreate;
    private CreateScenarioMenu createScenarioMenu ;
    private EditMenuScenario editMenuScenario;
    

    public EditCreateScenario() {
        editCreate = new ToggleGroup();
        Label title = new Label("_Créer ou modifier ?");
        title.setMnemonicParsing(true);
        HBox hbox = new HBox();
        
        RadioButton createButton = new RadioButton("Créer un Scénario");
        RadioButton editButton = new RadioButton("Modifier un scénario");
        
        title.setLabelFor(createButton);
        createButton.setSelected(true);
        createButton.setToggleGroup(editCreate);
        editButton.setToggleGroup(editCreate);

        createButton.setId("create");
        editButton.setId("edit");

        createButton.setUserData(0);
        editButton.setUserData(1);
        
        hbox.getChildren().addAll(createButton, editButton);
        Button button = new Button("Appliquer");
        createScenarioMenu = new CreateScenarioMenu();
        createScenarioMenu.setVisible(false);

        editMenuScenario = new EditMenuScenario();
        editMenuScenario.setVisible(false);



        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (editCreate.getSelectedToggle().getUserData().equals(0) ) {
                    createScenarioMenu.setVisible(true);
                    editMenuScenario.setVisible(false);
                } else {
                    createScenarioMenu.setVisible(false);
                    editMenuScenario.setVisible(true);

                }
            }
            
        });

        getChildren().addAll(title, hbox, button, createScenarioMenu, editMenuScenario);
    }

    public CreateScenarioMenu getCreateScenarioMenu() {
        return createScenarioMenu;
    }

    public EditMenuScenario  getEditMenu() {
        return editMenuScenario;
    }
}
