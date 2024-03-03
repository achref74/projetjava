package edu.esprit.entities;

import java.sql.Date;
import java.util.Objects;

public class Cours {
   public  Cours(){}
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private int id_cours ;
    private String nom ;
    private String descrption ;
    private String prerequis ;
    private String ressource ;
    private Date date ;
    private int idFormation ;
    private int duree ;
    private Evaluation evaluation ;
    private String img  ;


    public String getImage() {
        return img;
    }

    public void setImage(String image) {
        this.img = image;
    }

    /*public Cours(int id_cours, String nom, String descrption, String prerequis, String ressource, Date date, int duree) {
        this.id_cours = id_cours;
        this.nom = nom;
        this.descrption = descrption;
        this.prerequis = prerequis;
        this.ressource = ressource;
        this.date = date;
        this.duree = duree;
        this.idFormation = 3;
    }*/
    public Cours(int id_cours, String nom, String descrption, String prerequis, String ressource, Date date, int duree,String img ) {
        this.id_cours = id_cours;
        this.nom = nom;
        this.descrption = descrption;
        this.prerequis = prerequis;
        this.ressource = ressource;

        this.date = date;
        this.duree = duree;
        this.img =img ;
        this.idFormation = 3;

    }
    public Cours(int id_cours, String nom, String descrption, String prerequis, String ressource, Date date, int duree,String img ,  Evaluation evaluation) {
        this.id_cours = id_cours;
        this.nom = nom;
        this.descrption = descrption;
        this.prerequis = prerequis;
        this.ressource = ressource;
        this.date = date;
        this.idFormation = 3;
        this.duree = duree;
        this.img=img ;
        this.evaluation = evaluation;
    }
    public Cours(String nom, String descrption, String prerequis, String ressource, Date date, int duree,String img ,  Evaluation evaluation) {

        this.nom = nom;
        this.descrption = descrption;
        this.prerequis = prerequis;
        this.ressource = ressource;
        this.date = date;
        this.idFormation = 3;
        this.duree = duree;
        this.img=img;
        this.evaluation = evaluation;
    }
    public Cours(String nom, String descrption, String prerequis, String ressource, Date date, int duree,String img ) {

        this.nom = nom;
        this.descrption = descrption;
        this.prerequis = prerequis;
        this.ressource = ressource;
        this.date = date;
        this.idFormation = 3;
        this.duree = duree;
        this.img =img ;

    }


    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getPrerequis() {
        return prerequis;
    }

    public void setPrerequis(String prerequis) {
        this.prerequis = prerequis;
    }

    public String getRessource() {

        return ressource;
    }

    public void setRessource(String ressource) {

        this.ressource = ressource;
    }
    public Date getDate() {
        Date coursDate = date;

        return date;
    }

    public void setDate(Date date) {


            this.date = date;
    }


    public int getDuree() {


        return duree;
    }

    public void setDuree(int duree) {

        this.duree = duree;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cours cours = (Cours) o;
        return id_cours == cours.id_cours && idFormation == cours.idFormation && duree == cours.duree && Objects.equals(nom, cours.nom) && Objects.equals(descrption, cours.descrption) && Objects.equals(prerequis, cours.prerequis) && Objects.equals(ressource, cours.ressource) && Objects.equals(date, cours.date) && Objects.equals(evaluation, cours.evaluation) && Objects.equals(img, cours.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_cours, nom, descrption, prerequis, ressource, date, idFormation, duree, evaluation, img);
    }

    @Override
    public String toString() {
        long heures = duree / 3600;
        long minutes = (duree % 3600) / 60;
        long secondes = duree % 60;

        return String.format("Cours%n" +
                        "-------------------------------------------------%n" +
                        "| Nom           | Description     | Prérequis | Ressource | Date                | ID Formation | Durée                       | Evaluation  |%n" +
                        "-------------------------------------------------%n" +
                        "| %-13s | %-15s | %-9s | %-9s | %-20s | %-11d | %-2dh %-2dmin %-2dsc | %-12s |%n"+ "image ",
                nom, descrption, prerequis, ressource, date, idFormation, heures, minutes, secondes, evaluation,img);
    }
}
