package edu.esprit.entites;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reponse reponse = (Reponse) o;
        return id_reponse == reponse.id_reponse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_reponse);
    }
}
