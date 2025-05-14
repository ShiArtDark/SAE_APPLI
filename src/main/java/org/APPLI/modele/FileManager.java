package org.APPLI.modele;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class FileManager {

    /**
     * Cette méthode permet d'exporter toutes les villes et de les formater dans une structure de données de types TreeMap 
     * Comme clé, le nom de la ville et la valeur une liste d'entier correspondant à la distance entre chaque ville.
     * @return TreeMap<String, ArrayList<Integer>()> distance
     * @throws IOException
     */
    public static TreeMap<String, ArrayList<Integer>> exportVille() throws IOException {
        TreeMap<String, ArrayList<Integer>> distances = new TreeMap<String, ArrayList<Integer>>();
        
        File file = new File("ressources/data/distances.txt");
        Scanner scanner = new Scanner(file, "UTF-8");

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            
            ArrayList<Integer> tempDistance = new ArrayList<>();
            for (int i = 1; i < line.length; i++) {
                tempDistance.add(Integer.valueOf(line[i]));
            }

            distances.put(line[0], tempDistance);
        }
        scanner.close();
        
        return distances;
    }

    /**
     * Cette méthode permet d'exporter les différements membres du fichier textes ressources "membres_appli.txt"
     * Permet pour chaque de mettre une clé, une ville, et tout les utilisateur dans une liste
     * @return TreeMap<String,ArrayList<String>> pour chaque ville tout ces utilisateurs.
     * @throws IOException
     */
    public static TreeMap<String, ArrayList<String>> exportMembre()  throws IOException{
        TreeMap<String, ArrayList<String>> membres = new TreeMap<String, ArrayList<String>>();
        
        File file = new File("ressources/data/membres_APPLI.txt");
        Scanner scanner = new Scanner(file, "UTF-8");

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");

            if (!membres.containsKey(line[1])) {
                membres.put(line[1],new ArrayList<>()) ;
            }
            membres.get(line[1]).add(line[0]);

        }
        return membres;
    }

    /**
     * Cette méthode permet de désigner pour chaque ville un identifiant dans l'ordre d'apparition des distances
     * @return TreeMap<String, Integer> exp, Clé NomVille => Identitifiant numérique
     */
    public static TreeMap<String, Integer> exportVilleID() throws IOException {
        TreeMap<String, Integer> exp = new TreeMap<>();

        File file = new File("ressources/data/distances.txt");
        Scanner scanner = new Scanner(file, "UTF-8");
        int i = 0;
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            exp.put(line[0], i++);
        }
        return exp;
    }

}
