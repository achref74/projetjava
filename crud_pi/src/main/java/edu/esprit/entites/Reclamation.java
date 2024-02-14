package edu.esprit.entites;

import java.util.Date;

public class Reclamation {
    private int id_reclamation;
    private User user;
    private Outil outil;
    private Formation formation;
    private Reponse reponse;
    private Date date_reclamation;

    public Reclamation(int id_reclamation, User user, Outil outil, Formation formation, Reponse reponse, Date date_reclamation) {
        this.id_reclamation = id_reclamation;
        this.user = user;
        this.outil = outil;
        this.formation = formation;
        this.reponse = reponse;
        this.date_reclamation = date_reclamation;
    }
}
