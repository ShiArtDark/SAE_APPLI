package org.APPLI.vue;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.APPLI.modele.Scenario;

import java.util.ArrayList;
import java.util.Map;

public class VilleListView extends VBox {

    private ListView<String> listView;

    public VilleListView() {
        Label title = new Label("Liste des villes et membres");
        listView = new ListView<>();
        this.getChildren().addAll(title, listView);
        this.setSpacing(5);
    }

    public void updateWithScenario(Scenario scenario) {
        System.out.println("updateWithScenario appelé sur " + this + " avec scenario : " + (scenario != null ? scenario.getName() : "null"));

        listView.getItems().clear();

        if (scenario == null) {
            System.out.println("Scénario null (aucun scénario sélectionné).");
            return;
        }

        Map<String, ArrayList<String>> membresParVille = scenario.getMembres();
        if (membresParVille == null || membresParVille.isEmpty()) {
            System.out.println("Aucun membre trouvé dans ce scénario.");
            listView.getItems().add("Aucun membre trouvé.");
            return;
        }

        // Récupère uniquement les membres actifs dans ce scénario
        var membresScenario = scenario.getAllMembre();

        for (Map.Entry<String, ArrayList<String>> entry : membresParVille.entrySet()) {
            String ville = entry.getKey();
            for (String membre : entry.getValue()) {
                // Ajoute uniquement si le membre appartient au scénario
                if (membresScenario.contains(membre)) {
                    String ligne = ville + " -> " + membre;
                    System.out.println("Ajout dans la liste : " + ligne);
                    listView.getItems().add(ligne);
                }
            }
        }

        System.out.println("Nombre total d'éléments affichés : " + listView.getItems().size());
    }

    public String getSelectedItem() {
        return listView.getSelectionModel().getSelectedItem();
    }

    public ListView<String> getListView() {
        return listView;
    }
}