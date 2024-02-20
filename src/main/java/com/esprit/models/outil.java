package com.esprit.models;

import java.util.Objects;

public class outil {
    private int idoutils;
    private String nom;
    private String description;
    private Double prix;
    private String ressources;
    private String stock;
    private String etat;
    private Categorie categorie;

    public outil(int idoutils, String nom, String description, Double prix, String ressources, String stock, String etat,Categorie categorie) {
        this.idoutils = idoutils;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;
        this.categorie=categorie;
    }
    public outil(int idoutils, String nom, String description, Double prix, String ressources, String stock, String etat) {
        this.idoutils = idoutils;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;

    }
    public outil( String nom, String description, Double prix, String ressources, String stock, String etat) {

        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;

    }

    public outil(String nom, String description, Double prix, String ressources, String stock, String etat,Categorie categorie) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;
        this.categorie=categorie;
    }

    public outil() {
    }

    public int getIdoutils() {
        return idoutils;
    }

    public void setIdoutils(int idoutils) {
        this.idoutils = idoutils;
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

    public String getEtat() {
        return etat;
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

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        outil outil = (outil) o;
        return idoutils == outil.idoutils && Objects.equals(nom, outil.nom) && Objects.equals(description, outil.description) && Objects.equals(prix, outil.prix) && Objects.equals(ressources, outil.ressources) && Objects.equals(stock, outil.stock) && etat == outil.etat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idoutils, nom, description, prix, ressources, stock, etat);
    }

    @Override
    public String toString() {
        return "Outils{" +
                "id=" + idoutils +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", ressources='" + ressources + '\'' +
                ", stock='" + stock + '\'' +
                ", etat=" + etat +
                '}';
    }
}
