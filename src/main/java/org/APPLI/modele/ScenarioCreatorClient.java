package org.APPLI.modele;

public class ScenarioCreatorClient {
    public static void main(String[] args) {
        
        try {
            // scenarioCreator.createNewFile("sceneTest.txt");
            ScenarioCreator scenarioCreator = new ScenarioCreator();
            scenarioCreator.importTradeSheet("scenario_0.txt");
        } catch (Exception e) {
        }
    }
}
