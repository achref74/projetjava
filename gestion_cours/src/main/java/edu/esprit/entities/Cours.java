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
        return id_cours == cours.id_cours && idFormation == cours.idFormation && Objects.equals(nom, cours.nom) && Objects.equals(descrption, cours.descrption) && Objects.equals(prerequis, cours.prerequis) && Objects.equals(ressource, cours.ressource) && Objects.equals(date, cours.date) && Objects.equals(duree, cours.duree) && Objects.equals(evaluation, cours.evaluation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_cours, nom, descrption, prerequis, ressource, date, idFormation, duree, evaluation);
    }

    @Override
    public String toString() {
        return "Cours{" +
                "nom='" + nom + '\'' +
                ", descrption='" + descrption + '\'' +
                ", prerequis='" + prerequis + '\'' +
                ", ressource='" + ressource + '\'' +
                ", date=" + date +
                ", idFormation=" + idFormation +
                ", duree='" + duree + '\'' +
                ", evaluation=" + evaluation +
                '}';
    }


}
