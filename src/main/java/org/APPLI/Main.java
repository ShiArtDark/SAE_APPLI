package org.APPLI;

import org.APPLI.modele.FileManager;
import org.APPLI.modele.Graphe;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(FileManager.exportMembre());
            System.out.println(FileManager.exportVille());
            System.out.println(FileManager.exportVilleID());
            
        } catch (Exception e) {

        }

        int[][] tab = {
            {1,2},
            {},
            {4},
            {2,4},
            {}
        };
        Graphe g = new Graphe(tab);
        System.out.println(g.getEntrant());
        System.out.println(g.getSortant());
        System.out.println(g.getSource());
        System.out.println(g.triTopologique());
    }
}