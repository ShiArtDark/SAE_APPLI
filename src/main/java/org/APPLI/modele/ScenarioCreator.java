package org.APPLI.modele;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

public class ScenarioCreator {

    private LinkedList<Trade> tradeList;
    private final TreeSet<String> members;

    public ScenarioCreator()  throws IOException{
        tradeList = new LinkedList<>();
        members = FileManager.exportSetMembre();

    }

    public void createNewFile(String _filename) throws IOException {

        if(_filename.length() < 2) {
            throw new IOException("Le nom doit être plus grand");
        }
        File newFile = new File("ressources/scenario/"+_filename);
        
        if(newFile.exists() && !newFile.isDirectory()) {
            throw new IOException("Ce fichier existe déjà");
        }
        newFile.createNewFile();
    }   

    public void writeInto(String _filename, String _content) throws IOException {

        File file = new File("ressources/scenario/"+_filename);

        if(!file.exists() && !file.isDirectory()) {
            throw new IOException("Ce n'est pas un fichier");
        }

        try (FileWriter fileWriter = new FileWriter("ressources/scenario/"+_filename, StandardCharsets.UTF_8)) {
            fileWriter.append(_content);
        }
    }

    public void addToTrade(String _sender, String _client)  throws ScenarioCreatorException, IOException {
        if(members.contains(_sender) && members.contains(_client)) {
            tradeList.add(new Trade(tradeList.size()+1, _sender, FileManager.getVilleByMembre(_sender), _client, FileManager.getVilleByMembre(_client)));
        } else {
            throw new ScenarioCreatorException("Le livreur ou le client n'est pas correcte");
        }
    }

    public void removeTrade(int _index) throws ScenarioCreatorException {
        if (_index > tradeList.size() || _index < 0) {
            throw new ScenarioCreatorException("Vous tentez de supprimer une ligne inexistante");
        }
        for (int i = _index; i < tradeList.size(); i++) {
            tradeList.get(i).decrementIndex(); // On décrémente tout les index de 1
        }
        tradeList.remove(_index); // On supprime ensuite la ligne
    }

    public void importTradeSheet(String _filename) throws IOException {
        
        File file = new File("ressources/scenario/"+_filename);
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" -> ");
                tradeList.add(new Trade(tradeList.size()+1, line[0],  FileManager.getVilleByMembre(line[0]),line[1], FileManager.getVilleByMembre(line[1])));
            }
        }
    }

    public void resetTradeList() {
        tradeList.clear();
    }

    public String tradeToString() {
        String tradeSheet = "";
        for(Trade trade : tradeList) {
            tradeSheet+=trade+"\n";
        }
        return tradeSheet;
    }

    public void writeScenario(String _filename) throws IOException{
        writeInto(_filename, tradeToString());
    }

    public LinkedList<Trade> getTradeList() {
        return tradeList;
    }
}
