package edu.esprit.entities;

import java.util.Objects;

public class Question {
    public Question(String ressource) {
        this.ressource = ressource;
    }

    private int id_q ;
    private String ressource ;

    private int duree ;
    private int point ;

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getId_q() {
        return id_q;
    }

    public void setId_q(int id_q) {
        this.id_q = id_q;
    }

    public String getRessource() {

        if (!ressource.endsWith(".mp3") && !ressource.endsWith(".mp4") && !ressource.endsWith(".jpg") && !ressource.endsWith(".pdf") && !ressource.endsWith(".txt")) {
            throw new IllegalArgumentException("La ressource doit être un fichier avec l'extension mp3, mp4, jpg, pdf ou txt.");
        }
        return ressource;
    }

    public void setRessource(String ressource) {
        if (!ressource.endsWith(".mp3") && !ressource.endsWith(".mp4") && !ressource.endsWith(".jpg") && !ressource.endsWith(".pdf") && !ressource.endsWith(".txt")) {
            throw new IllegalArgumentException("La ressource doit être un fichier avec l'extension mp3, mp4, jpg, pdf ou txt.");
        }
        this.ressource = ressource;
    }

    public Question(int id_q, String ressource, int duree, int point) {
        this.id_q = id_q;
        this.ressource = ressource;
        this.duree = duree;
        this.point = point;
    }

    public Question(String ressource, int duree, int point) {
        this.ressource = ressource;
        this.duree = duree;
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id_q == question.id_q && duree == question.duree && point == question.point && Objects.equals(ressource, question.ressource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_q, ressource, duree, point);
    }

    @Override
    public String toString() {
        return "Question{" +
                "ressource='" + ressource + '\'' +
                ", duree=" + duree +
                ", point=" + point +
                '}';
    }
}
