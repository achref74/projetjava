package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;


public class Offre {

    private int idOffre;

    private double prixOffre;
    private String description;
    private Date dateD;
    private Date dateF;
    private Formation formation;

private int idFormation;



    public Offre(int idOffre,double prixOffre, String description, Date dateD, Date dateF,Formation formation) {
        this.idOffre = idOffre;

        this.prixOffre = prixOffre;
        this.description = description;
        this.dateD = dateD;
        this.dateF = dateF;
        this.formation = formation;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public Offre(double prixOffre, String description, Date dateD, Date dateF, int idFormation) {


        this.prixOffre = prixOffre;
        this.description = description;
        this.dateD = dateD;
        this.dateF = dateF;
        this.idFormation = idFormation;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Offre(double prixOffre, String description, Date dateD, Date dateF, Formation formation) {


        this.prixOffre = prixOffre;
        this.description = description;
        this.dateD = dateD;
        this.dateF = dateF;
         this.formation = formation;
    }
    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }


    public double getPrixOffre() {
        return prixOffre;
    }

    public void setPrixOffre(double prixOffre) {
        this.prixOffre = prixOffre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateD() {
        return dateD;
    }

    public void setDateD(Date dateD) {
        this.dateD = dateD;
    }

    public Date getDateF() {
        return dateF;
    }

    public void setDateF(Date dateF) {
        this.dateF = dateF;
    }






    @Override
    public String toString() {
        return "Outil{" +


                " prixOffre=" + prixOffre +
                ", description='" + description + '\'' +
                ", dateD=" + dateD +
                ", dateF=" + dateF +
                ", idFormation=" + formation.getNom() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offre offre = (Offre) o;
        return idOffre == offre.idOffre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOffre);
    }
}
