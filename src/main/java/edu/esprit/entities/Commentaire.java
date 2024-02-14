package edu.esprit.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Commentaire {
    private int idCommentaire ;
    private LocalDate dateCreation;
    private String description;
    private int idForum;

    private String contenu;

    public Commentaire() {

    }

    public Commentaire(int idCommentaire, String description, int idForum, String contenu) {
        this.idCommentaire = idCommentaire;
        this.description = description;
        this.idForum = idForum;
        this.contenu = contenu;
    }

    public Commentaire(String description, int idForum, String contenu) {
        this.dateCreation = LocalDate.now();
        this.description = description;
        this.idForum = idForum;
        this.contenu = contenu;

    }

    public Commentaire( LocalDate dateCreation,String description, int idForum, String contenu) {
        this.dateCreation = dateCreation;
        this.description = description;
        this.idForum = idForum;
        this.contenu = contenu;

    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "dateCreation=" + dateCreation +
                ", description='" + description + '\'' +
                ", contenu='" + contenu + '\'' +
                ", idForum=" + idForum +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commentaire that)) return false;
        return idCommentaire == that.idCommentaire;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommentaire);
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setIdForum(int idForum) {
        this.idForum = idForum;
    }



    public int getIdCommentaire() {
        return idCommentaire;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public String getContenu() {
        return contenu;
    }

    public int getIdForum() {
        return idForum;
    }
}
