package org.APPLI.modele;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class FileManager {

    /**
     * Cette méthode permet d'exporter toutes les villes et de les formater dans une structure de données de types TreeMap
     * Comme clé, le nom de la ville et la valeur une liste d'entier correspondant à la distance entre chaque ville.
     * @return TreeMap<String, ArrayList<Integer>()> distance
     * @throws IOException
     */
    public static TreeMap<String, ArrayList<Integer>> exportVille() throws IOException {

        TreeMap<String, ArrayList<Integer>> distances = new TreeMap<>();
        

        File file = new File("ressources/data/distances.txt");
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                
                ArrayList<Integer> tempDistance = new ArrayList<>();
                for (int i = 1; i < line.length; i++) {
                    tempDistance.add(Integer.valueOf(line[i]));
                }
                
                distances.put(line[0], tempDistance);
            }
        }

        return distances;
    }

    /**
     * Cette méthode permet d'exporter les différements membres du fichier textes ressources "membres_appli.txt"
     * Permet pour chaque de mettre une clé, une ville, et tout les utilisateur dans une liste
     * @return TreeMap<String,ArrayList<String>> pour chaque ville tout ces utilisateurs.
     * @throws IOException
     */
    public static TreeMap<String, ArrayList<String>> exportMembre()  throws IOException{

        TreeMap<String, ArrayList<String>> membres = new TreeMap<>();
        
        File file = new File("ressources/data/membres_APPLI.txt");
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                
                if (!membres.containsKey(line[1])) {
                    membres.put(line[1],new ArrayList<>()) ;
                }
                membres.get(line[1]).add(line[0]);
                
          
             }
        } catch (IOException e) {
            throw new IOException("Le ficier n'existe pas");
        }
        return membres;
    }

    public static TreeSet<String> exportAllMember(String _scenario) throws IOException {
        TreeSet<String> res = new TreeSet<>();

        File file = new File("ressources/scenario/"+_scenario);
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" -> ");
                
                if (!res.contains(line[0])) {
                    res.add(line[0]);
                }
                
                if (!res.contains(line[1])) {
                    res.add(line[1]);
                }
            }
        }

        return res;
    }

    /**
     * Cette méthode permet de désigner pour chaque ville un identifiant dans l'ordre d'apparition des distances
     * @return TreeMap<String, Integer> exp, Clé NomVille => Identitifiant numérique
     */
    public static TreeMap<String, Integer> exportVilleID() throws IOException {
        TreeMap<String, Integer> exp = new TreeMap<>();

        File file = new File("ressources/data/distances.txt");
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            int i = 0;
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                exp.put(line[0], i++);
            }
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
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            int i = 0;
            while(scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                res.put(i++, line[0]);
            }
        }

        return res;
    }

    /**
     * Cette méthode nous permettra de convertir les scénarios sous forme de graphe
     */
    public static TreeMap<Sommet, ArrayList<Sommet>> toGraph(String _scenario) throws Exception{
        TreeMap<Sommet, ArrayList<Sommet>> res = new TreeMap<>(Sommet::compareTo);
        TreeMap<String, ArrayList<Integer>> distance = exportVille();
        TreeMap<String, Integer> villeID = exportVilleID();

        File file = new File("ressources/scenario/"+_scenario);
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            Sommet velPlus = new Sommet("Velizy", 0, distance.get("Velizy"), exportVilleID());
            Sommet velMoins = new Sommet("Velizy", 1, distance.get("Velizy"), exportVilleID());
            
            res.put(velPlus, new ArrayList<>());
            res.put(velMoins, new ArrayList<>());
            
            
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(" -> ");
                
                
                String livVille = getVilleByMembre(line[0]); // On extrait le nom de la ville livrante
                String recVille = getVilleByMembre(line[1]); // On extrait le nom de la ville recevante
                if (livVille.compareTo(recVille) != 0) {
                    Sommet livSommet = new Sommet(livVille, 0, distance.get(livVille), villeID); // On crée le sommet correspondant
                    Sommet recSommet = new Sommet(recVille, 1, distance.get(recVille), villeID); // On crée le sommet correspondant
                    if (res.containsKey(livSommet) && res.get(livSommet).contains(recSommet)){ // Pour éviter les différents doublons.
                        continue;
                    }
                    
                    if (!res.containsKey(livSommet)) { // Si elle n'est pas existante on crée ce nouveau sommet
                        res.put(livSommet, new ArrayList<>());
                        ArrayList<Sommet> veliz = res.get(velPlus);
                        veliz.add(livSommet);
                        res.put(velPlus, veliz);    
                        
                    }
                
                
                    
                    
                    if (!res.containsKey(recSommet)) { // Si il n'existe pas, on crée une nouvelle entrée
                        ArrayList<Sommet> tempRec = new ArrayList<>();
                        tempRec.add(velMoins);
                        res.put(recSommet, tempRec);
                    }
                    res.get(livSommet).add(recSommet);
                
                }
            }   
        }
        return res;
    }

    public static String getVilleByMembre(String _member) throws IOException{
        TreeMap<String,ArrayList<String>> membres = exportMembre();

        for (String ville : membres.keySet()) {
            if (membres.get(ville).contains(_member)) {
                return ville;
            }
        }
        throw new IOException("City Not Found");
    }

    public static Scenario getScenario(String _path) throws Exception{
        TreeMap<String, ArrayList<Integer>> distances = exportVille();
        TreeMap<String, ArrayList<String>> membre = exportMembre();
        TreeSet<String> allMember = exportAllMember(_path);
        TreeMap<String, Integer> villeID = exportVilleID();
        TreeMap<Integer, String> IDVille = exportIDVille();
        Graphe map= new Graphe(toGraph(_path));
        

        return new Scenario(_path, distances, membre, allMember, villeID, IDVille, map);
    }

    public static TreeSet<String> exportSetMembre() throws IOException {
        TreeSet<String> membres = new TreeSet<>();

        File file = new File("ressources/data/membres_APPLI.txt");
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while(scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");

                membres.add(line[0]);
            }
        } catch (IOException e) {
            throw new IOException("Le fichier n'existe pas");
        }
        return membres;
    }



}
