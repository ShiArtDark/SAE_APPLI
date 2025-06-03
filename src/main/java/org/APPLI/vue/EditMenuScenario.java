package org.APPLI.vue;

import java.util.TreeSet;

import org.APPLI.controleur.Controleur;
import org.APPLI.modele.FileManager;
import org.APPLI.modele.ScenarioCreator;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class EditMenuScenario extends GridPane {
    private ComboBox<String> sender;
    private ComboBox<String> client;
    private ChoiceBox<Integer> numBox;
    private String scenario="";
    private Label errorAdd;
    private Label errorRem;
    

    public EditMenuScenario(){


        Label title = new Label("Modification d'un scénario");
        errorAdd = new Label("");
        errorRem= new Label("");
        
        try {

            // Ajout d'un echange
            Label labelSender = new Label("_Livreur");
            Label labelClient = new Label("_Client");
            TreeSet<String> members = FileManager.exportSetMembre();
            sender = new ComboBox<>();
            client = new ComboBox<>();
            labelSender.setMnemonicParsing(true);
            labelSender.setLabelFor(sender);
            labelClient.setMnemonicParsing(true);
            labelClient.setLabelFor(client);
   
            sender.setPrefWidth(200);
            client.setPrefWidth(200);

            Label ajout = new Label( "_Ajouter cette échange");
            Button addButton = new Button("Ajouter");
            addButton.setAccessibleText("ajoutLigne");
            addButton.setOnAction(new Controleur());
            ajout.setMnemonicParsing(true);
            ajout.setLabelFor(addButton);

            for(String mem : members) {
                sender.getItems().add(mem);
                client.getItems().add(mem);
            }


            // Suppression d'un échange
            Label titleSuprress = new Label("_Suppression d'une ligne");
            Label saisi = new Label("_Numéro à supprimer");
            numBox = new ChoiceBox<>();
            numBox.setPrefWidth(200);
            saisi.setMnemonicParsing(true);
            titleSuprress.setMnemonicParsing(true);
            saisi.setLabelFor(numBox);
            
            Button delButton = new Button("Supprimer");
            delButton.setAccessibleText("delButton");
            delButton.setOnAction(new Controleur());
            titleSuprress.setLabelFor(delButton);

            add(title, 0,0);
            add(new Label(" "), 0,1);

            add(labelSender, 0, 2);
            add(sender, 1,2);
            add(labelClient, 0, 3);
            add(client, 1,3);
            add(new Label(" "), 0, 4);
            add(ajout,0,5);
            add(addButton,5,5);
            add(errorAdd, 0, 6);
            add(new Label(" "), 0, 7);
            add(new Label(" "), 0, 8);
            add(new Label(" "), 0, 9);


            add(titleSuprress,0,9);
            add(saisi, 0, 10);
            add(numBox,1,10);
            add(new Label(" "),0, 11);
            add(delButton,5,12);
            add(new Label(" "), 0, 13);
            add(errorRem, 0, 14);


        

        } catch (Exception e) {
        }

        
    }

    public void addLine() {
        errorAdd.setText("");
        scenario = HBoxRoot.getCreator().getScenario();
        
        try {
            ScenarioCreator scenarioCreator = new ScenarioCreator();
            scenarioCreator.importTradeSheet(scenario);
            scenarioCreator.addToTrade(sender.getSelectionModel().getSelectedItem(), client.getSelectionModel().getSelectedItem());
            sender.getSelectionModel().clearSelection();
            client.getSelectionModel().clearSelection();
            scenarioCreator.writeScenario(scenario);

        } catch (Exception e) {
            errorAdd.setText(e.getMessage());
        } 
    }

    public void removeLine() {
        errorRem.setText("");
        scenario = HBoxRoot.getCreator().getScenario();
        try {
            
            ScenarioCreator scenarioCreator = new ScenarioCreator();
            scenarioCreator.importTradeSheet(scenario);
            
            scenarioCreator.removeTrade(numBox.getSelectionModel().getSelectedIndex());
            scenarioCreator.writeScenario(scenario);
            scenarioCreator.importTradeSheet(scenario);

            setNumBox(scenarioCreator.getTradeList().size());
            numBox.getSelectionModel().clearSelection();
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void setNumBox(int _size) {
        numBox.getItems().clear();
        for (int i = 1; i<_size+1;i++) {
            numBox.getItems().add(i);
        }
    }

   
}
