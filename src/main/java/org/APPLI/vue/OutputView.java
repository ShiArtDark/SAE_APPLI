package org.APPLI.vue;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.APPLI.modele.Sommet;
import org.APPLI.modele.Graphe;

import java.util.ArrayList;

/**
 * OutputView : affiche les résultats des algorithmes dans une zone de texte.
 */
public class OutputView extends VBox {

    private Label titleLabel;    // Titre au-dessus de la zone de résultats
    private TextArea resultArea; // Zone de texte pour afficher les résultats

    public OutputView() {
        super(10); // Espacement vertical

        titleLabel = new Label("Résultats des algorithmes");

        resultArea = new TextArea();
        resultArea.setEditable(false);       // On ne peut pas modifier le texte manuellement
        resultArea.setPrefRowCount(20);      // Hauteur préférée (en lignes)
        resultArea.setPrefColumnCount(50);   // Largeur préférée (en colonnes)
        resultArea.setWrapText(true);        // Retour à la ligne automatique

        // ScrollPane pour rendre la zone de texte défilable si besoin
        ScrollPane scrollPane = new ScrollPane(resultArea);
        scrollPane.setFitToWidth(true);      // Le ScrollPane suit la largeur du parent
        scrollPane.setPrefHeight(400);       // Hauteur fixe

        this.getChildren().addAll(titleLabel, scrollPane);
    }

    /**
     * Affiche le résultat d’un tri topologique : chemin, distance et nom de l’algo.
     */
    public void displayTriTopologique(ArrayList<Sommet> chemin, String algoName, int distance) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(algoName).append(" ===\n");
        sb.append("Distance totale: ").append(distance).append(" km\n");
        sb.append("Chemin: ");

        // Convertit la liste de sommets en noms de villes
        ArrayList<String> cheminConverti = Graphe.convertChemin(chemin);
        for (int i = 0; i < cheminConverti.size(); i++) {
            sb.append(cheminConverti.get(i));
            if (i < cheminConverti.size() - 1) {
                sb.append(" → ");
            }
        }
        sb.append("\n\n");

        resultArea.setText(sb.toString());
    }

    /**
     * Affiche les meilleures solutions (plusieurs chemins) avec un nombre k.
     */
    public void displayMeilleuresSolutions(ArrayList<ArrayList<Sommet>> solutions, int k) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Meilleures Solutions (k=").append(k).append(") ===\n\n");

        if (solutions.isEmpty()) {
            sb.append("Aucune solution trouvée.\n");
        } else {
            for (int i = 0; i < solutions.size(); i++) {
                ArrayList<Sommet> chemin = solutions.get(i);
                ArrayList<String> cheminConverti = Graphe.convertChemin(chemin);

                sb.append("Solution ").append(i + 1).append(":\n");
                sb.append("Chemin: ");

                for (int j = 0; j < cheminConverti.size(); j++) {
                    sb.append(cheminConverti.get(j));
                    if (j < cheminConverti.size() - 1) {
                        sb.append(" → ");
                    }
                }
                sb.append("\n\n");
            }
        }

        resultArea.setText(sb.toString());
    }

    /**
     * Affiche un message d’erreur.
     */
    public void displayError(String error) {
        resultArea.setText("Erreur : " + error);
    }

    /**
     * Efface la zone de texte des résultats.
     */
    public void clear() {
        resultArea.clear();
    }
}
