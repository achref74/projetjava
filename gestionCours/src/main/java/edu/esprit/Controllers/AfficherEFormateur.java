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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class AfficherEFormateur implements Initializable {

    @FXML
    private Label msg ;
    @FXML
    private TextField searchField ;

    @FXML
    private VBox Evaluation;

    @FXML
    private GridPane grid;
    private MyListener myListener;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    @FXML
    private TextField nom;

    @FXML
    private TextField note;

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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/QuestionFormateur.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                QuestionFormateurController questionFormateur = fxmlLoader.getController();

                if (questionFormateur != null) {
                    questionFormateur.set(question, myListener);
                    grid.add(anchorPane, column++, row);

                    // Réinitialiser la colonne et passer à la ligne suivante si nécessaire
                    if (column == 1) {
                        column = 0;
                        row++;
                    }
                } else {
                    System.err.println("question pour le formateur est nulle  est null");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    void supprimer(ActionEvent event) {
        if (e != null) {
            // Afficher une boîte de dialogue de confirmation avant de supprimer l'évaluation
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Supprimer l'évaluation ?");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cette évaluation ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé la suppression, donc nous supprimons l'évaluation
                ServiceEvaluation serviceEvaluation = new ServiceEvaluation();
                serviceEvaluation.supprimerEvaluationById(e.getId_e()); // Supprimez l'évaluation en utilisant son identifiant

                // Affichez un message de confirmation à l'utilisateur
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Suppression réussie");
                successAlert.setHeaderText(null);
                successAlert.setContentText("L'évaluation a été supprimée avec succès !");
                successAlert.showAndWait();
                nom.setText("");
                note.setText("");
                // Vous pouvez implémenter une redirection vers une autre vue ou effectuer toute autre action nécessaire ici
            }
        } else {
            // Afficher un message d'erreur si l'évaluation est nulle
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Impossible de supprimer l'évaluation car elle n'existe pas !");
            errorAlert.showAndWait();
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        if (e != null) {
            // Récupérer les valeurs des champs nom et note
            String nomEvaluation = nom.getText();
            int noteEvaluation = Integer.parseInt(note.getText());


            e.setNom(nomEvaluation);
            e.setNote(noteEvaluation);
            ServiceEvaluation serviceEvaluation = new ServiceEvaluation();
            if(!serviceEvaluation.isnotevalid(Integer.parseInt(note.getText())))
                msg.setVisible(true);
            else {

            serviceEvaluation.modifier(e); // Assurez-vous que votre service dispose d'une méthode modifier(Evaluation evaluation)

            // Afficher un message de confirmation à l'utilisateur
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Modification réussie");
            successAlert.setHeaderText(null);
            successAlert.setContentText("L'évaluation a été modifiée avec succès !");
            successAlert.showAndWait();

            // Vous pouvez implémenter une redirection vers une autre vue ou effectuer toute autre action nécessaire ici

        } }else {
            // Afficher un message d'erreur si l'évaluation est nulle
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Impossible de modifier l'évaluation car elle n'existe pas !");
            errorAlert.showAndWait();
        }}

    private Set<Question> filterQuestions(String searchString) {
        // Si la recherche est vide, retourner la liste complète des questions
        if (searchString == null || searchString.isEmpty()) {
            return liste;
        }

        // Filtrer les questions en fonction de la ressource, choix1, choix2, choix3 ou correction
        return liste.stream()
                .filter(question ->
                        question.getRessource().toLowerCase().contains(searchString.toLowerCase()) ||
                                question.getChoix1().toLowerCase().contains(searchString.toLowerCase()) ||
                                question.getChoix2().toLowerCase().contains(searchString.toLowerCase()) ||
                                question.getChoix3().toLowerCase().contains(searchString.toLowerCase()) ||
                                question.getCrx().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toSet());
    }
    private void afficherQuestions(Set<Question> questionsList) {
        // Effacer les questions précédentes du GridPane
        grid.getChildren().clear();

        // Initialiser les variables de position dans le GridPane
        int column = 0;
        int row = 1;

        try {
            // Parcourir la liste des questions filtrées
            for (Question question : questionsList) {
                // Charger le fichier FXML de l'élément de question
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/QuestionFormateur.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                // Récupérer le contrôleur de l'élément de question
                QuestionFormateurController questionFormateur = fxmlLoader.getController();

                if (questionFormateur != null) {
                    // Définir les données de la question et le gestionnaire d'événements dans le contrôleur de l'élément de question
                    questionFormateur.set(question, myListener);

                    // Ajouter l'élément de question au GridPane et incrémenter les variables de position
                    grid.add(anchorPane, column++, row);

                    // Réinitialiser la colonne et passer à la ligne suivante si nécessaire
                    if (column == 1) {
                        column = 0;
                        row++;
                    }
                } else {
                    System.err.println("questionFormateur est null");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        msg.setVisible(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filtrer les questions en fonction du texte de recherche
            Set<Question> filteredQuestions = filterQuestions(newValue);
            // Mettre à jour l'affichage des questions
            afficherQuestions(filteredQuestions);
        });
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