package org.APPLI.vue;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.APPLI.modele.Scenario;

/**
 * HBoxVue : conteneur horizontal principal pour les vues secondaires
 * Organise l'affichage en deux parties : à gauche la liste des villes et distances,
 * à droite la zone d'affichage des résultats.
 */
public class HBoxVue extends HBox {

    // Vue affichant la liste des villes
    private VilleListView villeListView;
    // Vue affichant les distances et membres associés
    private DistanceView distanceView;
    // Vue affichant les résultats et messages de sortie
    private OutputView outputView;

    /**
     * Constructeur : initialise les vues et organise la mise en page
     */
    public HBoxVue() {
        super(10);  // Espacement horizontal

        // Instanciation des vues secondaires
        villeListView = new VilleListView();
        distanceView = new DistanceView();
        outputView = new OutputView();

        // Création du panneau gauche : liste villes + distances
        VBox leftPane = new VBox(villeListView, distanceView);

        // Création du panneau droit : affichage des résultats
        VBox rightPane = new VBox(outputView);

        // Ajout des deux panneaux (gauche et droite) dans la HBox principale
        this.getChildren().addAll(leftPane, rightPane);
    }

    /**
     * Mise à jour des vues en fonction du scénario sélectionné
     * @param scenario scénario courant (peut être null)
     */
    public void setScenario(Scenario scenario) {
        System.out.println("HBoxVue reçoit scénario : " + (scenario != null ? scenario.getName() : "null"));

        // Mise à jour de la liste des villes avec les données du scénario
        villeListView.updateWithScenario(scenario);

        // Mise à jour des distances/membres affichés selon le scénario
        distanceView.setScenario(scenario);
    }

    /**
     * Accesseur à la vue des résultats
     */
    public OutputView getOutputView() {
        return outputView;
    }

    /**
     * Accesseur à la vue de la liste des villes
     */
    public VilleListView getVilleListView() {
        return villeListView;
    }

    /**
     * Accesseur à la vue des distances/membres
     */
    public DistanceView getDistanceView() {
        return distanceView;
    }
}
