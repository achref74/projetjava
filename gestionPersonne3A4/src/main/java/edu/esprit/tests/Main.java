package edu.esprit.tests;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;

import edu.esprit.service.ServiceUser;

import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Formateur f2=new Formateur("raouf","lolita","atat",new Date(2002,1,1),"hdhdh",525452,"hgh","fhd","hgxf",5,"fx");
        ServiceUser su =new ServiceUser() ;

su.ajouter(f2);
        // su.modifier(f2);
        // su.supprimer(11);
       // System.out.println(su.getAll());
     //   System.out.println(su.getOneById(3));
        System.out.println(su.login("ffh","achref"));



    }


}

