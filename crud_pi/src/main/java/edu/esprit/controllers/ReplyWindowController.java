package edu.esprit.controllers;

import edu.esprit.entites.Reclamation;
import edu.esprit.entites.Reponse;
import edu.esprit.entites.User;
import edu.esprit.services.ServiceReponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

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

    @FXML
    private ListView<String> responseListView;
    ServiceReponse serviceReponse = new ServiceReponse();

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

            serviceReponse.ajouter(reponse);


            System.out.println("Submitted response: " + responseText);

            // Close the reply window
            Stage stage = (Stage) submitReponseButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Error: No associated Reclamation.");
            // Handle the case where there is no associated Reclamation (show an error message, etc.)
        }
    }

    public void setReclamationDetails(Reclamation reclamation) {

        // Set reclamation details in labels
        reclamationUserLabel.setText("User: " + reclamation.getUser().getNom());
        reclamationOutilLabel.setText("Outil: " + reclamation.getOutil().getNom());
        reclamationFormationLabel.setText("Formation: " + reclamation.getFormation().getNom());
        reclamationDescriptionLabel.setText("Description: " + reclamation.getDescription());
        reclamationDateLabel.setText("Date: " + reclamation.getDate_reclamation());

        // Fetch and display older responses
        Set<Reponse> olderResponses = serviceReponse.getReponseByReclamationId(reclamation.getId_reclamation());

        // Clear previous responses in the ListView
       // responseListView.getItems().clear();

        for (Reponse olderResponse : olderResponses) {
            // Display older responses in a ListView
            String responseText = olderResponse.getDescription();
            String usernom = String.valueOf(olderResponse.getUser().getNom());
            String responseDate = olderResponse.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            //Button b = new Button();
            //b.setText("delete");
           // b.setOnAction(Event -> {serviceReponse.supprimer(olderResponse.getId_reponse());});
            responseListView.getItems().add(usernom + " - " + responseDate + " : " + responseText  );

        }
    }


}
