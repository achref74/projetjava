package edu.esprit.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Evaluation {
    private int id_e ;
    private int duree ;
    private String nom ;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    private int note ;

   private  Set<Question> questions;

    public int getId_e() {
        return id_e;
    }

    public void setId_e(int id_e) {
        this.id_e = id_e;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Question q) {
        this.questions.add(q);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evaluation that = (Evaluation) o;
        return id_e == that.id_e && duree == that.duree && note == that.note && Objects.equals(questions, that.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_e, duree, note, questions);
    }

    @Override
    public String toString() {
        return "Evaluation   {  id : " +id_e +", "+
        "duree=" + duree +
                ", note=" + note +
                ", questions=" + questions +
                '}';
    }

    public Evaluation(int id_e, int duree, String nom, int note) {
        this.id_e = id_e;
        this.duree = duree;
        this.nom = nom;
        this.note = note;

     questions = new HashSet<>();
    }
    public Evaluation(int id_e, int duree, String nom, int note, Set<Question> questions) {
        this.id_e = id_e;
        this.duree = duree;
        this.nom = nom;
        this.note = note;

        this.questions = questions;
    }
}
