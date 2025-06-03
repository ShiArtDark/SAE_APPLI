
package org.APPLI.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.util.Pair;

public class Graphe {
    // On va passer par des identifiants de villes 

    private final TreeMap<Sommet, TreeSet<Sommet>> sortant;
    private final TreeMap<Sommet, Integer> entrant;
    private final LinkedList<Sommet> source;
    private TreeMap<Integer,ArrayList<ArrayList<Sommet>>> cheminRecursif;
    private final int minTheorical;
    private final double averageTheorical;

    private TreeMap<String, ArrayList<Integer>> distances;
    private TreeMap<String, Integer> villeID;
    private int n =0; // Valeur tampons
    private int k;
    private int nbSolution;
    
    public static final int FIRST = 0;
    public static final int DISTANCE = 1;
    public static final int DEGREE = 2;
    public static final int MAX_DISTANCE = 3;


    // ======================== CONSTRUCTEUR ========================

    public Graphe (TreeMap<Sommet, ArrayList<Sommet>> _tab) throws Exception{
        sortant = new TreeMap<>(Sommet::compareTo);
        entrant = new TreeMap<>(Sommet::compareTo);
        source = new LinkedList<>();
        cheminRecursif = new TreeMap<>();
   

        distances = FileManager.exportVille();
        villeID = FileManager.exportVilleID();


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
        minTheorical = getDistance(triTopologique(DISTANCE));
        averageTheorical = minTheorical*1.5*.7;
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

    public TreeMap<Integer, ArrayList<ArrayList<Sommet>>> getAllSolutions() {
        return cheminRecursif;
    }

    // ======================== Method ========================

    //#region Tri Topologique classique
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

    //#endregion 
    //#region Tri Topologique récursif
    public TreeMap<Integer, ArrayList<ArrayList<Sommet>>> triTopologiqueRecursif(int _k){

        TreeMap<Sommet,Integer> degEntrant = new TreeMap<>(entrant);
        LinkedList<Sommet> tempSource =  new LinkedList<>(source);
        HashMap<Pair<Integer, Integer>, Integer> cacheDistance = new HashMap<>();
        nbSolution = 0;
        elagage( degEntrant, tempSource, new ArrayList<>(), _k, cacheDistance);

        return cheminRecursif;
    }

    public TreeMap<Integer, ArrayList<ArrayList<Sommet>>> triTopologiqueRecursif() throws Exception{
        return triTopologiqueRecursif(0);
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

    private boolean isLastSituation(ArrayList<Sommet> _chemin) {
        if (!_chemin.isEmpty()) {

            Sommet last = _chemin.get(_chemin.size()-1);
            if (last.getName() == "Velizy" && last.getPass() == 1) {
                return true;
            }
        }
        return false;


    }

    private void insertionSort(LinkedList<Sommet> _source, Sommet _last) {
        
        int len = _source.size();
        for (int i = 1; i < len; i++) {
            Sommet key = _source.get(i);
            int j = i -1;

            while (j >= 0 && _last.getDistance().get(_source.get(j).getId()) > _last.getDistance().get(key.getId())) {
                _source.set(j+1, _source.get(j));
                j = j -1;
            }
            _source.set(j+1, key );
        }

       
    }
    /**
     * 
     * @param _entrant
     * @param _source
     * @param _chemin
     * @param _k
     * @param _cacheDistance
     * @return
     */
    public int elagage(
        TreeMap<Sommet,Integer> _entrant,
        LinkedList<Sommet> _source,
        ArrayList<Sommet> _chemin,
        int _k,
        HashMap<Pair<Integer, Integer>, Integer> _cacheDistance
        ){
            n++;
            if (n > 3_000_000) {
                return 0;
            }
           
            int dist = getDistance(_chemin);

            
            
            if ((isLastSituation(_chemin) || (dist >= averageTheorical &&  nbSolution < nbSolution)) ) { // Condition de sortie

                if (isLastSituation(_chemin)) {

                    if (!cheminRecursif.containsKey(dist)) {
                        cheminRecursif.put(dist, new ArrayList<>());
                        
                    }

                    ArrayList<ArrayList<Sommet>> chemins = cheminRecursif.get(dist);
    
                    for (ArrayList<Sommet> sub : chemins) { // On supprime les doublons par ID
                        if (isSame(sub, _chemin)) {
                            return dist;
                        }
                    }
                    chemins.add(_chemin);
                    nbSolution++;
                    cheminRecursif.put(dist, chemins);
                }
                return dist;

            } else {

                int valeur = Integer.MAX_VALUE;
                float avg = Integer.MAX_VALUE;

                if(!_source.isEmpty() && !_chemin.isEmpty()) {
                    avg = 0;
                    Sommet last = _chemin.get(_chemin.size()-1);
                    for (Sommet s: _source) {

                        if(!_cacheDistance.containsKey(new Pair<>(last.getId(), s.getId())) || !_cacheDistance.containsKey(new Pair<>(s.getId(), last.getId())) ) {
                            int tmpDist = last.getDistanceFrom(s);
                            _cacheDistance.put(new Pair<>(last.getId(), s.getId()), tmpDist);
                            _cacheDistance.put(new Pair<>(s.getId(), last.getId()), tmpDist);

                        }
                        avg += _cacheDistance.get(new Pair<>(last.getId(), s.getId()));
                    
                    }
                    avg = avg/_source.size();
                }

         
                  
                for (int i = 0; i < _source.size(); i++) {
                    
                    if(!_chemin.isEmpty()) {

                        Sommet last = _chemin.get(_chemin.size()-1);
                       

                        if(last.getDistanceFrom(_source.get(i) ) > avg*1.5) {
                            continue;
                        }
                    }

         
                    TreeMap<Sommet,Integer> tmpEntrant = new TreeMap<>(_entrant);
                    LinkedList<Sommet> tmpSource = new LinkedList<>(_source);
                    ArrayList<Sommet> tmpChemin = new ArrayList<>(_chemin);

                    
                    Sommet s = tmpSource.remove(i);

                    if(!_chemin.isEmpty()) {
                        
                    }
                    for (Sommet v : sortant.get(s)) {
                        tmpEntrant.put(v, tmpEntrant.get(v)-1);
                        if(tmpEntrant.get(v) == 0 ) {
                            tmpSource.add(v);
                        }
                    }
                    
                    
                    insertionSort(tmpSource, s);
                    tmpChemin.add(s);
              
                    valeur = Math. min( valeur, elagage( tmpEntrant, tmpSource, tmpChemin, _k ,_cacheDistance));

                    
                 
                    
                }
                return valeur;   
            }
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

    /**
     * Premiere Version du tri topologique récursif pour toutes les solutions
     * @param _entrant
     * @param _source
     * @param _chemin
     */
    private void allTopologogiqueRecursif(TreeMap<Sommet, Integer> _entrant, LinkedList<Sommet> _source, ArrayList<Sommet> _chemin) {
        n++;
        if (getDistance(_chemin)>(averageTheorical) ) {
            return ;
        }

        if(_source.isEmpty()) {
            if (pathIsValid(_chemin)) {
                int dist = getDistance(_chemin);

                if (!cheminRecursif.containsKey(dist)) {
                    cheminRecursif.put(dist, new ArrayList<>());
                }

                
                ArrayList<ArrayList<Sommet>> tmpChemins = cheminRecursif.get(dist);
                for (ArrayList<Sommet> chem : tmpChemins) {
                    if (isSame(_chemin, chem)) {
                        return;
                    }
                }
                tmpChemins.add(_chemin);
                cheminRecursif.put(dist, tmpChemins);
                
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

    //#endregion

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
    public int getDistance(ArrayList<Sommet> _chemin)   {

        int res = 0;
        
        for (int i = 1; i < _chemin.size(); i++) {
            String last = _chemin.get(i).getName();
            String current = _chemin.get(i-1).getName();
            
            res += distances.get(last).get(villeID.get(current));
        }
        
        return res;
    }
    public int getIteration() {
        return n;
    }

    public TreeMap<Integer,ArrayList<ArrayList<Sommet>>> getSolutionK(int _k) {
        TreeMap<Integer, ArrayList<ArrayList<Sommet>>> resultat = new TreeMap<>();
        

        for(int dist : cheminRecursif.keySet()) {
            for(ArrayList<Sommet> chemin : cheminRecursif.get(dist)) {
                
                if (!resultat.containsKey(dist)) {
                    resultat.put(dist, new ArrayList<>());
                }
                ArrayList<ArrayList<Sommet>> tmpChemins = resultat.get(dist);
                tmpChemins.add(chemin);
                resultat.put(dist, tmpChemins);
                _k--;
                if(_k < 1) {
                    return resultat;
                }
                
            }
            

        }
        return  resultat;
    }

    public TreeMap<Integer,ArrayList<ArrayList<Sommet>>> calculeDesSolutions(int _k) {
        triTopologiqueRecursif(_k);
        return getSolutionK(_k);
    }

    public ArrayList<String> convertChemin(ArrayList<Sommet> _chemin) {
        ArrayList<String> chemin = new ArrayList<>();
        chemin.add(_chemin.get(0).getName());

        for (int i = 1; i < _chemin.size();i++) {
            if(chemin.get(chemin.size()-1).compareTo( _chemin.get(i).getName()) == 0) {
                continue;
            }

            chemin.add(_chemin.get(i).getName());
        }

        return chemin;
        
    }
}