package edu.esprit.entities;
import javax.annotation.processing.Generated;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Formation {


    private int idFormation;
    private String nom;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private double prix;
    private int nbrCours;
   private int idUser;
    private int idCategorie;

    private String imageUrl; // Nouvel attribut pour l'URL de l'image


    public Formation(int idFormation, String nom, String description, Date dateDebut, Date dateFin,
                     double prix, int nbrCours) {
        this.idFormation = idFormation;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.nbrCours = nbrCours;


    }
    public Formation(int idFormation, String nom, String description, Date dateDebut, Date dateFin,
                     double prix, int nbrCours, int idUser,int idCategorie) {
        this.idFormation = idFormation;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.nbrCours = nbrCours;
        this.idUser=0;
        this.idCategorie=0;

    }
    public Formation(int idFormation, String nom, String description, Date dateDebut, Date dateFin,
                     double prix, int nbrCours,String imageUrl) {
        this.idFormation = idFormation;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.nbrCours = nbrCours;
       this.imageUrl=imageUrl;

    }
public Formation(){}
    public Formation(String nom, String description, Date dateDebut, Date dateFin,double prix, int nbrCours,int idUser,int idCategorie) {

        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.nbrCours = nbrCours;
        this.idUser=0;
        this.idCategorie=0;

    }

    public Formation(String nom, String description, Date dateDebut, Date dateFin,double prix, int nbrCours, String imageUrl) {

        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.nbrCours = nbrCours;
        this.idUser=2;
        this.imageUrl = imageUrl; // Initialisez imageUrl

    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Date getDateDebut() {
        return  dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getNbrCours() {
        return nbrCours;
    }

    public void setNbrCours(int nbrCours) {
        this.nbrCours = nbrCours;
    }

   /* public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
*/
    @Override
    public String toString() {
        return "Formation{" +

                " nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", prix=" + prix +
                ", nbrCours=" + nbrCours +
                ", imageUrl='" + imageUrl + '\'' + // Ajout de imageUrl à la sortie
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formation formation = (Formation) o;
        return idFormation == formation.idFormation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFormation);
    }
}
