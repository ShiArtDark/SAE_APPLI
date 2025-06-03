package org.APPLI.vue;

import java.io.File;

import org.APPLI.controleur.Controleur;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SCSelection extends VBox {
    
    private Label labelIsEmpty;
    private ScrollPane scrollPane;
    private ToggleGroup scenarioGroup;
    private VBox selectionView;

    public SCSelection() {
        setId("SelectionMenu");
        HBox selectionHeader = new HBox();

        scrollPane = new ScrollPane();

        scrollPane.setPrefViewportHeight(1000);
        scrollPane.setPrefViewportWidth(300);
    
        createSelectionMenu();
    
        selectionHeader.getChildren().add(labelIsEmpty);
        getChildren().addAll(selectionHeader, scrollPane);
        

    }

    public void createSelectionMenu() {

        try {
            
            File scenarioDir = new File("ressources/scenario");
            if (scenarioDir.listFiles().length > 0) {
                labelIsEmpty = new Label(scenarioDir.listFiles().length+" "+ ((scenarioDir.listFiles().length > 1 ) ? "scénarios" : "scénario"));
            } else {
                labelIsEmpty = new Label("Aucun scénario");
            }
            
            scenarioGroup = new ToggleGroup();
            selectionView = new VBox();
            scrollPane.setContent(selectionView);
            for(File file : scenarioDir.listFiles()) {
                RadioButton tmpButton = new RadioButton(file.getName());
                tmpButton.setId("SelectionButton");
                tmpButton.setUserData(file.getName());
                tmpButton.setToggleGroup(scenarioGroup);
                selectionView.getChildren().add(tmpButton);
                tmpButton.setOnAction(new Controleur());
            }
        } catch (Exception e) {
            
        }
    }
}
