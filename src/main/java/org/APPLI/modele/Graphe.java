
package org.APPLI.modele;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.util.Pair;

public class Graphe {
    
    private final TreeMap<Sommet, TreeSet<Sommet>> sortant;
    private final TreeMap<Sommet, Integer> entrant;
    private final LinkedList<Sommet> source;
    private TreeMap<Integer,ArrayList<ArrayList<Sommet>>> cheminRecursif;
    private ArrayList<ArrayList<Sommet>> meilleursSolution;
    private final int minTheorical;
    private final double averageTheorical;

    private TreeMap<String, ArrayList<Integer>> distances;
    private TreeMap<String, Integer> villeID;
    private int n =0;
    private int nbSolution;
    
    public static final int FIRST = 0;
    public static final int DISTANCE = 1;
    public static final int DEGREE = 2;


    // ======================== CONSTRUCTEUR ========================

    public Graphe (TreeMap<Sommet, ArrayList<Sommet>> _tab) throws IOException{
        sortant = new TreeMap<>(Sommet::compareTo);
        entrant = new TreeMap<>(Sommet::compareTo);
        source = new LinkedList<>();
        cheminRecursif = new TreeMap<>();
        meilleursSolution = new ArrayList<>();
   

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
        averageTheorical = minTheorical*3;
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
     * @param _option est un entier permettant de selectionner une option de recherche.
     *                  0 -> On extrait toujours la première source comme une file
     *                  1 -> On extrait le sommet avec la distance minimale par rapport à la dernière ville
     *                  2 -> On extrait toujours le sommet avec le plus de sommet entrant

     * @return ArrayList<Sommet> une liste de sommet constituant le chemin.
     */
    public ArrayList<Sommet> triTopologique(int _option) {

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
                default:
                    index = 0;
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
     * Par défaut, on veut que le tri topologique nous retourne le premier élément, c'est le tri topologique vu en cours
     * @return un chemin de sommet
     */
    public ArrayList<Sommet> triTopologique() {
        return triTopologique(FIRST);
    }

    //#endregion 

    //#region Tri Topologique récursif

    /**
     * Cette méthode permet de calculer récursivement des solutions de chemin, c'est celle qui appelle notre méthode récursive
     * @param _k -> k est le nombre de solution minimale à chercher
     * @return Une TreeMap de chemins où la clé est la distance
     */
    public TreeMap<Integer, ArrayList<ArrayList<Sommet>>> triTopologiqueRecursif(int _k){

        TreeMap<Sommet,Integer> degEntrant = new TreeMap<>(entrant);
        LinkedList<Sommet> tempSource =  new LinkedList<>(source);
        HashMap<Pair<Integer, Integer>, Integer> cacheDistance = new HashMap<>(); // Le cache nous permet d'éviter les calculs inutiles de distances
        nbSolution = _k;
        meilleursSolution = new ArrayList<>();
        elagage( degEntrant, tempSource, new ArrayList<>(), _k, cacheDistance);

        return cheminRecursif;
    }

    /**
     * Permet d'avoir une valeur par défaut à 0, avec un nombre de chemin minimale à 0
     * @return
     */
    public TreeMap<Integer, ArrayList<ArrayList<Sommet>>> triTopologiqueRecursif(){
        return triTopologiqueRecursif(0);
    }

    /**
     * Cette méthode permet de déterminer si un chemin est le même qu'un autre
     * @param _chemin
     * @param _chemin2
     * @return boolean Vrai si c'est les même, Faux sinon
     */
    private boolean isSame(ArrayList<Sommet> _chemin, ArrayList<Sommet> _chemin2) {
        if (_chemin.size() != _chemin2.size()) {
            return false;
        }

        for (int i = 1; i < _chemin.size(); i++ ) {
            if(_chemin.get(i).getId() != _chemin2.get(i).getId()) {
                return false;
            } 
        }

     
        
 
        return true;
    }


    /**
     * Cette méthode permet de savoir si un chemin est valide, 
     * il est valide si et seulement si le début et la fin corresponde à Vélizy
     * si la taille du chemin correspond à la taille de tout les sommets du graphe
     * @param _path
     * @return Vrai si c'est le cas, sinon Faux
     */
    private boolean pathIsValid(ArrayList<Sommet> _path) {
        if(_path.get(0).getName().compareTo("Velizy") != 0 || _path.get(_path.size()-1).getName().compareTo("Velizy") != 0) {
            return false;
        }

        if (_path.size() != sortant.size()) {
            return false;
        }

        return true;
    }


    /**
     * Cette méthode permet de déterminer si c'est le cas de base de la fonction récursive, si le chemin n'esst pas vide et que le dernier sommet correspond au Sommet Velizy Sortant
     * @param _chemin
     * @return Vrai si c'est le cas, sinon non.
     */
    private boolean isLastSituation(ArrayList<Sommet> _chemin) {
        if (!_chemin.isEmpty()) {

            Sommet last = _chemin.get(_chemin.size()-1);
            if (last.getName() == "Velizy" && last.getPass() == 1) {
                return true;
            }
        }
        return false;


    }

    /**
     * Cette méthode est un tri par insertion, elle permet de trier les sommets dans l'ordre croissant des distances par rapport au dernier sommet du chemin
     * @param _source _LinkedList de sommet de la source
     * @param _last Le dernier sommet du chemin
     */
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

    private void insertionSort(ArrayList<ArrayList<Sommet>> _chemins) {
        
        int len = _chemins.size();
        for (int i = 1; i < len; i++) {
            ArrayList<Sommet> key = _chemins.get(i);

            int j = i -1;

            while (j >= 0 && getDistance(_chemins.get(j)) > getDistance(key)) {
                _chemins.set(j+1, _chemins.get(j));
                j = j -1;
            }
            _chemins.set(j+1, key );
        }
        
    }
    /**
     * Cette méthode est notre méthode récursive qui nous permet de calculer un nombre de possibilité.
     * L'arbre de récursion devient assez vite conséquent, nous avons fait le nécessaire pour réduire le nombre de calculs pour optimiser sa recherche.
     *     __Optimisation__:
     *             - Nombre de récursion limité : fixé, mais grande perte de solution
     *             - Etablissement d'un seuil : Quand une distance devient trop abberante, on supprime la branche des possibles 
     *
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
            if (n > 10_000_000) {
                return 0;
            }
           
            int dist = getDistance(_chemin);

          
            
            if ((isLastSituation(_chemin) || (dist >= averageTheorical &&  meilleursSolution.size() < _k)) ) { // Condition de sortie -> Si c'est la dernière situation, ou que la distance

                if (isLastSituation(_chemin)) {

                    if (!cheminRecursif.containsKey(dist)) {
                        cheminRecursif.put(dist, new ArrayList<>());
                        
                    }
                    
                    if (meilleursSolution.size() < _k) {
                        for(ArrayList<Sommet> chm : meilleursSolution) {
                            if(isSame(_chemin, chm)) {
                                return dist;
                            }
                        }
                        meilleursSolution.add(_chemin) ;
                    } else {
                        int bestDist = getDistance(meilleursSolution.get(0));
                        if (dist < bestDist) {
                            for(ArrayList<Sommet> chm : meilleursSolution) {
                                if(isSame(_chemin, chm)) {
                                    return dist;
                                }
                            }
                            meilleursSolution.remove(0);
                            meilleursSolution.add(_chemin);
                        }
                    }

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
                       

                        if(last.getDistanceFrom(_source.get(i) ) > avg*1.6) {
                            continue;
                        }

                     
                    }

         
                    TreeMap<Sommet,Integer> tmpEntrant = new TreeMap<>(_entrant);
                    LinkedList<Sommet> tmpSource = new LinkedList<>(_source);
                    ArrayList<Sommet> tmpChemin = new ArrayList<>(_chemin);

                    
                    Sommet s = tmpSource.remove(i);
                    tmpChemin.add(s);
                    tmpChemin.add(tmpChemin.get(0));

                    int tmpDist = getDistance(tmpChemin);
                    if (meilleursSolution.size() == _k && tmpDist >= getDistance(meilleursSolution.get(0))) {
                        continue;
                    }
                    tmpChemin.remove(tmpChemin.size()-1);


                    if(!_chemin.isEmpty()) {
                        
                    }
                    for (Sommet v : sortant.get(s)) {
                        tmpEntrant.put(v, tmpEntrant.get(v)-1);
                        if(tmpEntrant.get(v) == 0 ) {
                            tmpSource.add(v);
                        }
                    }
                    
                    
                    insertionSort(tmpSource, s);
                    
                    

                    
              
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

    public ArrayList<ArrayList<Sommet>> calculeDesSolutions(int _k) {
        triTopologiqueRecursif(_k);
        insertionSort(meilleursSolution);
        return meilleursSolution;
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