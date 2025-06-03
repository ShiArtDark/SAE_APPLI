package org.APPLI.vue;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.APPLI.controleur.Controleur;

/**
 * HBoxRoot : la racine de l'interface graphique (Vue principale)
 * Structure l'affichage en une VBox contenant un menu + une HBox centrale
 */
public class HBoxRoot extends VBox {

    private Controleur controleur; // Référence unique au contrôleur de l'application

    public HBoxRoot() {
        super(10);  // Espacement vertical entre les éléments enfants de la VBox (VBoxRoot)

        // Initialisation du contrôleur qui gère les vues et les actions utilisateur
        controleur = new Controleur();

        // ===== Zone principale centrale =====
        HBox principale = new HBox(10); // HBox avec espacement horizontal de 10px

        // Partie gauche : Liste des villes + tableau des distances
        VBox leftPane = new VBox(
                controleur.getVilleListView(),     // Vue des villes (liste)
                controleur.getDistanceView()       // Vue des distances et membres
        );

        // Partie droite : zone de sortie des résultats (algorithmes)
        VBox rightPane = new VBox(
                controleur.getOutputView()         // Vue des résultats (TextArea)
        );

        // Ajout des deux panneaux à la HBox principale
        principale.getChildren().addAll(leftPane, rightPane);

        // Ajout du menu en haut et de la zone principale en dessous
        this.getChildren().addAll(
                controleur.getMenuMenuBar(),   // Barre de menu en haut
                principale                     // Contenu principal en dessous
        );
    }

    /**
     * Accesseur au contrôleur (utile pour test ou interactions externes)
     */
    public Controleur getControleur() {
        return controleur;
    }
}
