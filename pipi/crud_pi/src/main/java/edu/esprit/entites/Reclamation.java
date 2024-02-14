package edu.esprit.entites;

import edu.esprit.services.IService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reclamation {
    private int id_reclamation;
    private int id_user;
    private int id_outil;
    private int id_formation;
    private String Description;
    private List<Reponse> responses = new ArrayList<>();

    private LocalDate date_reclamation;

    public Reclamation(int id_reclamation, int user, int outil, int formation,  LocalDate date_reclamation) {
        this.id_reclamation = id_reclamation;
        this.id_user = user;
        this.id_outil = outil;
        this.id_formation = formation;
        responses = new ArrayList<>();
        this.date_reclamation = date_reclamation;
    }
    public Reclamation(int id_reclamation, int user, int outil, int formation) {
        this.id_reclamation = id_reclamation;
        this.id_user = user;
        this.id_outil = outil;
        this.id_formation = formation;
        responses = new ArrayList<>();
        this.date_reclamation = LocalDate.now();
    }
}
