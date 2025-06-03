package org.APPLI.vue;

import javafx.scene.control.*;
import org.APPLI.modele.Scenario;
import org.APPLI.modele.FileManager;
import org.APPLI.modele.Graphe;

import java.io.File;

/**
 * MenuMenuBar : barre de menus principale de l'application
 * Permet de sélectionner un scénario et un algorithme à exécuter.
 * Utilise des interfaces pour notifier les écouteurs (listeners).
 */
public class MenuMenuBar extends MenuBar {

    /**
     * Interface pour écouter la sélection d'un scénario.
     */
    public interface ScenarioSelectionListener {
        void onScenarioSelected(Scenario scenario);
    }

    /**
     * Interface pour écouter la sélection d'un algorithme.
     */
    public interface AlgoSelectionListener {
        void onAlgoSelected(int option, int k);
    }

    // Références aux listeners externes
    private ScenarioSelectionListener scenarioListener;
    private AlgoSelectionListener algoListener;

    /**
     * Définit le listener pour la sélection de scénario.
     */
    public void setScenarioSelectionListener(ScenarioSelectionListener listener) {
        this.scenarioListener = listener;
    }

    /**
     * Définit le listener pour la sélection d'algorithme.
     */
    public void setAlgoSelectionListener(AlgoSelectionListener listener) {
        this.algoListener = listener;
    }

    /**
     * Constructeur : crée les menus "Scénarios" et "Algorithmes"
     */
    public MenuMenuBar() {
        try {
            // === Menu Scénarios ===
            File scenarioFolder = new File("ressources/scenario");
            ToggleGroup scegroup = new ToggleGroup();  // Groupe pour sélection radio
            Menu scenarioMenu = new Menu("Scénarios");

            File[] files = scenarioFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        String scenarioName = file.getName();

                        RadioMenuItem scenarioItem = new RadioMenuItem(scenarioName);
                        scenarioItem.setToggleGroup(scegroup);

                        // Au clic, charge le scénario et notifie le listener
                        scenarioItem.setOnAction(e -> {
                            try {
                                Scenario selectedScenario = FileManager.getScenario(scenarioName);
                                System.out.println("Scénario sélectionné : " + selectedScenario.getName());
                                if (scenarioListener != null) {
                                    scenarioListener.onScenarioSelected(selectedScenario);
                                }
                            } catch (Exception ex) {
                                System.out.println("Erreur chargement scénario : " + ex.getMessage());
                            }
                        });

                        scenarioMenu.getItems().add(scenarioItem);
                    }
                }
            }
            this.getMenus().add(scenarioMenu);

            // === Menu Algorithmes ===
            Menu algoMenu = new Menu("Algorithmes");

            MenuItem triFirst = new MenuItem("Tri Topologique - Premier élément");
            MenuItem triDistance = new MenuItem("Tri Topologique - Distance minimale");
            MenuItem meilleuresSolutions = new MenuItem("Meilleures Solutions (k=5)");
            MenuItem meilleuresSolutionsCustom = new MenuItem("Meilleures Solutions (k personnalisé)");

            algoMenu.getItems().addAll(
                    triFirst,
                    triDistance,
                    new SeparatorMenuItem(),
                    meilleuresSolutions,
                    meilleuresSolutionsCustom
            );

            // Définition des actions pour chaque menu d'algorithme
            triFirst.setOnAction(e -> notifyAlgoSelected(Graphe.FIRST, 0));
            triDistance.setOnAction(e -> notifyAlgoSelected(Graphe.DISTANCE, 0));
            meilleuresSolutions.setOnAction(e -> notifyAlgoSelected(-1, 5));

            meilleuresSolutionsCustom.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("10");
                dialog.setTitle("Nombre de solutions");
                dialog.setHeaderText("Combien de solutions voulez-vous ?");
                dialog.setContentText("Entrez k :");

                dialog.showAndWait().ifPresent(result -> {
                    try {
                        int k = Integer.parseInt(result);
                        if (k > 0) {
                            notifyAlgoSelected(-1, k);
                        } else {
                            showAlert("Erreur", "k doit être un nombre positif");
                        }
                    } catch (NumberFormatException ex) {
                        showAlert("Erreur", "Veuillez entrer un nombre valide");
                    }
                });
            });

            this.getMenus().add(algoMenu);

        } catch (Exception e) {
            System.out.println("Erreur dans MenuMenuBar : " + e.getMessage());
        }
    }

    /**
     * Notifie le listener qu'un algorithme a été sélectionné.
     * @param option id de l'algorithme (-1 pour meilleures solutions)
     * @param k paramètre optionnel (ex: nombre de solutions)
     */
    private void notifyAlgoSelected(int option, int k) {
        if (algoListener != null) {
            algoListener.onAlgoSelected(option, k);
        }
    }

    /**
     * Affiche une boîte d'alerte avec un message d'erreur.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
