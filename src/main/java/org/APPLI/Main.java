package org.APPLI;

import org.APPLI.modele.FileManager;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(FileManager.exportMembre());
            System.out.println(FileManager.exportVille());
            System.out.println(FileManager.exportVilleID());
            
        } catch (Exception e) {

        }

    
    }
}