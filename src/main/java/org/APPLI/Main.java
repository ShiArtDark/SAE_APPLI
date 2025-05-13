package org.APPLI;

import org.APPLI.modele.FileManager;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(new FileManager().exportVille());
            
        } catch (Exception e) {
        }
    }
}