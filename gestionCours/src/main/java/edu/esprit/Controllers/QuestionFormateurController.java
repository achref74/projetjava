package edu.esprit.Controllers;

import edu.esprit.entities.Question;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.tests.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class QuestionFormateurController {

    @FXML
    private CheckBox deuxiemechoix;

    @FXML
    private TextField duree;

    @FXML
    private TextField points;

    @FXML
    private CheckBox premierchoix;

    @FXML
    private TextField text1;

    @FXML
    private TextField text2;

    @FXML
    private TextField text3;
    @FXML
    private TextField crx;
    @FXML
    private TextField ressource;

    @FXML
    private CheckBox troisiemechoix;
    private MyListener myListener;
    private Question question ;
    public QuestionFormateurController() {
    }


    @FXML
    private void cliquer(MouseEvent mouseEvent) {
        this.myListener.onClickListner(this.question);
    }

    public void set(Question question, MyListener myListener) {

this.crx.setText(question.getCrx());
        this.question = question;
        this.myListener = myListener;
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
    String reponse ;
    @FXML
    void modifier(ActionEvent event) {
        try {       if (premierchoix.isSelected())
         reponse=text1.getText();
         else if (deuxiemechoix.isSelected()) {
            reponse= text2.getText();
        } else if (troisiemechoix.isSelected()) {
            reponse=text3.getText();
        }
        question.setRessource(ressource.getText());
        question.setReponse(reponse);
         question.setChoix1(text1.getText());
            question.setChoix2(text2.getText());
            question.setChoix3(text3.getText());
            question.setCrx(

          crx.getText()  );
            question.setDuree(Integer.parseInt(duree.getText()));
            question.setPoint(Integer.parseInt(points.getText()));

            ServiceQuestion serviceQuestion = new ServiceQuestion(); // Vous devriez peut-être réfléchir à une meilleure gestion de la création du service
            serviceQuestion.modifiercorrection(question);
        } catch (NumberFormatException e) {
            // Gérez l'exception si la conversion de chaînes en entiers échoue
            // Par exemple, affichez un message d'erreur à l'utilisateur
            e.printStackTrace(); // Pour afficher la trace de l'erreur dans la console
        }
    }
    @FXML
    void supprimer(ActionEvent event) {
        if (question != null) {
            ServiceQuestion serviceQuestion = new ServiceQuestion();
            serviceQuestion.supprimer(question.getId_q()); // Supprimez la question en utilisant son identifiant

            // Actualiser l'affichage après la suppression de la question
            actualiserAffichage();


        } else {
            // Afficher un message d'erreur si la question est nulle
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Impossible de supprimer la question car elle n'existe pas !");
            errorAlert.showAndWait();
        }
    }
    private void actualiserAffichage() {
        // Effacer les champs de texte ou réinitialiser les données nécessaires à l'affichage
        text1.clear();
        text2.clear();
        text3.clear();
        crx.clear();
        ressource.clear();
        duree.clear();
        points.clear();
    }
}



