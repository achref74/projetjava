package edu.esprit.Controllers;

import edu.esprit.entities.Evaluation;
import edu.esprit.entities.Question;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.tests.MyListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Back3Controller implements Initializable {
    @FXML
    private GridPane grid;
    @FXML
    private TextField searchField;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ComboBox<String> tri;

    private Set<Question> liste = new HashSet<>();

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    private MyListener myListener;
    private String selectedId ;


    private Set<Question> getData() {
        Set<Question> liste = new HashSet<>();
        ServiceQuestion serviceQuestion =new ServiceQuestion();
        liste=serviceQuestion.getAll();
        return liste;
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        liste.addAll(getData());
        if (!liste.isEmpty()) {
            Iterator<Question> iterator = liste.iterator();
            Question firstCours = iterator.next();




        }
        int column = 0;
        int row = 1;
        try {


            int i = 0;
            for (Question question : liste) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Quest.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                QuesController questionController = fxmlLoader.getController();


                if  (questionController != null) {

                    questionController.setData(question, myListener);
                } else  System.err.println("Evaluation Controller  est null");
                i++;
                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Mettre à jour la liste des questions en fonction du texte de recherche
            Set<Question> filteredList = liste.stream()
                    .filter(question ->
                            question.getRessource().toLowerCase().contains(newValue.toLowerCase()) || // Recherche par ressource
                                    String.valueOf(question.getDuree()).contains(newValue) || // Recherche par durée
                                    String.valueOf(question.getPoint()).contains(newValue) || // Recherche par point
                                    question.getChoix1().toLowerCase().contains(newValue.toLowerCase()) || // Recherche par choix 1
                                    question.getChoix2().toLowerCase().contains(newValue.toLowerCase()) || // Recherche par choix 2
                                    question.getChoix3().toLowerCase().contains(newValue.toLowerCase()) || // Recherche par choix 3
                                    String.valueOf(question.getCrx()).contains(newValue) // Recherche par CRX
                    )
                    .collect(Collectors.toSet());
            afficherQuestions(filteredList);

            ObservableList<String> options = FXCollections.observableArrayList(
                    "Les questions  les plus dures", "Les questions les moins dures","aucun");
            tri.setItems(options);

            tri.setOnAction(this::handleTriSelection);
        });

    }
    @FXML
    private void handleTriSelection(ActionEvent event) {
        String selectedTri = tri.getValue();
        Set<Question> sortedE = new HashSet<>();
        if (selectedTri.equals("Les questions  les plus dures")) {

            sortedE = liste.stream()
                    .sorted(Comparator.comparingInt(Question::getPoint))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else if (selectedTri.equals("Les questions les moins dures")) {

            sortedE = liste.stream()
                    .sorted(Comparator.comparingInt(Question::getPoint).reversed())
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else if (selectedTri.equals("aucun")) { afficherQuestions(liste);
            return;

        }
        afficherQuestions(sortedE);
    }
    private void afficherQuestions(Set<Question> questionList) {
        grid.getChildren().clear(); // Effacer les cours précédents

        int column = 0;
        int row = 1;
        try {
            int i = 0;
            for (Question question : questionList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Quest.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                QuesController itemController  = fxmlLoader.getController();

                if (itemController != null) {
                    itemController.setData(question, myListener);
                } else {
                    System.err.println("question est nulle");
                }
                i++;
                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

////








}

