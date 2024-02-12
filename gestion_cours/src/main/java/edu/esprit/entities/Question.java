package edu.esprit.entities;

import java.util.Objects;

public class Question {
    public Question(String ressource) {
        this.ressource = ressource;
    }

    private int id_q ;
    private String ressource ;

    public int getId_q() {
        return id_q;
    }

    public void setId_q(int id_q) {
        this.id_q = id_q;
    }

    public String getRessource() {
        return ressource;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    @Override
    public String toString() {
        return "Question{ id question "+ id_q + "/"+
                "ressource=" + ressource + "/" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id_q == question.id_q && Objects.equals(ressource, question.ressource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_q, ressource);
    }

    public Question(int id_q, String ressource) {
        this.id_q = id_q;
        this.ressource = ressource;
    }
}
