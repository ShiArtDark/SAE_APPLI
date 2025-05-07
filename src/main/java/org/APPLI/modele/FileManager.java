package org.APPLI.modele;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FileManager {

    Set<String> chNomVille;
    TreeMap<String, ArrayList<Integer>> chDistance;

    public FileManager() {
        chDistance = exportVille();
        chNomVille = chDistance.keySet();
    }

    public TreeMap<String, ArrayList<Integer>> exportVille() {
        TreeMap<String, ArrayList<Integer>> distances = new TreeMap<String, ArrayList<Integer>>();
        try {
            File file = new File("ressources/data/distances.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                ArrayList<Integer> tempDistance = new ArrayList<Integer>();
                
                for (int i = 1; i<line.length;i++) {
                    tempDistance.add(Integer.parseInt(line[i]));
                }
                distances.put(line[0], tempDistance);
            }
        } catch (Exception e) {

        }
        return distances;
    }


}
