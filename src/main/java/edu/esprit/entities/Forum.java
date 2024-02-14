package edu.esprit.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Forum {
    private int idForum;
    private String titre;
    private LocalDate dateCreation;
    private int nbrMessage;
    private int idUser;
    private int idFormation ;

    public Forum( String titre, int nbrMessage, int idUser, int idFormation) {
        this.titre = titre;
        this.dateCreation = LocalDate.now();
        this.nbrMessage = nbrMessage;
        this.idUser = idUser;
        this.idFormation = idFormation;
    }

    public Forum() {
    }

    public Forum(String titre, LocalDate dateCreation, int nbrMessage, int idUser, int idFormation) {
        this.titre = titre;
        this.dateCreation = dateCreation;
        this.nbrMessage = nbrMessage;
        this.idUser = idUser;
        this.idFormation = idFormation;
    }

    public Forum(int idForum, String titre, int nbrMessage, int idUser, int idFormation) {
        this.idForum = idForum;
        this.titre = titre;
        this.nbrMessage = nbrMessage;
        this.idUser = idUser;
        this.idFormation = idFormation;
    }

    public int getIdForum() {
        return idForum;
    }

    public void setIdForum(int idForum) {
        this.idForum = idForum;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getNbrMessage() {
        return nbrMessage;
    }

    public void setNbrMessage(int nbrMessage) {
        this.nbrMessage = nbrMessage;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forum forum)) return false;
        return idForum == forum.idForum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idForum);
    }

    @Override
    public String toString() {
        return "Forum{" +
                "titre='" + titre + '\'' +
                ", dateCreation=" + dateCreation +
                ", nbrMessage=" + nbrMessage +
                ", idUser=" + idUser +
                ", idFormation=" + idFormation +
                '}';
    }
}
