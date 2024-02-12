package edu.esprit.entities;

import java.sql.Date;
import java.util.Objects;

public class Formateur extends User {
    private String specialite;
    private String niveauAcademique;
    private int disponibilite;
    private String cv;

    public Formateur(int id, String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp, String specialite, String niveauAcademique, int disponibilite, String cv) {
        super(id, nom, prenom, email, dateNaissance, adresse, numtel, mdp);
        this.specialite = specialite;
        this.niveauAcademique = niveauAcademique;
        this.disponibilite = disponibilite;
        this.cv = cv;
    }

    public Formateur(String nom, String prenom, String email, Date dateNaissance, String adresse, int numtel, String mdp, String specialite, String niveauAcademique, int disponibilite, String cv) {
        super(nom, prenom, email, dateNaissance, adresse, numtel, mdp);
        this.specialite = specialite;
        this.niveauAcademique = niveauAcademique;
        this.disponibilite = disponibilite;
        this.cv = cv;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getNiveauAcademique() {
        return niveauAcademique;
    }

    public void setNiveauAcademique(String niveauAcademique) {
        this.niveauAcademique = niveauAcademique;
    }

    public int getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(int disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Formateur formateur = (Formateur) o;
        return disponibilite == formateur.disponibilite && Objects.equals(specialite, formateur.specialite) && Objects.equals(niveauAcademique, formateur.niveauAcademique) && Objects.equals(cv, formateur.cv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialite, niveauAcademique, disponibilite, cv);
    }

    @Override
    public String toString() {
        return "Formateur{" +super.toString()+
                "specialite='" + specialite + '\'' +
                ", niveauAcademique='" + niveauAcademique + '\'' +
                ", disponibilite=" + disponibilite +
                ", cv='" + cv + '\'' +
                "} " ;
    }
}
