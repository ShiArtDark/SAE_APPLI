package org.APPLI.vue;

import org.APPLI.controleur.Controleur;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class HBoxRoot extends VBox {



    private static MenuMenuBar menuMenuBar;
    private static HBoxVue hBoxVue;
    private static ScenarioCreatorView scenarioCreatorView;
    private static Controleur controleur;
    private static StackPane stackPane;

    public HBoxRoot() {
        super(10);
        menuMenuBar =new MenuMenuBar();
        controleur = new Controleur();
        stackPane = new StackPane();
        
        
        HBox principale = new HBox(10);
        
        VBox leftPane = new VBox(controleur.getVilleListView(), controleur.getDistanceView());
        VBox rightPane = new VBox(controleur.getOutputView());
        scenarioCreatorView = new ScenarioCreatorView();
        scenarioCreatorView.setVisible(false);
        principale.getChildren().addAll(leftPane, rightPane);
        
        scenarioCreatorView.setAccessibleText("creatorView"); 
        principale.setAccessibleText("mainWindow");
        
        stackPane.getChildren().addAll(principale, scenarioCreatorView);

        this.getChildren().addAll(controleur.getMenuMenuBar(), stackPane);
    }

    public static Controleur getControleur() {
        return controleur;
    }



    public static MenuMenuBar getMenuBar() {
        return menuMenuBar;
    }
    public static HBoxVue getHboxVue(){
        return hBoxVue;
    }
    public static ScenarioCreatorView getCreator()  {
        return scenarioCreatorView;
    }
    public static StackPane getStackPane() {
        return stackPane;
    }



    


}

