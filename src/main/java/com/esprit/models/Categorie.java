package com.esprit.models;

import java.util.Objects;

public class Categorie {
private int idcategorie;
private String nom;
private String description;


    public Categorie(int idcategorie, String nom, String description) {
        this.idcategorie = idcategorie;
        this.nom = nom;
        this.description = description;
    }
    public Categorie(){}


    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public int getIdcategorie() {
        return idcategorie;
    }

    public void setIdcategorie(int idcategorie) {
        this.idcategorie = idcategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return idcategorie == categorie.idcategorie && Objects.equals(nom, categorie.nom) && Objects.equals(description, categorie.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcategorie, nom, description);
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "idcategorie=" + idcategorie +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
