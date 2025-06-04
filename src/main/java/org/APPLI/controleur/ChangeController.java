package org.APPLI.controleur;

import org.APPLI.vue.HBoxRoot;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;

public class ChangeController implements  EventHandler<ActionEvent>{
    

    @Override
    public void handle(ActionEvent event) {
        

        if (event.getSource() instanceof RadioMenuItem) {
            if (((MenuItem) event.getSource()).getUserData().equals(0)) {
                HBoxRoot.getStackPane().getChildren().get(0).setVisible(true);
                HBoxRoot.getStackPane().getChildren().get(1).setVisible(false);
            } else {
                HBoxRoot.getStackPane().getChildren().get(0).setVisible(false);
                HBoxRoot.getStackPane().getChildren().get(1).setVisible(true);
            }
        }
    }
}
