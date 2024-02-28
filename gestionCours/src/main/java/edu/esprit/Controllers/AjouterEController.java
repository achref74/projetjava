package edu.esprit.Controllers;

import edu.esprit.entities.Evaluation;
import edu.esprit.entities.Question;
import edu.esprit.services.ServiceEvaluation;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.tests.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AjouterEController {

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
    private VBox Evaluation1;





    private Set<Question> liste = new HashSet<>();
    private List<String> colorPalette = new ArrayList<>();
    private edu.esprit.entities.Evaluation e;

    private String coursId;
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
              //  initializeQuestionsGrid();
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
    @FXML
    void retour(ActionEvent event) {

    }
    @FXML
    private void ajouterEvaluation() {
        // Vérifier que le nom et la note ne sont pas vides
        if (!nom.getText().isEmpty() && !note.getText().isEmpty()) {
            try {
                // Récupérer le nom et la note depuis les champs TextField
                String nomEvaluation = nom.getText();
                int noteEvaluation = Integer.parseInt(note.getText());

                // Créer une nouvelle instance d'Evaluation
                Evaluation nouvelleEvaluation = new Evaluation(nomEvaluation, noteEvaluation);

                // Appeler la méthode d'ajout d'évaluation du service avec l'ID du cours
                ServiceEvaluation serviceEvaluation = new ServiceEvaluation();
                serviceEvaluation.ajouter(nouvelleEvaluation, Integer.parseInt(coursId));

                // Afficher un message de succès
                System.out.println("Nouvelle évaluation ajoutée avec succès : " + nouvelleEvaluation);

                // Réinitialiser les champs TextField
                nom.clear();
                note.clear();

                // Actualiser l'affichage des évaluations dans le GridPane
                setCoursId(coursId); // Réafficher les évaluations après l'ajout de la nouvelle évaluation
            } catch (NumberFormatException e) {
                // Gérer l'erreur si la note n'est pas un nombre valide
                System.err.println("Erreur lors de la conversion de la note en nombre : " + e.getMessage());
            }
        } else {
            // Afficher un message d'erreur si le nom ou la note est vide
            System.err.println("Le nom ou la note de l'évaluation est vide !");
        }
    }

}
