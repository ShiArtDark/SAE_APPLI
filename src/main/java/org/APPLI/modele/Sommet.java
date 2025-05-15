package org.APPLI.modele;

public class Sommet {
    private String name;
    private int pass;
    private int id;
    // =================== Constructeur =====================
    public Sommet(String _name, int _pass, int _id) {
        name = _name;
        pass = _pass;
        id = _id;
    }   


    // =================== Get/Set ==========================
    public void setName(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setPass(int _pass) {
        pass = _pass;
    }

    public int getPass() {
        return pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }

    
}
