package edu.esprit.tests;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Forum;
import edu.esprit.entities.Publication;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServiceForum;
import edu.esprit.services.ServicePublication;
import edu.esprit.utils.DataSource;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {

        ServiceCommentaire sc = new ServiceCommentaire();
        ServiceForum sf = new ServiceForum();
        ServicePublication sp = new ServicePublication();
      // sp.ajouter(new Publication("xxx","xxx",0,8,1));
        //sp.supprimer(1);
       // System.out.println(sp.getOneById(2));
       // System.out.println(sp.getAll());
       // sp.modifier(new Publication(2,"fff","ffff",5,8,1));
       // sc.ajouter(new Commentaire("med",1,2,0));

       // sf.ajouter(new Forum("med","bonjour",3));
        //sf.supprimer(1);
//       System.out.println(sf.getOneById(8));
//        try {
//            System.out.println(sf.getAll());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        //sc.supprimer(9);
        System.out.println(sc.getAll());
       // System.out.println(sc.getOneById(22));


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

//        try {
//            sc.modifier(new Commentaire( 22,"bonjourAAA",1,2,3));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
       /* try {
            sf.modifier(new Forum("felhi","mohamed",6));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }     */






    }
}

