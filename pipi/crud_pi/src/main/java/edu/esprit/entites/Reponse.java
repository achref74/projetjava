package edu.esprit.entites;

import java.time.LocalDate;

public class Reponse {
    private int id_reponse;
    private String description;
    private LocalDate date;

    public Reponse() {
    }

    public Reponse( String description) {

        this.description = description;
        this.date = LocalDate.now();
    }

    public Reponse(int id_reponse, String description, LocalDate date) {
        this.id_reponse = id_reponse;
        this.description = description;
        this.date = date;
    }

    public int getId_reponse() {
        return id_reponse;
    }

    public void setId_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate() {
        this.date = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id_reponse=" + id_reponse +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
