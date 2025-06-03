package org.APPLI.modele;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

public class ScenarioCreator {

    private LinkedList<String> tradeList;
    private final TreeSet<String> members;

    public ScenarioCreator()  throws IOException{
        tradeList = new LinkedList<>();
        members = FileManager.exportSetMembre();

    }
    

    public void createNewFile(String _filename) throws IOException {
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

        try (FileWriter fileWriter = new FileWriter("ressources/scenario/"+_filename, true)) {
            fileWriter.append(_content);
        }
    }

    public void addToTrade(String _sender, String _client)  throws ScenarioCreatorException {
        if(members.contains(_sender) && members.contains(_client)) {
            tradeList.add(_sender+" -> "+_client);
        } else {
            throw new ScenarioCreatorException("Le livreur ou le client n'est pas correcte");
        }
    }

    public void removeTrade(int _index) throws ScenarioCreatorException {
        if (_index > tradeList.size() || _index < 0) {
            throw new ScenarioCreatorException("Vous tentez de supprimer une ligne inexistante");
        }
        tradeList.remove(_index);
    }

    public void importTradeSheet(String _filename) throws IOException {
        
        File file = new File("ressources/scenario/"+_filename);
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                tradeList.add(line);
            }
        }
    }

    public void resetTradeList() {
        tradeList.clear();
    }

    public String tradeToString() {
        String tradeSheet = "";
        for(String trade : tradeList) {
            tradeSheet+=trade+"\n";
        }
        return tradeSheet;
    }

}
