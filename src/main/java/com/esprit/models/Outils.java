package com.esprit.models;

import java.util.Objects;

public class Outils {
    private int id;
    private String nom;
    private String description;
    private Double prix;
    private String ressources;
    private String stock;
    private EtatOutils etat;

    public Outils(int id, String nom, String description, Double prix, String ressources, String stock, EtatOutils etat) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;
    }

    public Outils(String nom, String description, Double prix, String ressources, String stock, EtatOutils etat) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getRessources() {
        return ressources;
    }

    public void setRessources(String ressources) {
        this.ressources = ressources;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public EtatOutils getEtat() {
        return etat;
    }

    public void setEtat(EtatOutils etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outils outils = (Outils) o;
        return id == outils.id && Double.compare(prix, outils.prix) == 0 && Objects.equals(nom, outils.nom) && Objects.equals(description, outils.description) && Objects.equals(ressources, outils.ressources) && Objects.equals(stock, outils.stock) && etat == outils.etat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description, prix, ressources, stock, etat);
    }

    @Override
    public String toString() {
        return "Outils{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", ressources='" + ressources + '\'' +
                ", stock='" + stock + '\'' +
                ", etat=" + etat +
                '}';
    }
}
