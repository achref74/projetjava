package edu.esprit.entites;

import edu.esprit.services.IService;
import edu.esprit.entites.Reponse;
import edu.esprit.services.ServiceReponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Reclamation {
    private int id_reclamation;
    private User user;
    private Outil outil;
    private Formation formation;
    private String description;

    private LocalDateTime date_reclamation;

    public Reclamation() {
    }

    public Reclamation(int id_reclamation, User user, Outil outil, Formation formation, String description , LocalDateTime date_reclamation) {
        this.id_reclamation = id_reclamation;
        this.user = user;
        this.outil = outil;
        this.formation = formation;
        this.description = description;
        this.date_reclamation = date_reclamation;
    }
    public Reclamation( User user, Outil outil, Formation formation,String description ) {

        this.user = user;
        this.outil = outil;
        this.formation = formation;
        this.description = description;
        this.date_reclamation = LocalDateTime.now();
    }
    public Reclamation( int id_reclamation ,User user, Outil outil, Formation formation,String description) {
        this.id_reclamation = id_reclamation;
        this.user = user;
        this.outil = outil;
        this.formation = formation;
        this.description = description;


    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Outil getOutil() {
        return outil;
    }

    public void setOutil(Outil outil) {
        this.outil = outil;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public LocalDateTime getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_reclamation() {
        this.date_reclamation = LocalDateTime.now();
    }

    @Override
    public String toString() {
        ServiceReponse serviceReponse = new ServiceReponse();
        return "Reclamation{" +
                "id_reclamation=" + id_reclamation +
                ", id_user=" + user.getId_user() +
                ", id_outil=" + outil.getId_outil() +
                ", id_formation=" + formation.getId_formation() +
                ",reponses= " +  serviceReponse.getByReclamationId(id_reclamation) +
                ", description='" + description + '\'' +
                ", date_reclamation=" + date_reclamation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return id_reclamation == that.id_reclamation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_reclamation);
    }
}
