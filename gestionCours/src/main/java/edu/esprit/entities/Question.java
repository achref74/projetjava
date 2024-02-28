package edu.esprit.entities;

import java.util.Objects;

public class Question {
    private int id_q;
    private String ressource;
    private int duree;
    private int point;
    private String choix1;
    private String choix2;
    private String choix3;
    private String reponse;
    private String crx;

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

    public String getChoix1() {
        return choix1;
    }

    public void setChoix1(String choix1) {
        this.choix1 = choix1;
    }

    public String getChoix2() {
        return choix2;
    }

    public void setChoix2(String choix2) {
        this.choix2 = choix2;
    }

    public String getChoix3() {
        return choix3;
    }

    public void setChoix3(String choix3) {
        this.choix3 = choix3;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getCrx() {
        return crx;
    }

    public void setCrx(String crx) {
        this.crx = crx;
    }

    public Question(int id_q, String ressource, int duree, int point, String choix1, String choix2, String choix3, String reponse, String crx) {
        this.id_q = id_q;
        this.ressource = ressource;
        this.duree = duree;
        this.point = point;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.reponse = reponse;
        this.crx = crx;
    }

    // Getters and Setters for the new attributes

    // Equals and Hashcode method including new attributes

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id_q == question.id_q && duree == question.duree && point == question.point &&
                Objects.equals(ressource, question.ressource) &&
                Objects.equals(choix1, question.choix1) &&
                Objects.equals(choix2, question.choix2) &&
                Objects.equals(choix3, question.choix3) &&
                Objects.equals(reponse, question.reponse) &&
                Objects.equals(crx, question.crx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_q, ressource, duree, point, choix1, choix2, choix3, reponse, crx);
    }

    // ToString method including new attributes

    @Override
    public String toString() {
        return "Question{" +
                "id_q=" + id_q +
                ", ressource='" + ressource + '\'' +
                ", duree=" + duree +
                ", point=" + point +
                ", choix1='" + choix1 + '\'' +
                ", choix2='" + choix2 + '\'' +
                ", choix3='" + choix3 + '\'' +
                ", reponse='" + reponse + '\'' +
                ", crx='" + crx + '\'' +
                '}';
    }
}