package org.APPLI;

import java.util.ArrayList;
import java.util.TreeMap;

import org.APPLI.modele.FileManager;


public class Main {
    public static void main(String[] args) {
        TreeMap<String, ArrayList<Integer>> oui = new FileManager().exportVille();

        System.out.println(oui.keySet());

    }
}