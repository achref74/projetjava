package edu.esprit.Controllers;

import edu.esprit.entities.Evaluation;
import edu.esprit.entities.Question;
import edu.esprit.services.ServiceEvaluation;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AfficherEvaluation implements Initializable {

    @FXML
    private VBox Evaluation;

    @FXML
    private GridPane grid;
    private MyListener myListener;

    @FXML
    private Button modifierButtonClick;

    @FXML
    private TextField deuxiemechoix;

    @FXML
    private TextField duree;

    @FXML
    private TextField nom;

    @FXML
    private TextField note;

    @FXML
    private TextField points;

    @FXML
    private TextField premierchoix;

    @FXML
    private TextField quatriemechoix;

    @FXML
    private TextField ressource;

    @FXML
    private TextField troisiemechoix;

    @FXML
    private ScrollPane scroll;
    private Set<Question> liste = new HashSet<>();
    private List<String> colorPalette = new ArrayList<>();
    private Evaluation e;

    private String coursId;

    // Méthode pour définir l'ID du cours
    public void setCoursId(String coursId) {
        this.coursId = coursId;
        ServiceEvaluation se = new ServiceEvaluation();
        ServiceQuestion sq = new ServiceQuestion(); // Création du service de question ici

        try {
            // Convertir coursId en int
            int idInt = Integer.parseInt(coursId);

            // Récupérer l'évaluation par ID
            e = se.getEvaluationByIdCours(idInt);

            // Vérifier si l'évaluation n'est pas null
            if (e != null) {
                // Définir le nom et la note de l'évaluation dans les TextField
                nom.setText(e.getNom());
                note.setText(String.valueOf(e.getNote()));

                // Récupérer les questions par ID d'évaluation
                liste = sq.getQuestionsByIdEvaluation(e.getId_e());

                // Une fois que la liste des questions est récupérée, vous pouvez appeler la méthode pour initialiser le contenu de votre GridPane
                initializeQuestionsGrid();
            } else {
                // Gestion du cas où l'évaluation est null
                nom.setText("Evaluation non trouvée");
                note.setText("");
            }
        } catch (NumberFormatException e) {
            // Gestion de l'erreur de conversion de l'ID
            System.err.println("Erreur de format pour l'ID du cours : " + coursId);
            nom.setText("Erreur de format ID");
            note.setText("");
        }

        // Ajouter les couleurs à la palette
        colorPalette.addAll(List.of(
                "#D4A5A5", "#A0522D", "#8B4513", "#CD853F", "#D2B48C",
                "#BC8F8F", "#F4A460", "#DAA520", "#8B4513", "#46637F",
                "#44505E", "#7D5147", "#7F5A45", "#7E8C6B"
        ));
    }

    // Méthode pour initialiser le GridPane avec les questions
    private void initializeQuestionsGrid() {
        // Parcourir la liste des questions et ajouter les contrôleurs dans le GridPane
        int column = 0;
        int row = 1;
        try {
            for (Question question : liste) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Question.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                QuestionController questionController = fxmlLoader.getController();

                if (questionController != null) {
                    questionController.setData(question, myListener);
                    grid.add(anchorPane, column++, row);

                    // Réinitialiser la colonne et passer à la ligne suivante si nécessaire
                    if (column == 1) {
                        column = 0;
                        row++;
                    }
                } else {
                    System.err.println("questionController est null");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}