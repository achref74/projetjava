package  edu.esprit.Controllers;

import edu.esprit.entities.Evaluation;
import edu.esprit.entities.Question;
import edu.esprit.services.ServiceEvaluation;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.tests.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    private Label nom;

    @FXML
    private Label note;

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
    @FXML
    private Button retourC;

    @FXML
    private Button retourF;

    private String coursId;

    private int currentQuestionIndex = 0;

    private List<Question> listeQuestions = new ArrayList<>();

    public void setCoursId(String coursId) {
        this.coursId = coursId;
        ServiceEvaluation se = new ServiceEvaluation();
        ServiceQuestion sq = new ServiceQuestion();

        try {
            int idInt = Integer.parseInt(coursId);
            e = se.getEvaluationByIdCours(idInt);

            if (e != null) {
                nom.setText(e.getNom());
                note.setText(String.valueOf(e.getNote()));

                Set<Question> questionsSet = sq.getQuestionsByIdEvaluation(e.getId_e());
                listeQuestions.clear(); // Assurez-vous que la liste est vide avant de l'ajouter
                listeQuestions.addAll(questionsSet); // Convertir le Set en List pour un accès indexé

                if (!listeQuestions.isEmpty()) {
                    // Afficher la première question
                    loadCurrentQuestion();
                }
            } else {
                nom.setText("Evaluation non trouvée");
                note.setText("");
            }
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format pour l'ID du cours : " + coursId);
            nom.setText("Erreur de format ID");
            note.setText("");
        }
    }

    private void loadCurrentQuestion() {
        if (currentQuestionIndex < listeQuestions.size()) {
            grid.getChildren().clear(); // Effacer toutes les questions précédemment affichées

            Question currentQuestion = listeQuestions.get(currentQuestionIndex);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Question.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                // Spécifier les contraintes de disposition pour la question dans le GridPane
                GridPane.setConstraints(anchorPane, 0, 0); // Position (0,0)

                QuestionController questionController = fxmlLoader.getController();
                if (questionController != null) {
                    questionController.setData(currentQuestion, myListener);
                    questionController.setParentController(this); // Passer la référence
                    grid.getChildren().add(anchorPane); // Ajouter la question au GridPane
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Méthode pour passer à la question suivante
    public void nextQuestion() {
        if (currentQuestionIndex < listeQuestions.size() - 1) {
            currentQuestionIndex++;
            loadCurrentQuestion();
        } else {
            // Gérer la fin de l'évaluation ici
            System.out.println("Fin de l'évaluation");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button retour;

    public void retour(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Market.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
}