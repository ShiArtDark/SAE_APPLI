package org.APPLI.modele;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class FileManager {


    public TreeMap<String, ArrayList<Integer>> exportVille() {
        TreeMap<String, ArrayList<Integer>> distances = new TreeMap<String, ArrayList<Integer>>();
        try {
            File file = new File("ressources/data/distances.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                System.out.println(line[0]);
                System.out.println(line.length);
            }

        } catch (Exception e) {

        }
        return distances;
    }
    public TreeMap<String, ArrayList<String>> getmembres_APPLI() {
        TreeMap<String, ArrayList<String>> membres = new TreeMap<String, ArrayList<String>>();
        try {
            File file = new File("ressources/data/membres_APPLI.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");

                if (!membres.containsKey(line[1])) {
                    membres.put(line[1],new ArrayList<String>()) ;
                }
                membres.get(line[1]).add(line[0]);

            }

        } catch (Exception e) {

        }return membres;
    }
}
