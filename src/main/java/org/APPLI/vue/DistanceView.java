package org.APPLI.vue;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import org.APPLI.modele.Scenario;
import org.APPLI.modele.Sommet;

import java.util.ArrayList;

public class DistanceView extends VBox {
    private Label labScenario;
    private TableView<Sommet> table = new TableView<>();
    private Scenario chScenario;

    public DistanceView() {
        labScenario = new Label("Scenario");
        this.getChildren().add(labScenario);

        Label title = new Label("Chemins");
        this.getChildren().add(title);
        this.setSpacing(5);

        TableColumn<Sommet, String> ville = new TableColumn<>("ville");
        ville.setCellValueFactory(new PropertyValueFactory<>("name"));
        ville.setPrefWidth(150);

        TableColumn<Sommet, Integer> membres = new TableColumn<>("membres");
        membres.setCellValueFactory(new PropertyValueFactory<>("pass"));  // <-- ici "pass", pas "membres"
        membres.setPrefWidth(150);

        TableColumn<Sommet, ArrayList> distances = new TableColumn<>("distances");
        distances.setCellValueFactory(new PropertyValueFactory<>("distance"));
        distances.setPrefWidth(150);

        table.getColumns().addAll(ville, membres, distances);
        this.getChildren().add(table);
    }

    public void setScenario(Scenario scenario) {
        this.chScenario = scenario;
        updateTable();
    }

    private void updateTable() {
        if (chScenario == null) {
            table.getItems().clear();
            return;
        }
        /*ArrayList<Sommet> listeSommets = chScenario.getSommets(); // méthode à ajouter dans Scenario
        table.getItems().setAll(listeSommets);*/
    }
}
