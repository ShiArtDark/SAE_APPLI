package org.APPLI.vue;

import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import org.APPLI.modele.Sommet;

import java.util.ArrayList;

public class VilleListView extends VBox {

    private ListView<Sommet> listView;

    public VilleListView(ArrayList<Sommet> villes) {
        Label title = new Label("Liste des villes");
        listView = new ListView<>();
        listView.getItems().addAll(villes);

        this.getChildren().addAll(title, listView);
        this.setSpacing(5);
    }

    public Sommet getSelectedVille() {
        return listView.getSelectionModel().getSelectedItem();
    }

    public ListView<Sommet> getListView() {
        return listView;
    }
}
