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
import java.math.BigInteger;
import java.net.URL;
import java.util.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class AfficherEvaluation implements Initializable {
    public static final String ACCOUNT_SID = "ACb65e44c3e078d2e73b833e4dcb25007a";
    public static final String AUTH_TOKEN = "2c8c949cdf777285a3bf4fa28b00192f";
    public static final String TWILIO_NUMBER = "+14846015242";
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
                listeQuestions.clear(); //
                List<Question> questionsList = new ArrayList<>(questionsSet);
                Collections.shuffle(questionsList);
                // Sélectionner les 5 premières questions après le mélange
                listeQuestions = questionsList.subList(0, Math.min(5, questionsList.size()));
                if (!listeQuestions.isEmpty()) {

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
            grid.getChildren().clear();

            Question currentQuestion = listeQuestions.get(currentQuestionIndex);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Question.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                GridPane.setConstraints(anchorPane, 0, 0);

                QuestionController questionController = fxmlLoader.getController();
                if (questionController != null) {
                    questionController.setData(currentQuestion, myListener);
                    questionController.setParentController(this);
                    grid.getChildren().add(anchorPane);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void nextQuestion() {
        if (currentQuestionIndex < listeQuestions.size() - 1) {
            currentQuestionIndex++;
            loadCurrentQuestion();
        } else {

            double sumPoints = 0.0;
            for (Question question : listeQuestions) {
                sumPoints += question.getPoint();
            }


            double sumPointsFinal = 0.0;
            for (Question question : listeQuestions) {
                if (question.getReponse().equals(question.getCrx())) {
                    double pointsFinal = question.getPoint() * e.getNote() / sumPoints;
                    sumPointsFinal += pointsFinal;
                } else {
                    sumPointsFinal += 0;
                }
            }

            String formattedPointsFinal = String.format("%.2f", sumPointsFinal);
            System.out.println("Note finale de l'évaluation : " + formattedPointsFinal);

            // Déterminer la mention
            String mention;
            double halfNote = e.getNote() / 2.0;
            double threeQuartersNote = e.getNote() * 3.0 / 4.0;

            if (sumPointsFinal < halfNote) {
                mention = "Echec";
            } else if (sumPointsFinal >= halfNote && sumPointsFinal < threeQuartersNote) {
                mention = "Reussite";
            } else if (sumPointsFinal >= threeQuartersNote && sumPointsFinal < e.getNote()) {
                mention = "Bien";
            } else if (sumPointsFinal >= e.getNote()) {
                mention = "Excellent";
            } else {
                mention = ""; // Au cas où aucun des cas ci-dessus n'est valide
            }

            // Envoyer la note et la mention par SMS
            String messageBody = "Votre note finale est : " + formattedPointsFinal + ". Mention: " + mention + ".";
        //   sendSms("+21653946055", TWILIO_NUMBER, messageBody);
        }

    }
        private void sendSms(String to, String from, String body) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message
                    .creator(new PhoneNumber(to),
                            new PhoneNumber(from),
                            body)
                    .create();

            System.out.println("SMS envoyé avec succès: SID=" + message.getSid());
        }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button retour;

    public void retour(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCoursClient.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
}