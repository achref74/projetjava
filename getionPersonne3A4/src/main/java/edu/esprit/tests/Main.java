package edu.esprit.tests;

import edu.esprit.entities.Client;
import edu.esprit.services.ServiceClient;
import edu.esprit.services.ServiceUser;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        Client c=new Client("achref","prenom","ffh",new Date(2002,12,5),"hahahah",1522222,"achref","ffff");
        ServiceClient c1=new ServiceClient();
        c1.ajouter(c);
        System.out.println(c1.getAll());
        ServiceUser su=new ServiceUser();
        System.out.println(su.getAll());
        System.out.println(c1.getOneById(1));



    }
}
