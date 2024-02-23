package edu.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;

public class Home {

    @FXML
    private Button btnCours_H;

    @FXML
    private Button btnFormation_H;

    @FXML
    private Button btnForum_H;

    @FXML
    private Button btnOutils_H;

    @FXML
    private Button btnProfil_H;

    @FXML
    private Button btnReclamation_H;

    @FXML
    private Button btnSignOut_H;
    @FXML
    void navigatetoAfficheFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFormation.fxml"));
            btnFormation_H.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
}
