package edu.esprit.entities;

import java.sql.Date;
import java.util.Objects;

public class Cours {
    private int id_cours ;
    private String nom ;
    private String descrption ;
    private String prerequis ;
    private String ressource ;
    private Date date ;
    private int idFormation ;
    private int duree ;
    private Evaluation evaluation ;
    private String imgSource ;

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public Cours(int id_cours, String nom, String descrption, String prerequis, String ressource, Date date, int duree, Evaluation evaluation) {
        this.id_cours = id_cours;
        this.nom = nom;
        this.descrption = descrption;
        this.prerequis = prerequis;
        this.ressource = ressource;
        this.date = date;
        this.idFormation = 3;
        this.duree = duree;
        this.evaluation = evaluation;
    }
    public Cours(String nom, String descrption, String prerequis, String ressource, Date date, int duree, Evaluation evaluation) {

        this.nom = nom;
        this.descrption = descrption;
        this.prerequis = prerequis;
        this.ressource = ressource;
        this.date = date;
        this.idFormation = 3;
        this.duree = duree;
        this.evaluation = evaluation;
    }
    public Cours(String nom, String descrption, String prerequis, String ressource, Date date, int duree) {

        this.nom = nom;
        this.descrption = descrption;
        this.prerequis = prerequis;
        this.ressource = ressource;
        this.date = date;
        this.idFormation = 3;
        this.duree = duree;

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
        if (!ressource.endsWith(".mp3") && !ressource.endsWith(".mp4") && !ressource.endsWith(".jpg") && !ressource.endsWith(".pdf") && !ressource.endsWith(".txt")) {
            throw new IllegalArgumentException("La ressource doit être un fichier avec l'extension mp3, mp4, jpg, pdf ou txt.");
        }
        return ressource;
    }

    public void setRessource(String ressource) {
        if (!ressource.endsWith(".mp3") && !ressource.endsWith(".mp4") && !ressource.endsWith(".jpg") && !ressource.endsWith(".pdf") && !ressource.endsWith(".txt")) {
            throw new IllegalArgumentException("La ressource doit être un fichier avec l'extension mp3, mp4, jpg, pdf ou txt.");
        }
        this.ressource = ressource;
    }
    public Date getDate() {
        Date coursDate = date;
        if ( coursDate.after(new Date(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("La date du cours doit être dans le passé.");
        }
        else
        return date;
    }

    public void setDate(Date date) {

        Date coursDate = date;
        if ( coursDate.after(new Date(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("La date du cours doit être dans le passé.");
        }
        else
            this.date = date;
    }


    public int getDuree() {

        if (duree <= 0) {
            throw new IllegalArgumentException("La durée doit être supérieure à zéro.");
        }
        return duree;
    }

    public void setDuree(int duree) {
        if (duree <= 0) {
            throw new IllegalArgumentException("La durée doit être supérieure à zéro.");
        }
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
        return id_cours == cours.id_cours && idFormation == cours.idFormation && Objects.equals(nom, cours.nom) && Objects.equals(descrption, cours.descrption) && Objects.equals(prerequis, cours.prerequis) && Objects.equals(ressource, cours.ressource) && Objects.equals(date, cours.date) && Objects.equals(duree, cours.duree) && Objects.equals(evaluation, cours.evaluation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_cours, nom, descrption, prerequis, ressource, date, idFormation, duree, evaluation);
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
                        "| %-13s | %-15s | %-9s | %-9s | %-20s | %-11d | %-2dh %-2dmin %-2dsc | %-12s |%n",
                nom, descrption, prerequis, ressource, date, idFormation, heures, minutes, secondes, evaluation);
    }
}
