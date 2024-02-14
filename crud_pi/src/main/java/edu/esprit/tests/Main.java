package edu.esprit.tests;

import edu.esprit.entites.*;
import edu.esprit.services.ServiceAchat;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceReponse;
import edu.esprit.utils.DataSource;

import java.io.Serial;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        ServiceAchat serviceAchat = new ServiceAchat();
        ServiceReponse serviceReponse = new ServiceReponse();
        ServiceReclamation serviceReclamation = new ServiceReclamation();
        System.out.println(DataSource.getInstance());
        Formation formation = new Formation(3);
        Outil outil = new Outil(8);
        User user = new User(5);

        String code_promo="test23566";
       Achat achat1 = new Achat(70,69,69,code_promo,420);

       String desc="42440";
        Reponse r1 = new Reponse(desc);
        //serviceAchat.ajouter(achat1);
        //serviceAchat.supprimer(4);
        //ajouter reponse
        //serviceReponse.ajouter(r1);
       // serviceReponse.modifier(2,r1);
        //serviceReponse.supprimer(2);
        //System.out.println(serviceReponse.getOneById(1));
        //System.out.println(serviceAchat.getAll());

        Reclamation rec = new Reclamation(6969,0,0,"wiiiw wiiiw",3);
        serviceReclamation.modifier(3,rec);
        System.out.println(serviceReclamation.getAll());
        //serviceReclamation.supprimer(1);


    }
}
