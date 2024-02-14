package edu.esprit.tests;

import edu.esprit.entites.Achat;
import edu.esprit.entites.Formation;
import edu.esprit.entites.Outil;
import edu.esprit.entites.User;
import edu.esprit.services.ServiceAchat;
import edu.esprit.utils.DataSource;

import java.io.Serial;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        ServiceAchat serviceAchat = new ServiceAchat();
        System.out.println(DataSource.getInstance());
        Formation formation = new Formation(3);
        Outil outil = new Outil(8);
        User user = new User(5);

        String code_promo="test23566";
       Achat achat1 = new Achat(70,69,69,code_promo,420);
        //serviceAchat.ajouter(achat1);
        //serviceAchat.supprimer(4);
        serviceAchat.modifier(1,achat1);

    }
}
