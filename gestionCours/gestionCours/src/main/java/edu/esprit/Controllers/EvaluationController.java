package edu.esprit.Controllers;


import edu.esprit.entities.Evaluation;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class EvaluationController {


    @FXML
    private Label note;

    @FXML
    private Label nom;


    private Evaluation evaluation;
    private MyListener myListener;

    public EvaluationController() {
    }



    public void setData(Evaluation evaluation, MyListener myListener) {
        this.evaluation = evaluation;
        this.myListener = myListener;
        this.nom.setText(evaluation.getNom());

        this.note.setText(String.valueOf(evaluation.getNote()));




    }
    public Evaluation getEvaluation() {
        return this.evaluation;
    }
}
