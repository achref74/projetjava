package edu.esprit.controllers;
import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOffre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterOffre {

    @FXML
    private Button ajouterOffre;

    @FXML
    private Button btnCours;

    @FXML
    private Button btnFormation;

    @FXML
    private Button btnForum;

    @FXML
    private Button btnOutils;

    @FXML
    private Button btnReclamation;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnUser;

    @FXML
    private DatePicker dateDO;

    @FXML
    private DatePicker dateFO;

    @FXML
    private TextArea descripO;

    @FXML
    private Pane pnlOverview;
@FXML
private TextField nomF1;
    @FXML
    private TextField prixF;

    @FXML
    private TextField prixO;
    private final ServiceOffre so = new ServiceOffre();

    @FXML
    void AjouterOffreAction(ActionEvent event) {
       java.sql.Date dateDebut = java.sql.Date.valueOf(dateDO.getValue());
        java.sql.Date dateFin = java.sql.Date.valueOf(dateFO.getValue());


        try {
            so.ajouter(new Offre(Double.parseDouble(prixF.getText()),
                    descripO.getText(),
                    dateDebut,
                    dateFin),
                    Integer.parseInt(nomF1.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Offre ajoutée avec succès !");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void navigatetoAfficheFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFormation.fxml"));
            btnFormation.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

            void selectFormationAction(ActionEvent event) {
              /*  Formation formationSelectionnee = listeFormations.getValue();
                if (formationSelectionnee != null) {
                    int idFormation = formationSelectionnee.getIdFormation();
                    // Utilisez idFormation comme nécessaire
                    System.out.println("Formation sélectionnée avec l'ID: " + idFormation);
                }*/
            }



    }



