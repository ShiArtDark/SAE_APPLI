package org.APPLI.vue;

import javafx.scene.layout.VBox;

public class HBoxRoot extends VBox {



    private static MenuMenuBar menuMenuBar;
    private static HBoxVue hBoxVue;
    private static ScenarioCreatorView scenarioCreatorView;


    public HBoxRoot() {
        super(10);

        // menuMenuBar = new MenuMenuBar();
        // hBoxVue = new HBoxVue();


        // menuMenuBar.setScenarioSelectionListener(scenario -> {
        //     hBoxVue.setScenario(scenario);
        // });
        scenarioCreatorView =new ScenarioCreatorView();

        this.getChildren().addAll(scenarioCreatorView);
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

    


}
