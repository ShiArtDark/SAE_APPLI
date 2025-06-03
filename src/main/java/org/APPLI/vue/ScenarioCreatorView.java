package org.APPLI.vue;

import javafx.scene.layout.HBox;

public class ScenarioCreatorView extends HBox{
    SCSelection selectionPane;
    TableTradeSheet tableTradeSheet;
    EditCreateScenario edit;
    String scenario;


    public ScenarioCreatorView() {
        scenario = "";
        selectionPane= new SCSelection();
        tableTradeSheet = new TableTradeSheet();
        edit = new EditCreateScenario();

        getChildren().addAll(selectionPane, tableTradeSheet, edit);
    }

    public void setScenario(String _scenario) {
        scenario = _scenario;
    
        try {
            tableTradeSheet.setTable(_scenario);
        } catch (Exception e) {
            System.out.println("Le Scénario n'existe pas");
        }
    }

    public String getScenario() {
        return scenario;
    }

    public SCSelection getSelection() {
        return selectionPane;
    }

    public TableTradeSheet getTableTradeSheet() {
        return tableTradeSheet;
    }

    public EditCreateScenario getEdit() {
        return edit;
    }

    
}
