package com.esprit.controllers;

import com.esprit.models.Categorie;
import com.esprit.services.CategorieService2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class AffichageCategorieController {

    @FXML
    private VBox Vbox;

    @FXML
    private Button btnFormation;

    @FXML
    private Button btnOutils;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnUser;

    @FXML
    private Button deleteF;

    @FXML
    private TextField descrip;

    @FXML
    private TextField id;

    @FXML
    private Button navigate;

    @FXML
    private TextField nom;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Button updateF;
    CategorieService2 sc =new CategorieService2();
    private String selecteNom;
    private String selecteId;
    private String selecteDescrip;




    @FXML
    public  void navigatetoAfficherCategorieAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffichageCategorie.fxml"));
            btnFormation.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AffichageCategorie.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) deleteF.getScene().getWindow();

                    // Set the new scene to the stage
                    stage.setScene(scene);


                } catch (IOException var4) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sorry");
                    alert.setTitle("Error");
                    alert.show();
                }

            }
        });


        System.out.println("zzzzzzzzz");
        List<Categorie> CategorieList = null;
        CategorieList = sc.getAll();
        System.out.println("Reclamation List: " + CategorieList); // Print the list

        for (Categorie categorie : CategorieList) {
            System.out.println("Adding reclamation to TitledPane: " + CategorieList);
            // Create layout for each reclamation
            Label idLabel = new Label("id: " + categorie.getIdcategorie());
            Label nomLabel = new Label("nom: " + categorie.getNom());
            Label descripdLabel = new Label("Description: " + categorie.getDescription());
            GridPane gridPane = new GridPane();
            gridPane.add(idLabel, 0, 0);
            gridPane.add(nomLabel, 0, 1);
            gridPane.add(descripdLabel, 0, 2);


            TitledPane titledPane = new TitledPane("Reclamation " + categorie.getIdcategorie(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selecteId = String.valueOf(categorie.getIdcategorie());
                    selecteNom = categorie.getNom();
                    selecteDescrip = categorie.getDescription();


                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selecteId);
                    System.out.println("Selected Nom : " + selecteNom);
                    System.out.println("Selected Description: " + selecteDescrip);


                }
            });


            Vbox.getChildren().add(titledPane);
        }


    }


}