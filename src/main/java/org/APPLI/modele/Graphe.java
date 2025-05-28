
package org.APPLI.modele;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graphe {
    // On va passer par des identifiants de villes 

    private final TreeMap<Sommet, TreeSet<Sommet>> sortant;
    private final TreeMap<Sommet, Integer> entrant;
    private final LinkedList<Sommet> source;
    private ArrayList<ArrayList<Sommet>> cheminRecursif;
    private ArrayList<Integer> indexRecursif;
    private final int minTheorical;
    private final int maxTheorical;
    private final float averageTheorical;

    private TreeMap<String, ArrayList<Integer>> distances;
    private TreeMap<String, Integer> villeID;
    private TreeMap<Integer, String> IDville;



    
    public static final int FIRST = 0;
    public static final int DISTANCE = 1;
    public static final int DEGREE = 2;
    public static final int MAX_DISTANCE = 3;


    // ======================== CONSTRUCTEUR ========================

    public Graphe (TreeMap<Sommet, ArrayList<Sommet>> _tab) throws Exception{
        sortant = new TreeMap<>(Sommet::compareTo);
        entrant = new TreeMap<>(Sommet::compareTo);
        source = new LinkedList<>();
        cheminRecursif = new ArrayList<>();
        indexRecursif = new ArrayList<>();

        distances = FileManager.exportVille();
        villeID = FileManager.exportVilleID();


        for (Sommet som : _tab.keySet()) {
            entrant.put(som, 0);
        }

        for (Sommet som : _tab.keySet()) {
            TreeSet<Sommet> tempSet = new TreeSet<>(Sommet::compareTo);

            for (Sommet voisin : _tab.get(som)) {
                tempSet.add(voisin);
                entrant.put(voisin, entrant.get(som)+1);
            }
            sortant.put(som, tempSet);
        }

        for (Sommet som : entrant.keySet()) {
            if(entrant.get(som) == 0) {
                source.add(som);
            }
        }
        minTheorical = GetDistance(triTopologique(DISTANCE));
        maxTheorical = GetDistance(triTopologique(MAX_DISTANCE));
        averageTheorical = (minTheorical+maxTheorical)/2;
    }

    // ======================== GET/SET ========================

    public TreeMap<Sommet, TreeSet<Sommet>> getSortant() {
        return sortant;
    }

    public TreeMap<Sommet, Integer> getEntrant() {
        return entrant;
    }

    public LinkedList<Sommet> getSource() {
        return source;
    }

    public int getAllSolutionsSize() {
        return cheminRecursif.size();
    }

    // ======================== Method ========================

    
    /**
     * La méthode du tri topologique ou l'algorithme de Bellman-Ford-Moore, consiste à modéliser sous forme de graphe
     * orienté sans cycle, un chemin à partir de source.
     * 
     * @return ArrayList<Sommet> une liste de sommet constituant le chemin.
     * @throws Exception
     */
    public ArrayList<Sommet> triTopologique(int _option) throws Exception {

        TreeMap<Sommet, Integer> degreeEntrant = new TreeMap<>(entrant);
        LinkedList<Sommet> tempSource = new LinkedList<>(source);
        ArrayList<Sommet> chemin = new ArrayList<>();
      
        while (!tempSource.isEmpty()) {

            // Décision d'un index
            int index = 0;
            switch (_option) {
                case 0: // First
                    index = 0;
                    break;
                case 1: // MinDistance
                    index = getMinIndexDistance(chemin, tempSource);
                    break;
                case 2: // MaxDegree
                    index = getMaxIndexEntrant(chemin, tempSource, degreeEntrant);
                    break;
                case 3: // MaxDistance (Utile pour plus tard... ce n'est pas très heuristique)
                    index = getMaxIndexDistance(chemin, tempSource); 
                    break;
                
            }

            Sommet s = tempSource.remove(index);
           
    
            for(Sommet v : sortant.get(s)) {
                degreeEntrant.put(v,degreeEntrant.get(v) -1);
                if (degreeEntrant.get(v) == 0) {
                    tempSource.add(v);
                }
            }
            
            chemin.add(s);
        }
        return chemin;
    }
    /**
     * Par défaut, on veut que le tri topologique nous retourne le premier élément
     * @return un chemin de sommet
     * @throws Exception
     */
    public ArrayList<Sommet> triTopologique() throws Exception {
        return triTopologique(FIRST);
    }

    public TreeMap<Integer, ArrayList<Integer>> triTopologiqueRecursif() throws Exception{

        TreeMap<Sommet,Integer> degEntrant = new TreeMap<>(entrant);
        LinkedList<Sommet> tempSource =  new LinkedList<>(source);
        ArrayList<ArrayList<Sommet>> allPath = new ArrayList<>();
        TreeMap<Integer, ArrayList<Integer>> indexDict = new TreeMap<>(); // Clé Distance Résultante et valeur les index
        
        allTopologogiqueRecursif(degEntrant, tempSource, new ArrayList<>());

        for(int i = 0; i<cheminRecursif.size(); i++) {
            int tmpDistance = GetDistance(cheminRecursif.get(i));
            if (!indexDict.containsKey(tmpDistance)) {
                indexDict.put(GetDistance(cheminRecursif.get(i)), new ArrayList<>());
            }
            
            ArrayList<Integer> tmpIndex = indexDict.get(tmpDistance);
            tmpIndex.add(i);
            indexDict.put(tmpDistance, tmpIndex);
        }

        int key = indexDict.firstKey();
        for (int j :indexDict.get(key)) {
            System.out.println(cheminRecursif.get(j));
        }

    
        return indexDict;
    }

    private boolean isSame(ArrayList<Sommet> _chemin, ArrayList<Sommet> _chemin2) {
        if (_chemin.size() != _chemin2.size()) {
            return false;
        }

        for (int i = 0; i < _chemin.size(); i++ ) {
            if(_chemin.get(i).getId() != _chemin2.get(i).getId()) {
                return false;
            }
        } 
        return true;
    }

    

    private boolean pathIsValid(ArrayList<Sommet> _path) {
        if(_path.get(0).getName().compareTo("Velizy") != 0 || _path.get(_path.size()-1).getName().compareTo("Velizy") != 0) {
            return false;
        }

        
        return true;
    }

    public ArrayList<Sommet> topologogiqueRecursif(TreeMap<Sommet, Integer> _entrant, LinkedList<Sommet> _source, ArrayList<Sommet> _chemin) {

        if(_source.isEmpty()) {
            return _chemin;
        } else {
            Sommet s = _source.remove(0);

            for (Sommet v : sortant.get(s)) {

                _entrant.put(v, _entrant.get(v)-1);
                if(_entrant.get(v) == 0) {
                    _source.add(v);
                }
            }

            _chemin.add(s);
        }
        return topologogiqueRecursif(_entrant, _source, _chemin);
    }

    private void allTopologogiqueRecursif(TreeMap<Sommet, Integer> _entrant, LinkedList<Sommet> _source, ArrayList<Sommet> _chemin) {
        if (_chemin == null || GetDistance(_chemin)>(averageTheorical)) {
            return ;
        }

        if(_source.isEmpty()) {
            if (pathIsValid(_chemin)) {
                cheminRecursif.add(_chemin);
            }

        } else {

            
            for (int i = 0; i < _source.size(); i++) {
                TreeMap<Sommet,Integer> tmpEntrant = new TreeMap<>(_entrant);
                LinkedList<Sommet> tmpSource = new LinkedList<>(_source);
                ArrayList<Sommet> tmpChemin = new ArrayList<>(_chemin);

                Sommet s = tmpSource.remove(i);
                
                for (Sommet v : sortant.get(s)) {
                    tmpEntrant.put(v, tmpEntrant.get(v)-1);
                    if(tmpEntrant.get(v) == 0 ) {
                        tmpSource.add(v);
                    }
                }
                
                
                tmpChemin.add(s);
                
                allTopologogiqueRecursif(tmpEntrant, tmpSource, tmpChemin);
                
            }
        }
        
    } 


    
    /**
     * Retourne l'index minimum des sources par rapport au dernier du chemin
     * @param _chemin une ArrayList de chemin
     * @param _source une LinkedList de chemi
     * @return un entier correspondant l'index du minimum de la liste de source.
     */
    private int getMinIndexDistance(ArrayList<Sommet> _chemin, LinkedList<Sommet> _source ) {
        int index = 0;
        if (_source.size()>1) {
            Sommet last = _chemin.get(_chemin.size()-1);
            int distance = last.getDistance().get(_source.get(0).getId());

            
            for (int i = 1; i < _source.size();i++) {
                int current = _source.get(i).getId();
                if(distance > last.getDistance().get(current)) {
                    distance = last.getDistance().get(current);
                    index = i;
                }
            }
        } 
        // System.out.println(_source.get(index));
        return index;
    }

    private int getMaxIndexEntrant(ArrayList<Sommet> _chemin, LinkedList<Sommet> _source, TreeMap<Sommet, Integer> _entrant) {
        int index = 0;
        boolean isSame = false;

        if (_chemin.size() > 1) {
            Sommet last = _chemin.get(_chemin.size()-1);
            int nEntrant = _entrant.get(last);

            for (int i = 0; i < _source.size(); i++) {
                Sommet current = _source.get(i);
                if (current.compareToName(last) == 0) {
                    index = i;
                    isSame = true;
                }
                if (_entrant.get(current) > nEntrant && !isSame) {
                    nEntrant = _entrant.get(current);
                    index = i;
                } 
            }
            
        }

        return index;
        
    }

    private int getMaxIndexDistance(ArrayList<Sommet> _chemin, LinkedList<Sommet> _source ) {
        int index = 0;
        if (_source.size()>1) {
            Sommet last = _chemin.get(_chemin.size()-1);
            int distance = last.getDistance().get(_source.get(0).getId());

            
            for (int i = 1; i < _source.size();i++) {
                int current = _source.get(i).getId();
                if(distance < last.getDistance().get(current)) {
                    distance = last.getDistance().get(current);
                    index = i;
                }
            }
        } 
        // System.out.println(_source.get(index));
        return index;
    }


    @Override
    public String toString() {
        String str = "Voisin Sortant" + sortant+"\nVoisin Entrant"+ entrant+"\nSource : "+source;
        return str;
    }

    /**
     * Cette méthode permet d'obtenir la distance totale à partir d'un chemin
     * @param _chemin une liste de sommmet
     * @return un entier correspondant à la distance à parcourir/parcouru
     */
    public int GetDistance(ArrayList<Sommet> _chemin)   {

        int res = 0;
        
        for (int i = 1; i < _chemin.size(); i++) {
            String last = _chemin.get(i).getName();
            String current = _chemin.get(i-1).getName();
            
            res += distances.get(last).get(villeID.get(current));
        }
        
        return res;
    }


    

    

}
