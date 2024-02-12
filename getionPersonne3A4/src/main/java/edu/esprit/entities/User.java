package edu.esprit.entities;

import java.sql.Date;
import java.util.Objects;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private Date dateNaissance;
    private String adresse;
    private int numtel;
    private String mdp;

    public User(int id, String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.numtel = numtel;
        this.mdp = mdp;
    }

    public User(String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.numtel = numtel;
        this.mdp = mdp;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && numtel == user.numtel && Objects.equals(nom, user.nom) && Objects.equals(prenom, user.prenom) && Objects.equals(email, user.email) && Objects.equals(dateNaissance, user.dateNaissance) && Objects.equals(adresse, user.adresse) && Objects.equals(mdp, user.mdp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, email, dateNaissance, adresse, numtel, mdp);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", adresse='" + adresse + '\'' +
                ", numtel=" + numtel +
                ", mdp='" + mdp + '\'' +
                '}';
    }
}
