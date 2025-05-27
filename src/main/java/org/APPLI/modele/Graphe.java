package org.APPLI.modele;

import java.io.IOException;
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

    public static final int FIRST = 0;
    public static final int DISTANCE = 1;
    public static final int DEGREE = 2;


    // ======================== CONSTRUCTEUR ========================

    public Graphe (TreeMap<Sommet, ArrayList<Sommet>> _tab) {
        sortant = new TreeMap<>(Sommet::compareTo);
        entrant = new TreeMap<>(Sommet::compareTo);
        source = new LinkedList<>();
        cheminRecursif = new ArrayList<>();

        for (Sommet som : _tab.keySet()) {
            entrant.put(som, 0);
        }
    
        for (Sommet som : _tab.keySet()) {
            TreeSet<Sommet> tempSet = new TreeSet<>(Sommet::compareTo);

            for (Sommet voisin : _tab.get(som)) {
                tempSet.add(voisin);
                entrant.put(voisin, entrant.get(voisin)+1);
                
            }
            sortant.put(som, tempSet);
        }
        
        for (Sommet som : entrant.keySet()) {
            if(entrant.get(som) == 0) {
                source.add(som);
            }
        }
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

    // ======================== Method ========================
    

    /**
     * La méthode du tri topologique ou l'algorithme de Bellman-Ford-Moore, consiste à modéliser sous forme de graphe
     * orienté sans cycle, un chemin à partir de source.
     * 
     * @return ArrayList<Sommet> une liste de sommet constituant le chemin.
     * @throws Exception
     */
    public ArrayList<Sommet> triTopologique(int _option) throws Exception {

        TreeMap<Sommet, Integer> degreeEntrant = (TreeMap<Sommet, Integer>) entrant.clone();
        LinkedList<Sommet> tempSource = (LinkedList<Sommet>) source.clone();
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

    public ArrayList<ArrayList<Sommet>> triTopologiqueRecursif(
        ) {

        TreeMap<Sommet,Integer> degEntrant = new TreeMap<>(entrant);
        LinkedList<Sommet> tempSource =  new LinkedList<>(source);
        ArrayList<ArrayList<Sommet>> allPath = new ArrayList<>();
        
        topologogiqueRecursif(degEntrant, tempSource, new ArrayList<>(), allPath);

        for(int i = 0; i<cheminRecursif.size()-1;i++) {
            if (pathIsValid(cheminRecursif.get(i))) {

                allPath.add(cheminRecursif.get(i));
            }
        }
        
        return allPath;
    }

    private boolean pathIsValid(ArrayList<Sommet> _path) {
        if(_path.get(0).getName().compareTo("Velizy") != 0 || _path.get(_path.size()-1).getName().compareTo("Velizy") != 0) {
            return false;
        }

        for(Sommet som : sortant.keySet()) {
            if (!_path.contains(som)) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Sommet> topologogiqueRecursif(TreeMap<Sommet, Integer> _entrant, LinkedList<Sommet> _source, ArrayList<Sommet> _chemin) {

        if(_source.isEmpty()) {
            return _chemin;
        } else {
            Sommet s = _source.remove(0);

            for (Sommet v : sortant.get(s)) {

                // ancien degré
                int ancien = _entrant.get(s);
                // nouveau degré décremente

                _entrant.put(v, _entrant.get(v)-1);
                if(_entrant.get(v) == 0) {
                    _source.add(v);
                }
            }

            _chemin.add(s);
        }
        return topologogiqueRecursif(_entrant, _source, _chemin);
    }

    private void topologogiqueRecursif(TreeMap<Sommet, Integer> _entrant, LinkedList<Sommet> _source, ArrayList<Sommet> _chemin, ArrayList<ArrayList<Sommet>> _allPath) {
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

                
                topologogiqueRecursif(tmpEntrant, tmpSource, tmpChemin, _allPath);
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
        if (_chemin.size() >= 1) {
            Sommet last = _chemin.get(_chemin.size()-1);
            int distance = last.getDistance().get(_source.get(0).getId());
            
            for (int i = 1; i < _source.size();i++) {
                Sommet current = _source.get(i);
                if(distance > last.getDistance().get(current.getId())) {
                    distance = last.getDistance().get(current.getId());
                    index = i;
                }
            }
        } 
        
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

    @Override
    public String toString() {
        String str = "Voisin Sortant" + sortant+"\nVoisin Entrant"+ entrant+"\nSource : "+source;
        return str;
    }

    /**
     * Cette méthode permet d'obtenir la distance totale à partir d'un chemin
     * @param _chemin une liste de sommmet
     * @return un entier correspondant à la distance à parcourir/parcouru
     * @throws IOException
     */
    public int GetDistance(ArrayList<Sommet> _chemin) throws IOException {
        TreeMap<String, ArrayList<Integer>> distance = FileManager.exportVille();
        TreeMap<String, Integer> villeID = FileManager.exportVilleID();
        
        int res = 0;
        
        for (int i = 1; i < _chemin.size(); i++) {
            String last = _chemin.get(i).getName();
            String current = _chemin.get(i-1).getName();
            
            res += distance.get(last).get(villeID.get(current));
        }
        
        return res;
    }

    
}
