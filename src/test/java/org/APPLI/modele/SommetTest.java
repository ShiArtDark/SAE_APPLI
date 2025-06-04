package org.APPLI.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SommetTest {
    @Test
    void testCompareTo() {
        Sommet som1 = new Sommet("Sartrouville", 0);   
        Sommet som2 = new Sommet("Sartrouville", 1);   
        Sommet som3 = new Sommet("Paris", 1);   
        Sommet som4 = new Sommet("Versailles", 1); 
        Sommet som5 = new Sommet("Versailles", 1); 
        
     
        assertEquals(-1, som1.compareTo(som2), "Même ville, mais type différent -1");
        assertEquals(1, som2.compareTo(som1), "Même ville mais type différent +1");
        assertEquals(1, som2.compareTo(som3), "Ville différente +1");
        assertEquals(1, som2.compareTo(som3), "Ville différente +1");
        assertEquals(0, som4.compareTo(som5), "Même ville et même type");
    }

    @Test
    void testCompareToName() {
        Sommet som1 = new Sommet("Sartrouville", 0);   
        Sommet som2 = new Sommet("Sartrouville", 1);   
        Sommet som3 = new Sommet("Paris", 1);   
        Sommet som4 = new Sommet("Versailles", 1); 

        assertEquals(0, som1.compareToName(som2), "Même ville");
        assertEquals(1, som1.compareToName(som3), "Objet appelant où le nom est plus grand dans l'alphabet par rapport à l'objet appelé");
        assertEquals(-1, som3.compareToName(som4), "Objet appelant où le nom est plus petit dans l'alphabet par rapport à l'objet appelé");
    }

  
}
