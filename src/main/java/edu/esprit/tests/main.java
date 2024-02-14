package edu.esprit.tests;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Forum;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServiceForum;
import edu.esprit.utils.DataSource;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) {

        ServiceCommentaire sc = new ServiceCommentaire();
        ServiceForum sf = new ServiceForum();
        //sc.ajouter(new Commentaire("med",7,"tata"));
        //sc.ajouter(new Commentaire("xxx",5,"bbbb"));
        //sf.ajouter(new Forum("med",2,2,1));
        //sf.supprimer(1);
      // System.out.println(sf.getOneById(4));
      // System.out.println(sf.getAll());
        //sc.supprimer(9);
        //System.out.println(sc.getAll());
       // System.out.println(sc.getOneById(20));
       /* Commentaire commentaire = sc.getOneById(14);
        commentaire.setContenu("felhiii");
        sc.modifier(commentaire);*/

       /* try {
            System.out.println((sc.recuperer()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println((sf.recuperer()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        /*try {
            sc.modifier(new Commentaire( 20,"www", 9, "contenue2"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        try {
            sf.modifier(new Forum(2,"xxxx",10,20,50));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }






    }
}

