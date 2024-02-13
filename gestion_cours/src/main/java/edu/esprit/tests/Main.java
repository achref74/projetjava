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
        ServiceQuestion s =new ServiceQuestion();
        Question q = new Question("hbvq");
      //s.ajouter(q,1);
//  s.supprimer(19);
        System.out.println(s.getAll());
/*
        Question q1 = new Question(4,"ressourceeeeeeeeee");
        //s.modifier(q1);
        ServiceEvaluation c = new ServiceEvaluation();
        //c.supprimer(1);
       // System.out.println(c.getAll());
        ServiceEvaluation ev =new ServiceEvaluation();
       */ Evaluation e =new Evaluation(44,4,"jh",4);/*
        Evaluation e1 =new Evaluation(10,0000,"jh",4);
        ev.modifier( e1);
        */
        //System.out.println(ev.getAll());
        Cours c =new Cours(8,"loujein","r","rf","r", new Date(2003,12,12),4,e);
        ServiceCours se =new ServiceCours();
        //se.modifier(c);
        System.out.println(se.getAll());
        se.supprimer(10);
    }

}
