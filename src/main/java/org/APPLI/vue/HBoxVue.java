package org.APPLI.vue;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.APPLI.modele.Scenario;

public class HBoxVue extends HBox {

    private VilleListView villeListView;
    private DistanceView distanceView;
    private OutputView outputView;

    public HBoxVue() {
        super(10);

        villeListView = new VilleListView();
        distanceView = new DistanceView();
        outputView = new OutputView();

        VBox leftPane = new VBox(villeListView, distanceView);
        VBox rightPane = new VBox(outputView);

        this.getChildren().addAll(leftPane, rightPane);
    }

    public void setScenario(Scenario scenario) {
        System.out.println("HBoxVue reçoit scénario : " + (scenario != null ? scenario.getName() : "null"));
        villeListView.updateWithScenario(scenario);
        distanceView.setScenario(scenario);
    }

    public OutputView getOutputView() {
        return outputView;
    }

    public VilleListView getVilleListView() {
        return villeListView;
    }

    public DistanceView getDistanceView() {
        return distanceView;
    }
}