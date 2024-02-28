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
    private String image;

    public outil(int idoutils, String nom, String description, Double prix, String ressources, String stock, String etat,Categorie categorie,String image) {
        this.idoutils = idoutils;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;
        this.categorie=categorie;
        this.image=image;
    }
    public outil(int idoutils, String nom, String description, Double prix, String ressources, String stock, String etat, String image) {
        this.idoutils = idoutils;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;
        this.image=image;

    }
    public outil( String nom, String description, Double prix, String ressources, String stock, String etat, String image) {

        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;
        this.image=image;

    }

    public outil(String nom, String description, Double prix, String ressources, String stock, String etat,Categorie categorie, String image) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ressources = ressources;
        this.stock = stock;
        this.etat = etat;
        this.categorie=categorie;
        this.image=image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        outil outil = (outil) o;
        return idoutils == outil.idoutils && Objects.equals(nom, outil.nom) && Objects.equals(description, outil.description) && Objects.equals(prix, outil.prix) && Objects.equals(ressources, outil.ressources) && Objects.equals(stock, outil.stock) && Objects.equals(etat, outil.etat) && Objects.equals(categorie, outil.categorie) && Objects.equals(image, outil.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idoutils, nom, description, prix, ressources, stock, etat, categorie, image);
    }

    @Override
    public String toString() {
        return this.nom ;
    }
}
