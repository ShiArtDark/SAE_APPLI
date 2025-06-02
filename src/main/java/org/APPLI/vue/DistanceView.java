package org.APPLI.vue;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.APPLI.modele.Scenario;
import org.APPLI.modele.Sommet;

import java.util.ArrayList;

public class DistanceView extends VBox {

    private Label labScenario;
    private TableView<Sommet> table = new TableView<>();
    private Scenario chScenario;

    public DistanceView() {
        labScenario = new Label("Aucun scénario sélectionné");
        this.getChildren().add(labScenario);

        Label title = new Label("Chemins");
        this.getChildren().add(title);
        this.setSpacing(5);

        // Colonne ville (nom)
        TableColumn<Sommet, String> ville = new TableColumn<>("Ville");
        ville.setCellValueFactory(new PropertyValueFactory<>("name"));
        ville.setPrefWidth(150);

        // Colonne membres (affiche la liste des noms membres, pas le nombre)
        TableColumn<Sommet, String> membres = new TableColumn<>("Membres");
        membres.setCellValueFactory(cellData -> {
            String villeNom = cellData.getValue().getName();
            if (chScenario != null && chScenario.getMembres().containsKey(villeNom)) {
                var listeMembres = chScenario.getMembres().get(villeNom);
                if (listeMembres != null && !listeMembres.isEmpty()) {
                    String noms = String.join(", ", listeMembres);
                    return new javafx.beans.property.SimpleStringProperty(noms);
                }
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        membres.setPrefWidth(300);

        // On n'ajoute PAS la colonne distances (suppression)
        table.getColumns().addAll(ville, membres);
        this.getChildren().add(table);
    }

    public void setScenario(Scenario scenario) {
        System.out.println("DistanceView: setScenario appelé avec " + (scenario == null ? "null" : scenario.getName()));
        this.chScenario = scenario;
        updateTable();
    }

    private void updateTable() {
        table.getItems().clear();

        if (chScenario == null) {
            labScenario.setText("Aucun scénario sélectionné");
            return;
        }
        labScenario.setText("Scénario : " + chScenario.getName());

        ArrayList<Sommet> listeSommets = new ArrayList<>();


        var distances = chScenario.getDistance();
        var membres = chScenario.getMembres();

        for (String ville : distances.keySet()) {

            ArrayList<Integer> dist = distances.get(ville);

            int nbMembres = 0;
            if (membres.containsKey(ville)) {
                nbMembres = membres.get(ville).size();
            }

            Sommet sommet = new Sommet(ville, nbMembres, dist);
            listeSommets.add(sommet);
        }

        table.getItems().setAll(listeSommets);
    }
}
