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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifierReclamation {
    @FXML
    private ComboBox<Formation> formationComboBox;

    @FXML
    private ComboBox<Outil> outilComboBox;

    @FXML
    private TextArea descriptionTextArea;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();
    private ServiceOutil serviceOutil = new ServiceOutil();
    private ServiceFormation serviceFormation = new ServiceFormation();
    private AfficherReclamationBack afficherReclamationBackController;
    private User user = new User();
    private Reclamation selectedReclamation;


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

    public void updateReclamation(ActionEvent actionEvent) {

        // Retrieve selected values from ComboBoxes and TextArea
        Formation selectedFormation = formationComboBox.getValue();
        Outil selectedOutil = outilComboBox.getValue();
        String description = descriptionTextArea.getText();

        // Check if all required fields are selected/entered
        if (selectedFormation == null || selectedOutil == null || description.isEmpty()) {
            // Display an alert or handle the situation accordingly
            System.out.println("Invalid characters in the description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" desription");
            alert.setContentText("the description must not be null");
            alert.show();
            System.out.println("Please fill in all fields.");
            return;
        }
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
        //user.setId_user(2);
        // Create a new Reclamation object
        Reclamation reclamation = new Reclamation(user, selectedOutil, selectedFormation, description);

        // Set the ID of the selected Reclamation
        reclamation.setId_reclamation(selectedReclamation.getId_reclamation());
        user.setId_user(selectedReclamation.getUser().getId_user());


        try {
            // Update the reclamation in the database
            serviceReclamation.modifier(reclamation);
            System.out.println("Reclamation modified successfully!");

            // Notify the AfficherReclamationBack controller to update the UI
            if (afficherReclamationBackController != null) {
                afficherReclamationBackController.updateReclamationList();
            }

            // Close the ModifierReclamation stage
            Stage stage = (Stage) formationComboBox.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Reclamation");
            alert.setContentText("Reclamation N'est pas modifiée");
            alert.show();
            e.printStackTrace(); // Handle database exception appropriately
            System.out.println("Error modifying reclamation.");
        }
    }
    public void setReclamationData(Reclamation reclamation, AfficherReclamationBack afficherReclamationBackController) {
        selectedReclamation = reclamation;
        this.afficherReclamationBackController = afficherReclamationBackController;

        formationComboBox.setValue(selectedReclamation.getFormation());
        outilComboBox.setValue(selectedReclamation.getOutil());
        descriptionTextArea.setText(selectedReclamation.getDescription());
    }
}
