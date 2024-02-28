package edu.esprit.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Evaluation {
    private int id_e ;

    private String nom ;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom)   {
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



    public int getNote() {

        if ((note < 0)||(note >20)) {
            throw new IllegalArgumentException("La note doit être comprise entre zéro et 20 .");
        }
        return note;
    }

    public void setNote(int note) {

        if ((note < 0)||(note >20)) {
            throw new IllegalArgumentException("La note doit être comprise entre zéro et 20 .");
        }

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
        return id_e == that.id_e && note == that.note && Objects.equals(questions, that.questions);
    }

    @Override
    public String toString() {


        return String.format("Evaluation%n" +
                        "----------------------------------------------%n" +
                        " Note | Questions           |%n" +
                        "----------------------------------------------%n" +
                        note, questions);
    }

    public Evaluation(int id_e,  String nom, int note) {
        this.id_e = id_e;

        this.nom = nom;
        this.note = note;

     questions = new HashSet<>();
    }
    public Evaluation(int id_e,  String nom, int note, Set<Question> questions) {
        this.id_e = id_e;

        this.nom = nom;
        this.note = note;

        this.questions = questions;
    }
    public Evaluation( String nom, int note) {


        this.nom = nom;
        this.note = note;


    }

}
