package edu.esprit.tests;

import edu.esprit.controllers.AjoutClient;
import edu.esprit.controllers.Login;
import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;

import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import javax.swing.*;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Base64;
import java.util.Scanner;

public class Main {
      public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InterruptedException {
/*


su.ajouter(f2);
        // su.modifier(f2);
        // su.supprimer(11);
       // System.out.println(su.getAll());
     //   System.out.println(su.getOneById(3));
        System.out.println(su.login("ffh","achref"));

        System.out.println(su.isValidEmail("aaaaa"));



          AjoutClient l = new AjoutClient();
          ServiceUser s = new ServiceUser();
          User a = new Admin("achref","meddeb","achref.mouaddeb@esprit.tn",new Date(2002, 6, 19),
                  "Manzel Bourguiba",24954442,l.hashof("123456"),null,"Homme");
          s.ajouter(a);*/
           ServiceUser e = new ServiceUser();
            System.out.println(e.getOneByEmail("mohamed.abdelkebir1@esprit.tn"));


      }



}

