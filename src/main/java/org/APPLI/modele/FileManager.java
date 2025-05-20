package org.APPLI.modele;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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


    /**
     * Cette méthode est identique à celle de exportVILLEID cependant le dictionnaire attendu renvoie pour clé un id et la ville en valeur
     * @return TreeMap<Integer, String> res Id => Ville
     */
    public static TreeMap<Integer, String> exportIDVille() throws IOException {
        TreeMap<Integer, String> res = new TreeMap<>();

        File file = new File("ressources/data/distances.txt");
        Scanner scanner = new Scanner(file, "UTF-8");
        int i = 0;
        while(scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            res.put(i++, line[0]);
        }
        return res;
    }

    /**
     * Cette méthode nous permettra de convertir les scénarios sous forme de graphe
     */

    // à optimiser en sorte qu'en un seul passage on puisse crée une seule et unique
    public static TreeMap<Sommet, ArrayList<Sommet>> toGraph(String _scenario) throws IOException {
        TreeMap<Sommet, ArrayList<Sommet>> res = new TreeMap<>(Sommet::compareTo);
        TreeMap<String,ArrayList<String>> membre = exportMembre(); // On recup les membres
        TreeMap<String, ArrayList<Integer>> distance = exportVille();

        File file = new File("ressources/scenario/"+_scenario);
        Scanner scanner = new Scanner(file, "UTF-8");
        Scanner scanner2 = new Scanner(file, "UTF-8");

        res.put(new Sommet("Velizy", 0, distance.get("Velizy"), exportVilleID()), new ArrayList<>()); // exportVilleID est un champ static

        res.put(new Sommet("Velizy", 1, distance.get("Velizy")), new ArrayList<>());

        while(scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" -> ");

            for (String ville : membre.keySet()) {
                if(membre.get(ville).contains(line[0])) { // création de la ville°

                    Sommet tempSommet = new Sommet(ville, 0, distance.get(ville));
                    if (!res.containsKey(tempSommet)) {
                        res.put(tempSommet, new ArrayList<>());
                        ArrayList<Sommet> vel = res.get(new Sommet("Velizy", 0));
                        vel.add(tempSommet);
                        res.put(new Sommet("Velizy", 0), vel);
                    }

                }
            }
            
        }

        while (scanner2.hasNextLine()) {
            String line[] = scanner2.nextLine().split(" -> ");

            for (String ville: membre.keySet()) {
                if(membre.get(ville).contains(line[1])) {
                    Sommet tempSommet = new Sommet(ville, 1, distance.get(ville));
                    for (String vi : membre.keySet()) {
                        if (membre.get(vi).contains(line[0])) {
                            ArrayList<Sommet> tempList = res.get(new Sommet(vi, 0));
                            tempList.add(tempSommet);
                            res.put(new Sommet(vi, 0), tempList);
                            
                        }
                    }

                    ArrayList<Sommet> lastList = new ArrayList<>();
                    lastList.add(new Sommet("Velizy", 1));
                    res.put(tempSommet, lastList);
                }
            }
        }

        scanner.close();
        return res;
    }



}
