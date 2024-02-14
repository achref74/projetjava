package edu.esprit.tests;


import edu.esprit.entities.Cours;
import edu.esprit.entities.Evaluation;
import edu.esprit.entities.Question;

import edu.esprit.services.ServiceCours;
import edu.esprit.services.ServiceEvaluation;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.utils.DataSource;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        Cours intro =new Cours("Intro","//","algorithmique","hello.txt",new Date(2023,12,12),600);
 Cours InstallationEnv =new Cours("Installation Environnement Python","1er pas en python ","algorithmique","fichier.txt",new Date(2023,12,12),600);
 ServiceCours service_cours =new ServiceCours();
 //service_cours.ajouter(InstallationEnv);
      //  service_cours.ajouter(intro);
       //  service_cours.supprimer(13);
        System.out.println(service_cours.getAll());
        

















    }

}
