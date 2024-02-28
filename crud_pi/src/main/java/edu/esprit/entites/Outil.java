package edu.esprit.entites;

import javafx.collections.ObservableList;

public class Outil {

    private int id_outil;
    private String nom;

    public Outil() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Outil(int id_outil, String nom) {
        this.id_outil = id_outil;
        this.nom = nom;
    }

    public Outil(int id_outil) {
        this.id_outil = id_outil;
    }

    public int getId_outil() {
        return id_outil;
    }

    public void setId_outil(int id_outil) {
        this.id_outil = id_outil;
    }

    /*@Override
    public String toString() {
        return String.valueOf(id_outil);
    }*/

    @Override
    public String toString() {
        return nom ;

    }
}
