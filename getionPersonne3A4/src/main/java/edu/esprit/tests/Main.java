package edu.esprit.tests;

import edu.esprit.entities.Personne;
import edu.esprit.services.ServicePersonne;
import edu.esprit.utils.DataSource;

public class Main {
    public static void main(String[] args) {
       Personne p1=new Personne("f", "String prenom");
        Personne p2=new Personne("twil", "twil");
        Personne p3=new Personne("keke", "keke");
        Personne p4=new Personne("ababa", "ababa");
        ServicePersonne s=new ServicePersonne();
        s.ajouter(p1);
        s.ajouter(p2);
        s.ajouter(p3);
        s.ajouter(p4);


        System.out.println(s.getAll());
    }
}
