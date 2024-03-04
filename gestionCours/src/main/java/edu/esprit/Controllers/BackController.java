package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.services.ServiceCours;
import edu.esprit.tests.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class BackController implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    @FXML
    private TextField searchField;

    private Set<Cours> liste = new HashSet<>();

    @FXML
    private Button modifier;
    @FXML
    private Button evaluationf;
    @FXML
    private Button supprimer;
    @FXML
    private Button evaluation;
    private MyListener myListener;
    private String selectedId ;


    private Set<Cours> getData() {
        Set<Cours> liste = new HashSet<>();
        ServiceCours serviceCours =new ServiceCours();
        liste=serviceCours.getAll();
        return liste;
    }


    private  Cours cours = new Cours();
    private String selectedImageURL;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        liste.addAll(getData());
        if (!liste.isEmpty()) {
            Iterator<Cours> iterator = liste.iterator();
            Cours firstCours = iterator.next();




        }
        int column = 0;
        int row = 1;
        try {


            int i = 0;
            for (Cours cours : liste) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Cours.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CoursController coursController = fxmlLoader.getController();


                if  (coursController != null) {

                    coursController.setData(cours, myListener);
                } else  System.err.println("Cours Controller  est null");
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
            // Mettre à jour la liste des cours en fonction du texte de recherche
            Set<Cours> filteredList = liste.stream()
                    .filter(cours -> cours.getNom().toLowerCase().contains(newValue.toLowerCase()) || // Recherche par nom
                            cours.getDescrption().toLowerCase().contains(newValue.toLowerCase()) || // Recherche par description
                            cours.getDate().toString().toLowerCase().contains(newValue.toLowerCase()) || // Recherche par date
                            String.valueOf(cours.getDuree()).contains(newValue) || // Recherche par durée
                            cours.getPrerequis().toLowerCase().contains(newValue.toLowerCase()) || // Recherche par prérequis
                            cours.getRessource().toLowerCase().contains(newValue.toLowerCase())) // Recherche par ressource)
                    .collect(Collectors.toSet());
            afficherCours(filteredList);
        });


    }
    private void afficherCours(Set<Cours> coursList) {
        grid.getChildren().clear(); // Effacer les cours précédents

        int column = 0;
        int row = 1;
        try {
            int i = 0;
            for (Cours cours : coursList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Cours.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CoursController itemController = fxmlLoader.getController();

                if (itemController != null) {
                    itemController.setData(cours, myListener);
                } else {
                    System.err.println("cours est null");
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
    @FXML
    private Button back;
    public void back(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Back2.fxml"));
            back.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }


////








}

