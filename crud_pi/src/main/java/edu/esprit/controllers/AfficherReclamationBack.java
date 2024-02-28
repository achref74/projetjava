package edu.esprit.controllers;

import edu.esprit.entites.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherReclamationBack implements Initializable {

    @FXML
    private Button ajouterReclamationButton;
    @FXML
    private GridPane reclamationContainer;

    private final ServiceReclamation ReclamationService = new ServiceReclamation();
    private Set<Reclamation> Liste;
    @FXML
    private ScrollPane ScrolR;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateReclamationList();
    }
    @FXML
    public void Refresh(){
        updateReclamationList();
    }

    @FXML
    public void updateReclamationList() {
        // Clear the existing UI elements
        reclamationContainer.getChildren().clear();

        Liste = new HashSet<>(reclamations());
        int column = 0;
        int row = 1;

        try {
            for (Reclamation reclamation : Liste) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Reclamation.fxml"));

                VBox reclamationBox = fxmlLoader.load();

                ReclamationController reclamationController = fxmlLoader.getController();
                reclamationController.setData(reclamation);


                // Set Reclamation as user data for the VBox
                reclamationBox.setUserData(reclamation);

                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(event -> {
                    handleDelete(reclamation, reclamationBox);
                });

                Button updateButton = new Button("update");
                updateButton.setOnAction(event -> {
                    handleupdate(reclamation);
                });

                reclamationBox.setOnMouseClicked(event -> handleReclamationClick(reclamationBox));

                HBox hbox = new HBox();
                hbox.getChildren().addAll(deleteButton);
                hbox.getChildren().addAll(updateButton);
                reclamationBox.getChildren().add(hbox);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                //ScrolR.setContent(reclamationContainer);
                reclamationContainer.add(reclamationBox, column++, row);
               //ScrolR.setContent(reclamationBox);
                //column++;
                GridPane.setMargin(reclamationBox, new Insets(10));


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleupdate(Reclamation reclamation) {
        //Reclamation selectedReclamation = getReclamationFromEventSource(reclamationBox);
        try {
            // Load the FXML file for ModifierReclamation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamation.fxml"));
            Parent root = loader.load();

            // Get the ModifierReclamation controller
            ModifierReclamation modifierReclamationController = loader.getController();

            // Pass the whole clicked Reclamation object to the ModifierReclamation controller
            modifierReclamationController.setReclamationData(reclamation, this);

            // Create a new scene
            Scene scene = new Scene(root);

            // Create a new stage for ModifierReclamation
            Stage modifierReclamationStage = new Stage();
            modifierReclamationStage.setScene(scene);
            modifierReclamationStage.setTitle("Modifier Reclamation");

            // Show the new stage
            modifierReclamationStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void handleDelete(Reclamation reclamation, VBox reclamationBox) {
       /* ReclamationService.supprimer(reclamation.getId_reclamation());

        reclamationContainer.getChildren().remove(reclamationBox);*/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this reclamation?");

        // Show the confirmation dialog and wait for user response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // If user clicks OK, proceed with deletion
                ReclamationService.supprimer(reclamation.getId_reclamation());
                reclamationContainer.getChildren().remove(reclamationBox);
            }
        });
    }

    private Set<Reclamation> reclamations() {
        Set<Reclamation> listR = new HashSet<>();
        try {
            listR.addAll(ReclamationService.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listR;
    }

    @FXML
    private void handleReclamationClick(VBox clickedBox) {
        // Get the Reclamation associated with the clicked UI element
        Reclamation selectedReclamation = getReclamationFromEventSource(clickedBox);

        // Open the reply window when a reclamation is clicked
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ReplyWindow.fxml"));
            VBox replyWindow = fxmlLoader.load();
            ReplyWindowController replyWindowController = fxmlLoader.getController();

            // Pass the selected reclamation to set its details
            replyWindowController.setReclamationDetails(selectedReclamation);
            replyWindowController.setAssociatedReclamation(selectedReclamation);

            // Show the reply window
            Stage stage = new Stage();
            stage.setTitle("Reclamation Details and Responses");
            stage.setScene(new Scene(replyWindow));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Reclamation getReclamationFromEventSource(VBox clickedBox) {
        // Ensure that the associated Reclamation is not null
        if (clickedBox != null && clickedBox.getUserData() instanceof Reclamation) {
            return (Reclamation) clickedBox.getUserData();
        } else {

            return null;
        }
    }

    @FXML
    public void openAjouterReclamation() {
        try {
            // Load the FXML file for AjouterReclamation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Create a new stage for AjouterReclamation
            Stage ajouterReclamationStage = new Stage();
            ajouterReclamationStage.setScene(scene);
            ajouterReclamationStage.setTitle("Ajouter Reclamation");

            // Show the new stage
            ajouterReclamationStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}