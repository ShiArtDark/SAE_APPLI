package org.APPLI.vue;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import org.APPLI.modele.Sommet;

import java.util.ArrayList;

public class DistanceView extends VBox {

    public DistanceView() {
        Label title = new Label("Distances depuis la ville sélectionnée");
        this.getChildren().add(title);
        this.setSpacing(5);
    }

    public void afficherDistances(Sommet ville, ArrayList<Sommet> villes) {
        this.getChildren().clear();
        this.getChildren().add(new Label("Distances depuis " + ville.getName()));

        for (Sommet s : villes) {
            if (!s.equals(ville)) {
                Integer dist = ville.getDistance().get(s.getId());
                String text = s.getName() + " : " + (dist != null ? dist : "N/A");
                this.getChildren().add(new Label(text));
            }
        }
    }
}
