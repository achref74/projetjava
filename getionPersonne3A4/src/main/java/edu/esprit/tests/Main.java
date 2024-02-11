package edu.esprit.tests;

import edu.esprit.entities.Client;
import edu.esprit.services.ServiceClient;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        Client c=new Client("achref","prenom","ffh",new Date(2002,12,5),"hahahah",1522222,"achref","ffff");
        ServiceClient c1=new ServiceClient();
        c1.ajouter(c);



    }
}
