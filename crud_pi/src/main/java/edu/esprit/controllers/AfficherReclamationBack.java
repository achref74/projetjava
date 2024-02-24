package edu.esprit.controllers;

import edu.esprit.entites.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private GridPane reclamationContainer;
    private final ServiceReclamation ReclamationService = new ServiceReclamation();
    private Set<Reclamation> Liste ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Liste = new HashSet<>(reclamations());
        int column = 0;
        int row = 0;

        try {
            for (Reclamation reclamation: Liste) {
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

                reclamationBox.setOnMouseClicked(event -> handleReclamationClick(reclamationBox));



                HBox hbox = new HBox();
                hbox.getChildren().addAll(deleteButton);
                reclamationBox.getChildren().add(hbox);

                if(column == 3){
                    column = 0;
                    row++;
                }

                reclamationContainer.add(reclamationBox,column,row);
                column++;
                //grid weight
                reclamationContainer.setMinWidth(260);
                reclamationContainer.setPrefWidth(260);
                reclamationContainer.setMaxWidth(260);//
                //height
                reclamationContainer.setMinHeight(319);
                reclamationContainer.setPrefHeight(319);
                reclamationContainer.setMaxHeight(319);//
                reclamationContainer.setLayoutY(10);

                GridPane.setMargin(reclamationBox ,new Insets(10));
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void handleDelete(Reclamation reclamation, VBox reclamationBox) {
        ReclamationService.supprimer(reclamation.getId_reclamation());
        // Remove the reclamationBox from the parent container
        reclamationContainer.getChildren().remove(reclamationBox);
        // reclamationContainer.getChildren().clear();

    }


    private Set<Reclamation> reclamations(){
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
            // Handle the case where the Reclamation is null or not set
            // You might show an error message or take appropriate action
            return null;
        }
    }

}
