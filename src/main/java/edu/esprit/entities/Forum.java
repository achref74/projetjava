package edu.esprit.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Forum {
    private int idForum;
    private String titre;
    private LocalDate dateCreation;
    private String description;
    private int idFormation ;

    public Forum( String titre,String description , int idFormation) {
        this.titre = titre;
        this.dateCreation = LocalDate.now();
        this.description = description ;
        this.idFormation = idFormation;
    }

    public Forum() {
    }

    public Forum(String titre, LocalDate dateCreation, String description, int idFormation) {
        this.titre = titre;
        this.dateCreation = dateCreation;
        this.description = description;
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



    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", description='" + description + '\'' +
                ", idFormation=" + idFormation +
                '}';
    }
}
