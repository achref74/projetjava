package edu.esprit.Controllers;


import edu.esprit.entities.Cours;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class CoursController {
    @FXML
    private Label date;

    @FXML
    private Label description;

    @FXML
    private Label duree;

    @FXML
    private Label nom;

    @FXML
    private Label prerequis;

    @FXML
    private Label ressource;
    private Cours cours;
    private MyListener myListener;

    public CoursController() {
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListener(this.cours);
    }

    public void setData(Cours cours, MyListener myListener) {
        this.cours = cours;
        this.myListener = myListener;
        this.nom.setText(cours.getNom());
        this.description.setText(cours.getDescrption());
        this.date.setText(String.valueOf(cours.getDate()));

        long heures = cours.getDuree() / 3600;
        long minutes = (cours.getDuree() % 3600) / 60;
        long secondes = cours.getDuree() % 60;
        this.duree.setText(heures + "h " + minutes + "min " + secondes + "sc");

        this.prerequis.setText(cours.getPrerequis());
        this.ressource.setText(cours.getRessource());


    }
    public Cours getCours() {
        return this.cours;
    }
}
