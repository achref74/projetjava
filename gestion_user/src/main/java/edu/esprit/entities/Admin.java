package edu.esprit.entities;

import java.sql.Date;

public class Admin extends User{
    public Admin(int id, String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp) {
        super(id, nom, prenom, email, dateNaissance, adresse, numtel, mdp);
    }

    public Admin(String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp) {
        super(nom, prenom, email, dateNaissance, adresse, numtel, mdp);
    }

    public Admin() {
    }

}
