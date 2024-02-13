package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Certificat {
    private int idCertificat;
    private String titre;
    private String description;
    private Date dateObtention;
    private int nbrCours;
    private int idUser;
    private int idFormation;



    public Certificat(int idCertificat, String titre, String description, Date dateObtention, int nbrCours) {
        this.idCertificat = idCertificat;
        this.titre = titre;
        this.description = description;
        this.dateObtention = dateObtention;
        this.nbrCours = nbrCours;
        //this.idUser = idUser;
       // this.idFormation = idFormation;
    }

    public Certificat(String titre, String description, Date dateObtention, int nbrCours) {

        this.titre = titre;
        this.description = description;
        this.dateObtention = dateObtention;
        this.nbrCours = nbrCours;
        //this.idUser = idUser;
        //this.idFormation = idFormation;
    }
    public Certificat(String titre, String description, Date dateObtention, int nbrCours,int idFormation) {

        this.titre = titre;
        this.description = description;
        this.dateObtention = dateObtention;
        this.nbrCours = nbrCours;
        //this.idUser = idUser;
        this.idFormation = idFormation;
    }



    public int getIdCertificat() {
        return idCertificat;
    }

    public void setIdCertificat(int idCertificat) {
        this.idCertificat = idCertificat;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateObtention() {
        return dateObtention;
    }

    public void setDateObtention(Date dateObtention) {
        this.dateObtention = dateObtention;
    }

    public int getNbrCours() {
        return nbrCours;
    }

    public void setNbrCours(int nbrCours) {
        this.nbrCours = nbrCours;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    @Override
    public String toString() {
        return "Certificat{" +

                " titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", dateObtention=" + dateObtention +
                ", nbrCours=" + nbrCours +
                ", idUser=" + idUser +
                ", idFormation=" + idFormation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificat certificat = (Certificat) o;
        return idCertificat == certificat.idCertificat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCertificat);
    }
}
