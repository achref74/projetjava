package edu.esprit.Controllers;


import edu.esprit.entities.Question;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class QuesController {


    @FXML
    private Label choix1;

    @FXML
    private Label choix2;

    @FXML
    private Label choix3;

    @FXML
    private Label crx;

    @FXML
    private Label duree;

    @FXML
    private Label point;

    @FXML
    private Label ressource;
    private Question question;
    private MyListener myListener;



    public void setData(Question question, MyListener myListener) {
        this.question = question;
        this.myListener = myListener;

        this.ressource.setText(question.getRessource());
        this.duree.setText(String.valueOf(question.getDuree()));
        this.choix1.setText(question.getChoix1());
        this.choix2.setText(question.getChoix2());
        this.choix3.setText(question.getChoix3());
        this.point.setText(String.valueOf(question.getPoint()));
        this.crx.setText(question.getCrx());





    }
    public Question getQuestion() {
        return this.question;
    }
}
