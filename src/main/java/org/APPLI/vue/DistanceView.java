package org.APPLI.vue;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.APPLI.modele.Scenario;
import org.APPLI.modele.Sommet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Vue JavaFX affichant un tableau de distances et de membres par ville dans un scénario.
 */
public class DistanceView extends VBox {

    private Label labScenario;         // Label d'information sur le scénario actif
    private TableView<Sommet> table = new TableView<>();  // Table des villes avec leurs distances et membres
    private Scenario chScenario;      // Scénario actuellement affiché

    public DistanceView() {
        // Label affiché en haut
        labScenario = new Label("Aucun scénario sélectionné");
        this.getChildren().add(labScenario);

        // Titre au-dessus du tableau
        Label title = new Label("Chemins");
        this.getChildren().add(title);
        this.setSpacing(5); // Espacement vertical entre les éléments

        // ===== Colonne 1 : nom de la ville =====
        TableColumn<Sommet, String> ville = new TableColumn<>("Ville");
        ville.setCellValueFactory(new PropertyValueFactory<>("name")); // correspond au champ "name" de Sommet
        ville.setPrefWidth(150);

        // ===== Colonne 2 : membres présents dans la ville =====
        TableColumn<Sommet, String> membres = new TableColumn<>("Membres");

        membres.setCellValueFactory(cellData -> {
            String villeNom = cellData.getValue().getName();
            if (chScenario != null && chScenario.getMembres().containsKey(villeNom)) {
                List<String> listeMembres = chScenario.getMembres().get(villeNom);
                if (listeMembres != null && !listeMembres.isEmpty()) {
                    // On garde seulement les membres "actifs" (présents dans le scénario)
                    List<String> membresFiltres = listeMembres.stream()
                            .filter(membre -> chScenario.getAllMembre().contains(membre))
                            .collect(Collectors.toList());

                    String noms = String.join(", ", membresFiltres);
                    return new javafx.beans.property.SimpleStringProperty(noms);
                }
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        membres.setPrefWidth(300);

        // Ajout des colonnes à la table
        table.getColumns().addAll(ville, membres);
        this.getChildren().add(table);
    }

    /**
     * Met à jour le scénario à afficher et rafraîchit la table.
     */
    public void setScenario(Scenario scenario) {
        System.out.println("DistanceView: setScenario appelé avec " + (scenario == null ? "null" : scenario.getName()));
        this.chScenario = scenario;
        updateTable();
    }

    /**
     * Met à jour dynamiquement la table selon le scénario courant.
     */
    private void updateTable() {
        table.getItems().clear(); // Vide l'ancienne liste

        if (chScenario == null) {
            labScenario.setText("Aucun scénario sélectionné");
            return;
        }

        // Met à jour le label
        labScenario.setText("Scénario : " + chScenario.getName());

        ArrayList<Sommet> listeSommets = new ArrayList<>();

        // Accès aux données du modèle
        Map<String, ArrayList<Integer>> distances = chScenario.getDistance(); // Map : ville -> liste de distances
        TreeMap<String, ArrayList<String>> membres = chScenario.getMembres(); // Map : ville -> liste de membres

        // Création des objets `Sommet` à partir des données du scénario
        for (String ville : distances.keySet()) {
            ArrayList<Integer> dist = distances.get(ville);

            int nbMembres = 0;
            if (membres.containsKey(ville)) {
                nbMembres = (int) membres.get(ville).stream()
                        .filter(membre -> chScenario.getAllMembre().contains(membre))
                        .count();
            }

            Sommet sommet = new Sommet(ville, nbMembres, dist);
            listeSommets.add(sommet);
        }

        table.getItems().setAll(listeSommets); // Affichage dans la TableView
    }
}
