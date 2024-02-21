package edu.esprit.controllers;

import edu.esprit.entities.Certificat;
import edu.esprit.entities.Offre;
import edu.esprit.services.ServiceCertificat;
import edu.esprit.services.ServiceOffre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;


import javafx.event.ActionEvent;

import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterCertificat {

        @FXML
        private Button ajouterCertificat;

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
        private DatePicker dateDC;

        @FXML
        private TextArea descripC;

        @FXML
        private TextField nomF_C;

        @FXML
        private TextField nbrCoursC;

        @FXML
        private Pane pnlOverview;

        @FXML
        private TextField titreC;
    private final ServiceCertificat so = new ServiceCertificat();

        @FXML
        void AjouterCertificatAction(ActionEvent event) {
            java.sql.Date dateObt = java.sql.Date.valueOf(dateDC.getValue());


            try {
                so.ajouter(new Certificat(titreC.getText(),
                                descripC.getText(),
                                dateObt,
                               Integer.parseInt( nbrCoursC.getText())

                      ));
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

    }


