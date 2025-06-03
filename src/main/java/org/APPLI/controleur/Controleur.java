/*package org.APPLI.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioMenuItem;
import org.APPLI.modele.Scenario;
import org.APPLI.vue.DistanceView;
import org.APPLI.vue.MenuMenuBar;
import org.APPLI.vue.OutputView;
import org.APPLI.vue.VilleListView;

public class Controleur implements EventHandler<ActionEvent>, MenuMenuBar.AlgoSelectionListener {

    private VilleListView villeListView;
    private DistanceView distanceView;
    private MenuMenuBar menuMenuBar;
    private OutputView outputView;

    public Controleur(MenuMenuBar menuMenuBar, VilleListView villeListView, DistanceView distanceView
                      , OutputView outputView) {
        this.menuMenuBar = menuMenuBar;
        this.villeListView = villeListView;
        this.distanceView = distanceView;

        this.outputView = outputView;

        menuMenuBar.setScenarioSelectionListener(scenario -> {
            this.handle(new ActionEvent(scenario, null));
        });

        menuMenuBar.setAlgoSelectionListener(this);
    }

    public Controleur() {

    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() instanceof Scenario scenario) {
            System.out.println(">> handle() - Scénario sélectionné : " + scenario.getName());


            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);
        } else if (event.getSource() instanceof RadioMenuItem item) {

            Scenario scenario = (Scenario) item.getUserData();
            System.out.println(">> handle() - RadioMenuItem cliqué : " + scenario.getName());


            distanceView.setScenario(scenario);
            villeListView.updateWithScenario(scenario);
        }
    }

    @Override
    public void onAlgoSelected(int option) {
        outputView.clear();
        outputView.println("Algorithme sélectionné : " + option);

        // Ici tu appelles ton graphe ou méthode pour chaque algo (à toi d’intégrer selon ta classe Graphe)
        switch (option) {
            case 0:
                outputView.println("Tri Topologique Classique lancé");
                // appel graphe.triTopologique(0) ?
                break;
            case 1:
                outputView.println("Tri Topologique Distance Minimale lancé");
                // appel graphe.triTopologique(1) ?
                break;
            case 2:
                outputView.println("Tri Topologique Récursif lancé");
                // appel graphe.triTopologiqueRecursif() ?
                break;
            case 3:
                outputView.println("Élagage lancé");
                // appel graphe.elagage() ?
                break;
            default:
                outputView.println("Algorithme inconnu");
        }
    }
}*/
