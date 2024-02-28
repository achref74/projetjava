package edu.esprit.controllers;

import edu.esprit.entites.Reclamation;
import edu.esprit.entites.Reponse;
import edu.esprit.entites.User;
import edu.esprit.services.ServiceReponse;
import edu.esprit.utils.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class ReplyWindowController {
    Connection cnx = DataSource.getInstance().getCnx();

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
    private ListView<Reponse> responseListView;
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
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
          //  LocalDateTime formattedDate = LocalDateTime.parse(olderResponse.getDate().format(formatter));
            //Button b = new Button();
            //b.setText("delete");
            // b.setOnAction(Event -> {serviceReponse.supprimer(olderResponse.getId_reponse());});
            User user1 = new User();
            user1.setNom(usernom);
            user1.setId_user(olderResponse.getUser().getId_user());
            Reponse reponse = new Reponse(user1, responseText, reclamation);
            responseListView.getItems().add(reponse);
            String query = "SELECT `id_reponse`FROM `reponse` WHERE description=? AND id_user=?;";
            try (PreparedStatement ps = cnx.prepareStatement(query)) {
                ps.setInt(2, olderResponse.getUser().getId_user());
                ps.setString(1,responseText);
                // Set the parameter value
                ResultSet res = ps.executeQuery();
                if (res.next()) {
                    reponse.setId_reponse(res.getInt("id_reponse"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void remove(MouseEvent mouseEvent) {
        Reponse reponse = new Reponse();
        reponse.setId_reponse(responseListView.getItems().indexOf(reponse));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this response?");

        // Show the confirmation dialog and wait for user response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // If user clicks OK, proceed with deletion
                serviceReponse.supprimer(handleResponseItemClick(mouseEvent));
            }
        });
    }
    @FXML
    public int handleResponseItemClick(MouseEvent event) {
        int selectedIndex = responseListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex != -1) {
            Reponse selectedResponse = responseListView.getItems().get(selectedIndex);
            int responseId = selectedResponse.getId_reponse();
            String responseDescription = selectedResponse.getDescription();// Assuming you have getId_reponse() in your Reponse class
            // Now you have the responseId, you can use it as needed (e.g., for deletion)
            System.out.println("Selected Response ID: " + responseId);
            System.out.println(responseDescription);
            ReponseTextArea.setText(responseDescription);

            return responseId;
        }

        return selectedIndex;
    }


    public void update(MouseEvent mouseEvent) {
        Reponse reponse = new Reponse();
        reponse.setId_reponse(handleResponseItemClick(mouseEvent));
        String text = ReponseTextArea.getText();
        //reponse.setDescription(ReponseTextArea.getText());
        Reclamation reclamation = new Reclamation();
        User user1 =new User();

        String query = "SELECT `id_user`,`id_reclamation`FROM `reponse` WHERE description=? AND id_reponse=?;";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1,text);
            ps.setInt(2,handleResponseItemClick(mouseEvent));
            // Set the parameter value
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                if (associatedReclamation != null) {
                   // reclamation.setId_reclamation(res.getInt("id_reclamation"));
                    reponse.setReclamation(associatedReclamation);
                    //reponse.setId_reponse(res.getInt("id_reponse"));
                    user1.setId_user(res.getInt("id_user"));
                    reponse.setUser(user1);

                }
            }
            String a = ReponseTextArea.getText();
            reponse.setDescription(a);
            serviceReponse.modifier(reponse);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
