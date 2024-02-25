package edu.esprit.controllers;

import edu.esprit.entites.Formation;
import edu.esprit.entites.Outil;
import edu.esprit.entites.Reclamation;
import edu.esprit.entites.User;
import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOutil;
import edu.esprit.services.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterReclamation {

    @FXML
    private ComboBox<Formation> formationComboBox;

    @FXML
    private ComboBox<Outil> outilComboBox;

    @FXML
    private TextArea descriptionTextArea;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();
    private ServiceOutil serviceOutil = new ServiceOutil();
    private ServiceFormation serviceFormation = new ServiceFormation();
    @FXML
    private Button afficherReclamationButton;
    private AfficherReclamationBack afficherReclamationBackController;

    public void setAfficherReclamationBackController(AfficherReclamationBack afficherReclamationBackController) {
        this.afficherReclamationBackController = afficherReclamationBackController;
    }

    @FXML
    public void initialize() {
        // Populate ComboBoxes with data from the database
        populateFormationComboBox();
        populateOutilComboBox();
    }

    private void populateFormationComboBox() {
        try {
            ObservableList<Formation> formations = FXCollections.observableArrayList(serviceFormation.getAll());
            formationComboBox.setItems(formations);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    private void populateOutilComboBox() {
        try {
            ObservableList<Outil> outils = FXCollections.observableArrayList(serviceOutil.getAll());
            outilComboBox.setItems(outils);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    public void addReclamation() {
        // Retrieve selected values from ComboBoxes and TextArea
        Formation selectedFormation = formationComboBox.getValue();
        Outil selectedOutil = outilComboBox.getValue();
        String description = descriptionTextArea.getText();
        User user1= new User();
        user1.setId_user(2);

        // Check if all required fields are selected/entered
        if (selectedFormation == null || selectedOutil == null || description.isEmpty()) {
            // Display an alert or handle the situation accordingly
            System.out.println("Please fill in all fields.");
            return;
        }

        // Create a new Reclamation object
        Reclamation reclamation = new Reclamation(user1, selectedOutil, selectedFormation, description);

        try {
            // Add the reclamation to the database
            serviceReclamation.ajouter(reclamation);
            System.out.println("Reclamation added successfully!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Reclamation");
            alert.setContentText("Reclamation Bien Ajouté");
            alert.show();

            // Notify the AfficherReclamationBack controller to update the UI
            if (afficherReclamationBackController != null) {
                afficherReclamationBackController.updateReclamationList();
            }

            // Close the AjouterReclamation stage
            Stage stage = (Stage) formationComboBox.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Reclamation");
            alert.setContentText("Reclamation N'est pas Ajoutée");
            alert.show();
            e.printStackTrace(); // Handle database exception appropriately
            System.out.println("Error adding reclamation.");
        }
    }
    @FXML
    public void openAfficherReclamation() {
        try {
            // Load the FXML file for AfficherReclamationBack
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationBack.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the current button
            Stage stage = (Stage) afficherReclamationButton.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.setTitle("Afficher Reclamation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}