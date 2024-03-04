package edu.esprit.entites;

public class Formation {
    private int id_formation;
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Formation() {
    }

    public Formation(int id_formation) {
        this.id_formation = id_formation;
    }

    public Formation(int id_formation , String nom) {
        this.id_formation = id_formation;
        this.nom=nom;
    }

    public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }
    public String toString() {
        return nom;
    }
    public String tooString() {
        return "test"+nom;
    }

}
