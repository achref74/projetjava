package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.entities.Question;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.tests.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;

public class QuestionController {
    @FXML
    private CheckBox deuxiemechoix;

    @FXML
    private TextField duree;

    @FXML
    private TextField points;

    @FXML
    private CheckBox premierchoix;

    @FXML
    private TextField reponse;

    @FXML
    private TextField ressource;

    @FXML
    private TextField text1;

    @FXML
    private TextField text2;

    @FXML
    private TextField text3;

    @FXML
    private CheckBox troisiemechoix;



    @FXML
    void modifierQuestion(ActionEvent event) {if (premierchoix.isSelected()) {
        reponse.setText(text1.getText());
    } else if (deuxiemechoix.isSelected()) {
        reponse.setText(text2.getText());
    } else if (troisiemechoix.isSelected()) {
        reponse.setText(text3.getText());
    }
        // Enregistrer les modifications dans la base de données
        question.setReponse(reponse.getText());
        ServiceQuestion serviceQuestion =new ServiceQuestion(); serviceQuestion.modifierReponse(question);

    }
    private MyListener myListener;
    private Question question ;
    public QuestionController() {
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListner(this.question);
    }

    public void setData(Question question, MyListener myListener) {
        this.question = question;
        this.myListener = myListener;
        this.reponse.setText(question.getReponse());
        this.text1.setText(question.getChoix1());
        this.text2.setText(question.getChoix2());
        this.text3.setText(question.getChoix3());
        this.ressource.setText(question.getRessource());
        this.duree.setText(""+question.getDuree());
        this.points.setText(""+question.getPoint());


    }


    public Question getQuestion() {
        return this.question;
    }
}



