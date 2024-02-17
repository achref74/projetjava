package edu.esprit.entities;

import java.util.Objects;

public class Avis {
    private int idAvis;
    private int rate;
    private int idUser;
    private int idFormation;
    private String nomUser;
    private String nomF;
    public Avis(){}

    public Avis(int rate, int idUser, int idFormation) {
        this.rate = rate;
        this.idUser = idUser;
        this.idFormation = idFormation;
    }

    public Avis(int rate, String nomF, String nomUser) {
        this.rate = rate;
        this.nomF = nomF;
        this.nomUser = nomUser;
    }

    public Avis(int idAvis, int rate, int idUser, int idFormation) {
        this.idAvis = idAvis;
        this.rate = rate;
        this.idUser = idUser;
        this.idFormation = idFormation;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getNomF() {
        return nomF;
    }

    public void setNomF(String nomF) {
        this.nomF = nomF;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avis avis)) return false;
        return idAvis == avis.idAvis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAvis);
    }

    @Override
    public String toString() {
        return "Avis{" +
                "rate=" + rate +
                ", nomUser='" + nomUser + '\'' +
                ", nomF='" + nomF + '\'' +
                '}';
    }
}
