package edu.esprit.entities;

import java.sql.Date;
import java.util.Objects;


public class Client extends User {
    private String niveau_scolaire;

    public Client(int id, String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp, String img, String genre, String niveau_scolaire) {
        super(id, nom, prenom, email, dateNaissance, adresse, numtel, mdp, img, genre);
        this.niveau_scolaire = niveau_scolaire;
    }

    public Client(String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp, String img, String genre, String niveau_scolaire) {
        super(nom, prenom, email, dateNaissance, adresse, numtel, mdp, img, genre);
        this.niveau_scolaire = niveau_scolaire;
    }



    public Client() {
    }

    public String getNiveau_scolaire() {
        return niveau_scolaire;
    }

    public void setNiveau_scolaire(String niveau_scolaire) {
        this.niveau_scolaire = niveau_scolaire;
    }

    @Override
    public String toString() {
        return "Client{ " + super.toString()+"niveau_scolaire="+ this.getNiveau_scolaire()+"}"+'\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(niveau_scolaire, client.niveau_scolaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), niveau_scolaire);
    }
}
