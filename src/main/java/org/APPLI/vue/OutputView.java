package org.APPLI.vue;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.APPLI.modele.Sommet;
import org.APPLI.modele.Graphe;

import java.util.ArrayList;

public class OutputView extends VBox {

    private TextArea resultArea;
    private Label titleLabel;

    public OutputView() {
        super(10);

        titleLabel = new Label("Résultats des algorithmes");

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(20);
        resultArea.setPrefColumnCount(50);
        resultArea.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane(resultArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

        this.getChildren().addAll(titleLabel, scrollPane);
    }

    public void displayTriTopologique(ArrayList<Sommet> chemin, String algoName, int distance) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(algoName).append(" ===\n");
        sb.append("Distance totale: ").append(distance).append(" km\n");
        sb.append("Chemin: ");

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

    public void displayError(String error) {
        resultArea.setText("Erreur: " + error);
    }

    public void clear() {
        resultArea.clear();
    }
}