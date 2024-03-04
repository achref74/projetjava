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
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        User user1 = new User();
        user1.setId_user(2);

        // Check if either formation or outil is selected
        if ((selectedFormation == null && selectedOutil == null) || description.isEmpty()) {
            // Display an alert or handle the situation accordingly
            System.out.println("Please select either formation or outil and fill in description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Description");
            alert.setContentText("Please select either formation or outil and fill in description.");
            alert.show();
            return;
        }

        // Validate the description using regex
        String descriptionPattern = "^[a-zA-Z0-9\\s]+$";
        Pattern pattern = Pattern.compile(descriptionPattern);
        Matcher matcher = pattern.matcher(description);

        if (!matcher.matches()) {
            // Display an alert or handle the situation accordingly
            System.out.println("Invalid characters in the description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Description");
            alert.setContentText("The description only contains numbers and letters.");
            alert.show();
            return;
        }

        // Create a new Reclamation object only if either formation or outil is selected
        Reclamation reclamation;
        if (selectedFormation.getId_formation()!=-1) {
            reclamation = new Reclamation(user1, new Outil(-1), selectedFormation, description);
        } else {
            reclamation = new Reclamation(user1, selectedOutil, new Formation(-1), description);
        }

        try {
            // Add the reclamation to the database
            serviceReclamation.ajouter(reclamation);
            System.out.println("Reclamation added successfully!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reclamation");
            alert.setContentText("Reclamation added successfully.");
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
            alert.setTitle("Reclamation");
            alert.setContentText("Failed to add reclamation.");
            alert.show();
            e.printStackTrace(); // Handle database exception appropriately
            System.out.println("Error adding reclamation.");
        }
    }
    /*public void addReclamation() {
        // Retrieve selected values from ComboBoxes and TextArea
        Formation selectedFormation = formationComboBox.getValue();
        Outil selectedOutil = outilComboBox.getValue();
        String description = descriptionTextArea.getText();
        User user1= new User();
        user1.setId_user(2);

        // Check if all required fields are selected/entered
       // if (selectedFormation == null || selectedOutil == null || description.isEmpty()) {

            // Display an alert or handle the situation accordingly
        if ((selectedFormation == null && selectedOutil == null) || description.isEmpty()) {

            System.out.println("Please fill in all fields.");
            System.out.println("Invalid characters in the description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" desription");
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return;
        }
        // Validate the description using regex
        String descriptionPattern = "^[a-zA-Z0-9\\s]+$";
        Pattern pattern = Pattern.compile(descriptionPattern);
        Matcher matcher = pattern.matcher(description);

        if (!matcher.matches()) {
            // Display an alert or handle the situation accordingly
            System.out.println("Invalid characters in the description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" desription");
            alert.setContentText("the description only contains numbers and letters");
            alert.show();
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
    }*/
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

    public void setReclamationData(Reclamation reclamation) {
        formationComboBox.setValue(reclamation.getFormation());
        outilComboBox.setValue(reclamation.getOutil());
        descriptionTextArea.setText(reclamation.getDescription());
    }
}

