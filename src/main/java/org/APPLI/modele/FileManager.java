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
            return distances;
        } catch (Exception e) {

        }finally {
            return new TreeMap<String, ArrayList<Integer>>();
        }
    }
}
