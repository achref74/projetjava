package edu.esprit.entities;

import java.sql.Date;

public class Admin extends User{

    public Admin(int id, String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp, String img, String genre) {
        super(id, nom, prenom, email, dateNaissance, adresse, numtel, mdp, img, genre);
    }

    public Admin(String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp, String img, String genre) {
        super(nom, prenom, email, dateNaissance, adresse, numtel, mdp, img, genre);
    }

    public Admin() {
    }

}
