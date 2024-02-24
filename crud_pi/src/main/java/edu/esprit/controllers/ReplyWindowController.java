package edu.esprit.controllers;

import edu.esprit.entites.Reclamation;
import edu.esprit.entites.Reponse;
import edu.esprit.entites.User;
import edu.esprit.services.ServiceReponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class ReplyWindowController {
    @FXML
    private Label reclamationUserLabel;

    @FXML
    private Label reclamationOutilLabel;

    @FXML
    private Label reclamationFormationLabel;

    @FXML
    private Label reclamationDescriptionLabel;

    @FXML
    private Label reclamationDateLabel;

    @FXML
    private TextArea ReponseTextArea;

    @FXML
    private Button submitReponseButton;
    private Reclamation associatedReclamation;

    public void setAssociatedReclamation(Reclamation reclamation) {
        this.associatedReclamation = reclamation;
        // You can update the GUI to show the associated reclamation details if needed
    }

    @FXML
    public void submitResponse() {
        // Check if the associatedReclamation is set
        if (associatedReclamation != null) {
            // Implement logic to handle the submitted response
            String responseText = ReponseTextArea.getText();
            // Create a Reponse object with associated Reclamation
            User user1 = new User();
            user1.setId_user(2);
            Reponse reponse = new Reponse(user1, responseText, associatedReclamation);
            // Add the response to the database
            ServiceReponse serviceReponse = new ServiceReponse();
            serviceReponse.ajouter(reponse);

            System.out.println("Submitted response: " + responseText);

            // Close the reply window
            Stage stage = (Stage) submitReponseButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Error: No associated Reclamation.");
            // Handle the case where there is no associated Reclamation (show an error message, etc.)

        }}
            public void setReclamationDetails(Reclamation reclamation) {
        // Set reclamation details in labels
        reclamationUserLabel.setText("User: " + reclamation.getUser().getId_user());
        reclamationOutilLabel.setText("Outil: " + reclamation.getOutil().getId_outil());
        reclamationFormationLabel.setText("Formation: " + reclamation.getFormation().getId_formation());
        reclamationDescriptionLabel.setText("Description: " + reclamation.getDescription());
        reclamationDateLabel.setText("Date: " + reclamation.getDate_reclamation());
    }
}
