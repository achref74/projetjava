package edu.esprit.Controllers;

import edu.esprit.entities.Question;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.tests.MyListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {

    @FXML
    private CheckBox premierchoix;

    @FXML
    private Label text1;

    @FXML
    private CheckBox deuxiemechoix;
    @FXML
    private Label timerLabel;

    @FXML
    private Label text2;

    @FXML
    private CheckBox troisiemechoix;

    @FXML
    private Label text3;

    @FXML
    private Label ressource;



    @FXML
    private Label points;

    @FXML
    private Label reponse;

    private AfficherEvaluation parentController;


    private MyListener myListener;
    private Question question;

    public QuestionController() {
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListner(this.question);
    }

    public void setParentController(AfficherEvaluation parentController) {
        this.parentController = parentController;
    }

    private void setupCheckBoxListeners() {
        premierchoix.setOnAction(event -> {
            if (premierchoix.isSelected()) {
                handleChoice(text1.getText());
            }
        });

        deuxiemechoix.setOnAction(event -> {
            if (deuxiemechoix.isSelected()) {
                handleChoice(text2.getText());
            }
        });

        troisiemechoix.setOnAction(event -> {
            if (troisiemechoix.isSelected()) {
                handleChoice(text3.getText());
            }
        });
    }

    private void handleChoice(String selectedAnswer) {
        if (timeRemaining > 0) {
            reponse.setText(selectedAnswer);
            question.setReponse(selectedAnswer);
            ServiceQuestion serviceQuestion = new ServiceQuestion();
            serviceQuestion.modifierReponse(question);
            disableChoices();

            // Vérifier si la réponse est correcte
            if (selectedAnswer.equals(question.getCrx())) {

                String path = getClass().getResource("/app.wav").toString();
                Media sound = new Media(path);
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play() ;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bonne réponse !");
                alert.setHeaderText("Félicitations !");
                alert.setContentText("La réponse : " + question.getCrx()+" est correcte");
                alert.showAndWait();

            }else { String path = getClass().getResource("/Fail.mp3").toString();
                Media sound = new Media(path);
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play() ;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mauvaise réponse !");
                alert.setContentText("La réponse correcte est : " + question.getCrx());
                alert.showAndWait();}
        }

        if (parentController != null) {
            parentController.nextQuestion();
        }
    }
    private void startTimer(int duration) {
        timeRemaining = duration;

        timeline = new Timeline();
        timeline.setCycleCount(duration); // Définir le nombre de cycles à la durée de la question

        // Définir l'action à effectuer à chaque cycle
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            // Décrémenter le temps restant
            timeRemaining--;
            // Mettre à jour le label du minuteur
            timerLabel.setText(timeRemaining + "s");

            // Vérifier si le temps est écoulé
            if (timeRemaining <= 0) {
                // Arrêter le minuteur lorsque le temps est écoulé
                timeline.stop();
                // Vérifier si une réponse a été donnée
                if (reponse.getText().isEmpty()) {
                    // Afficher une alerte si aucune réponse n'a été donnée
                    Platform.runLater(() -> {   String path = getClass().getResource("/Fail.mp3").toString();
                        Media sound = new Media(path);
                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
                        mediaPlayer.play() ;
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Aucune réponse rendue");
                        alert.setHeaderText("Vous n'avez rendu aucune réponse dans le temps imparti.");

                        alert.showAndWait();
                        // Passer automatiquement à la question suivante
                        parentController.nextQuestion();
                    });
                }
            }
        }));

        timeline.play(); // Démarrer le minuteur
    }
    private void disableChoices() {
        premierchoix.setDisable(true);
        deuxiemechoix.setDisable(true);
        troisiemechoix.setDisable(true);
    }

    public void setData(Question question, MyListener myListener) {
        this.question = question;
        // Initialiser les éléments de l'interface avec les données de la question
        this.reponse.setText("");
        this.text1.setText(question.getChoix1());
        this.text2.setText(question.getChoix2());
        this.text3.setText(question.getChoix3());
        this.ressource.setText(question.getRessource());

        this.points.setText(String.valueOf(question.getPoint()));
        this.question = question;
        // Initialiser le minuteur avec la durée de la question
        startTimer(question.getDuree());
        setupCheckBoxListeners();
    }

    public Question getQuestion() {
        return this.question;
    }

    private Timeline timeline;
    private int timeRemaining;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}