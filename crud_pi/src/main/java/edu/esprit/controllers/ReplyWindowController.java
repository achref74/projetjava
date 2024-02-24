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

   /* @FXML
    private void initialize() {
        // Set the custom cell factory for the ListView
        responseListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Reponse> call(ListView<Reponse> param) {
                return new DeleteButtonCell();
            }
        });

        // Fetch and display initial responses
        Set<Reponse> initialResponses = serviceReponse.getReponseByReclamationId(associatedReclamation.getId_reclamation());
        refreshListView(initialResponses);
    }

    private class DeleteButtonCell extends ListCell<Reponse> {
        private final Button deleteButton;

        public DeleteButtonCell() {
            deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> handleDeleteButton(getItem()));
        }

        @Override
        protected void updateItem(Reponse item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText("User ID: " + item.getUser().getId_user() + " - Date: " + item.getDate());
                setGraphic(deleteButton);
            }
        }
    }*/

    /*private void handleDeleteButton(Reponse response) {
        if (response != null) {
            // Implement logic to delete the response using ServiceReponse delete method
            serviceReponse.supprimer(response.getId_reponse());

            // Show a message to indicate successful deletion
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Response Deleted");
            alert.setHeaderText(null);
            alert.setContentText("Response deleted successfully.");
            alert.showAndWait();

            // Fetch and display updated responses
            Set<Reponse> updatedResponses = serviceReponse.getReponseByReclamationId(associatedReclamation.getId_reclamation());
            refreshListView(updatedResponses);
        }
    }*/

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
            //refreshListView();

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
        reclamationUserLabel.setText("User: " + reclamation.getUser().getId_user());
        reclamationOutilLabel.setText("Outil: " + reclamation.getOutil().getId_outil());
        reclamationFormationLabel.setText("Formation: " + reclamation.getFormation().getId_formation());
        reclamationDescriptionLabel.setText("Description: " + reclamation.getDescription());
        reclamationDateLabel.setText("Date: " + reclamation.getDate_reclamation());

        // Fetch and display older responses
        Set<Reponse> olderResponses = serviceReponse.getReponseByReclamationId(reclamation.getId_reclamation());

        // Clear previous responses in the ListView
        responseListView.getItems().clear();

        for (Reponse olderResponse : olderResponses) {
            // Display older responses in a ListView
            String responseText = olderResponse.getDescription();
            String userId = String.valueOf(olderResponse.getUser().getId_user());
            String responseDate = olderResponse.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            responseListView.getItems().add(userId + " - " + responseDate + " : " + responseText);
        }
    }

    private void refreshListView(Set<Reponse> responses) {
        // Clear and reload responses in the ListView
        responseListView.getItems().clear();
        for (Reponse response : responses) {
            // Display responses in the ListView
            String responseText = response.getDescription();
            String userId = String.valueOf(response.getUser().getId_user());
            String responseDate = response.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            responseListView.getItems().add(userId + " - " + responseDate + " : " + responseText);
        }
    }
}
