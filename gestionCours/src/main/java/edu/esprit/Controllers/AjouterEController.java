package edu.esprit.Controllers;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AjouterEController implements Initializable {

    @FXML
    private VBox Evaluation;

    @FXML
    private GridPane grid;
    private MyListener myListener;

    @FXML
    private Button modifierButtonClick;

    @FXML
    private Button modifier1;

    @FXML
    private TextField nom;

    @FXML
    private TextField note;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField crx;

    @FXML
    private TextField deuxiemechoix;

    @FXML
    private TextField duree;

    @FXML
    private TextField point;

    @FXML
    private TextField premierchoix;

    @FXML
    private TextField ressource;

    @FXML
    private TextField troisiemechoix;

    @FXML
    private VBox Evaluation1;
    @FXML
    private Button retourC;
    @FXML
    private Label msg ;

    @FXML
    private Button retourF;

    @FXML
    private Button ajouterBtn;

    private Set<Question> liste = new HashSet<>();
    private List<String> colorPalette = new ArrayList<>();
    private Evaluation e;

    private String coursId;

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
                liste = sq.getQuestionsByIdEvaluation(e.getId_e());
            } else {
                nom.setText("");
                note.setText("");
            }
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format pour l'ID du cours : " + coursId);
            nom.setText("Erreur de format ID");
            note.setText("");
        }

        colorPalette.addAll(List.of(
                "#D4A5A5", "#A0522D", "#8B4513", "#CD853F", "#D2B48C",
                "#BC8F8F", "#F4A460", "#DAA520", "#8B4513", "#46637F",
                "#44505E", "#7D5147", "#7F5A45", "#7E8C6B"
        ));
        msg.setVisible(false);
    }

    @FXML
    void retour(ActionEvent event) {


    }
    @FXML
    private void ajouterEvaluation(ActionEvent event) {
        if (nom.getText().isEmpty() || note.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
            try {
                String nomEvaluation = nom.getText();
                int noteEvaluation = Integer.parseInt(note.getText());
                Evaluation nouvelleEvaluation = new Evaluation(nomEvaluation, noteEvaluation);
                ServiceEvaluation serviceEvaluation = new ServiceEvaluation();

                // Vérifier si une évaluation existe déjà pour ce cours et si elle est remplie
                if (e != null && isEvaluationFilled(e)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("L'évaluation existante pour ce cours est déjà remplie !");
                    alert.showAndWait();
                    return; // Arrêter l'exécution de la méthode
                }


                 if(!serviceEvaluation.isnotevalid(Integer.parseInt(note.getText())))
                  msg.setVisible(true);
                    else {
                serviceEvaluation.ajouter(nouvelleEvaluation, Integer.parseInt(coursId));
                System.out.println("Nouvelle évaluation ajoutée avec succès : " + nouvelleEvaluation);
                nom.clear();
                note.clear();
                setCoursId(coursId);}
            } catch (NumberFormatException e) {
                System.err.println("Erreur lors de la conversion de la note en nombre : " + e.getMessage());
            }

    }

    private boolean isEvaluationFilled(Evaluation evaluation) {
        // Ajoutez ici votre logique pour vérifier si une évaluation est remplie.
        // Dans cet exemple, nous supposons qu'une évaluation est remplie si son nom et sa note ne sont pas vides.
        return !evaluation.getNom().isEmpty() && evaluation.getNote() != 0;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ajouterBtn.setOnAction(this::ajouterEvaluation);
        nom.setText("");
        note.setText("0");
        if (e != null) {
            // Afficher une alerte pour informer l'utilisateur
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Le cours a déjà une évaluation. Les informations de cette évaluation ont été pré-remplies.");
            alert.showAndWait();

            // Remplir les champs nom et note avec les valeurs de l'évaluation existante
            nom.setText(e.getNom());
            note.setText(String.valueOf(e.getNote()));



        }
    }

    @FXML
    private void ajouterQuestion(ActionEvent event) {
        String ressourceText = ressource.getText();
        String dureeText = duree.getText();
        String pointText = point.getText();
        String choix1Text = premierchoix.getText();
        String choix2Text = deuxiemechoix.getText();
        String choix3Text = troisiemechoix.getText();
        String crxText = crx.getText();

        // Vérification des champs vides
        if (ressourceText.isEmpty() || dureeText.isEmpty() || pointText.isEmpty() || choix1Text.isEmpty() || choix2Text.isEmpty() || choix3Text.isEmpty() || crxText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        int dureeValue = Integer.parseInt(dureeText);
        int pointValue = Integer.parseInt(pointText);





        Question question = new Question(ressourceText, dureeValue, pointValue, choix1Text, choix2Text, choix3Text, crxText);
        int id_e = e.getId_e();

        ServiceQuestion serviceQuestion = new ServiceQuestion();
        if (serviceQuestion.questionExisteParRessource(ressourceText)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Cette quetsion existe déja !");
            alert.showAndWait();
            return;
        }
        serviceQuestion.ajouter(question, id_e);

        System.out.println("Nouvelle question ajoutée avec succès : " + question);

        ressource.clear();
        duree.clear();
        point.clear();
        premierchoix.clear();
        deuxiemechoix.clear();
        troisiemechoix.clear();
        crx.clear();

        setCoursId(coursId);
    }
    public void retourClient(javafx.event.ActionEvent actionEvent) {


    }
    public void retourFormateur(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Market.fxml"));
            retourF.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }
}