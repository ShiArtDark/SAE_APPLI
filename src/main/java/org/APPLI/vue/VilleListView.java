package org.APPLI.vue;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.APPLI.modele.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * VilleListView : affiche la liste des villes et leurs membres pour un scénario donné.
 */
public class VilleListView extends VBox {

    private ListView<String> listView;  // Liste affichant les villes et membres

    public VilleListView() {
        Label title = new Label("Liste des villes et membres");
        listView = new ListView<>();
        this.getChildren().addAll(title, listView);
        this.setSpacing(5);  // Espace entre le label et la liste
    }

    /**
     * Met à jour la liste avec les villes et membres du scénario passé en paramètre.
     */
    public void updateWithScenario(Scenario scenario) {
        System.out.println("updateWithScenario appelé sur " + this + " avec scénario : " + (scenario != null ? scenario.getName() : "null"));

        listView.getItems().clear(); // Vide la liste avant mise à jour

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

        // Récupère la liste de tous les membres actifs dans ce scénario
        List<String> membresScenario = new ArrayList<>(scenario.getAllMembre());

        // Parcourt chaque ville et ses membres
        for (Map.Entry<String, ArrayList<String>> entry : membresParVille.entrySet()) {
            String ville = entry.getKey();
            for (String membre : entry.getValue()) {
                // Ajoute à la liste uniquement si le membre appartient au scénario
                if (membresScenario.contains(membre)) {
                    String ligne = ville + " -> " + membre;
                    System.out.println("Ajout dans la liste : " + ligne);
                    listView.getItems().add(ligne);
                }
            }
        }

        System.out.println("Nombre total d'éléments affichés : " + listView.getItems().size());
    }

    /**
     * Retourne l'élément actuellement sélectionné dans la liste.
     */
    public String getSelectedItem() {
        return listView.getSelectionModel().getSelectedItem();
    }

    /**
     * Accès direct à la ListView (utile pour gérer des événements ou personnalisation).
     */
    public ListView<String> getListView() {
        return listView;
    }
}
