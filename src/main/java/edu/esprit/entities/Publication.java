package edu.esprit.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Publication {
    private  int idP ;
    private LocalDateTime dateCreation;
    private  String contenu;
    private  String image;
    private int nbLike;
    private int idForum;
    private int idUser;

    public Publication( String contenu, String image, int nbLike, int idForum, int idUser) {
        this.dateCreation = LocalDateTime.now();
        this.contenu = contenu;
        this.image = image;
        this.nbLike = nbLike;
        this.idForum = idForum;
        this.idUser = idUser;
    }

    public Publication(LocalDateTime dateCreation, String contenu, String image, int nbLike, int idForum, int idUser) {
        this.dateCreation = dateCreation;
        this.contenu = contenu;
        this.image = image;
        this.nbLike = nbLike;
        this.idForum = idForum;
        this.idUser = idUser;
    }
    public Publication(){}

    public Publication(int idP, String contenu, String image, int nbLike, int idForum, int idUser) {
        this.idP = idP;
        this.contenu = contenu;
        this.image = image;
        this.nbLike = nbLike;
        this.idForum = idForum;
        this.idUser = idUser;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNbLike() {
        return nbLike;
    }

    public void setNbLike(int nbLike) {
        this.nbLike = nbLike;
    }

    public int getIdForum() {
        return idForum;
    }

    public void setIdForum(int idForum) {
        this.idForum = idForum;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publication that)) return false;
        return idP == that.idP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idP);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "dateCreation=" + dateCreation +
                ", contenu='" + contenu + '\'' +
                ", image='" + image + '\'' +
                ", nbLike=" + nbLike +
                ", idForum=" + idForum +
                ", idUser=" + idUser +
                '}';
    }
}
