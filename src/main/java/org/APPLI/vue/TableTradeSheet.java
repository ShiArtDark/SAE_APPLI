package org.APPLI.vue;

import java.io.IOException;

import org.APPLI.modele.ScenarioCreator;
import org.APPLI.modele.Trade;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class TableTradeSheet extends VBox{
    
    private Label scenarioName;
    private TableView<Trade> table;
    private String scenario ="";

    public TableTradeSheet() {
        scenarioName = new Label("Selectionner un Scénario");
        table = new TableView<>();
        table.setId("TradeTable");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); // Permet de retirer la dernière colonne 

        TableColumn<Trade, Integer> indexCol = new TableColumn<>("Index");
        TableColumn<Trade, String> senderCol = new TableColumn<>("Livreur");
        TableColumn<Trade, String> clientCol = new TableColumn<>("Client");

        indexCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        senderCol.setCellValueFactory(new PropertyValueFactory<>("senderTown"));
        clientCol.setCellValueFactory(new PropertyValueFactory<>("clientTown"));


        table.getColumns().add(indexCol);
        table.getColumns().add(senderCol);
        table.getColumns().add(clientCol);
 
        
        
  

        getChildren().addAll(scenarioName, table);
    }

    public void setTable(String _scenario) throws IOException {
        scenario = _scenario;
        scenarioName.setText(_scenario.substring(0, _scenario.length()-4));
        table.getItems().clear();
        ScenarioCreator scenarioCreator = new ScenarioCreator();
        scenarioCreator.importTradeSheet(_scenario);
        for(Trade tr : scenarioCreator.getTradeList()) {

            table.getItems().add(tr);
        }


    }

    public void refresh() throws IOException{
        table.getItems().clear();
        ScenarioCreator scenarioCreator = new ScenarioCreator();
        scenarioCreator.importTradeSheet(scenario);
        for(Trade tr : scenarioCreator.getTradeList()) {

            table.getItems().add(tr);
        }
    }



}
