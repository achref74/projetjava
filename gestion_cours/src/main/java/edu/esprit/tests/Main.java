package edu.esprit.tests;


import edu.esprit.entities.Question;

import edu.esprit.services.ServiceEvaluation;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.utils.DataSource;

public class Main {
    public static void main(String[] args) {
        ServiceQuestion s =new ServiceQuestion();
        Question q = new Question("hbvq");
        s.ajouter(q,1);
 // s.supprimer(19);
        System.out.println(s.getAll());

        Question q1 = new Question(4,"ressourceeeeeeeeee");
        //s.modifier(q1);
        ServiceEvaluation c = new ServiceEvaluation();
        c.supprimer(1);
        System.out.println(c.getAll());


    }

}
