package edu.esprit.controllers;
import edu.esprit.entites.Reclamation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


import java.awt.*;
import java.io.IOException;

import static java.awt.Color.RED;

public class ReclamationController {
    @FXML
    private Label ReclamationDate;

    @FXML
    private Label ReclamationDescription;

    @FXML
    private Label ReclamationFormation;

    @FXML
    private Label ReclamationOutil;

    @FXML
    private Label ReclamationUser;
    private Reclamation selectedReclamation;



    //private String [] colors = {"Red","BDB2FE","FB9AA8"};

    /*public void setData(Reclamation reclamation){
        ReclamationUser.setText(String.valueOf(reclamation.getUser().getId_user()));
        ReclamationOutil.setText(String.valueOf(reclamation.getOutil().getId_outil()));
        ReclamationFormation.setText(String.valueOf(reclamation.getFormation().getId_formation()));
        ReclamationDescription.setText(reclamation.getDescription());
        ReclamationDate.setText(String.valueOf(reclamation.getDate_reclamation()));

       // vboxx.setStyle("-fx-background-color: RED ");
    }*/

    public void setData(Reclamation reclamation) {
        if (reclamation != null) {
            if (reclamation.getUser() != null) {
                ReclamationUser.setText(reclamation.getUser().getNom());
            } else {
                ReclamationUser.setText("User not specified");
            }

            if (reclamation.getOutil() != null) {
                ReclamationOutil.setText((reclamation.getOutil()).getNom());
            } else {
                ReclamationOutil.setText("Outil not specified");
            }

            if (reclamation.getFormation() != null) {
                ReclamationFormation.setText(reclamation.getFormation().getNom());
            } else {
                ReclamationFormation.setText("Formation not specified");
            }

            ReclamationDescription.setText(reclamation.getDescription());
            ReclamationDate.setText(String.valueOf(reclamation.getDate_reclamation()));
        } else {
            // Handle the case where the Reclamation object is null
            System.out.println("Received null Reclamation object");
        }
    }

    /*
    public void handleReclamationClick(MouseEvent event){
        // Open the reply window when a reclamation is clicked
        selectedReclamation = getReclamationFromEventSource(event);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ReplyWindow.fxml"));
            VBox replyWindow = fxmlLoader.load();
            ReplyWindowController replyWindowController = fxmlLoader.getController();

            // Pass the selected reclamation to set its details
            replyWindowController.setReclamationDetails(selectedReclamation);

            // Show the reply window
            Stage stage = new Stage();
            stage.setTitle("Reclamation Details and Responses");
            stage.setScene(new Scene(replyWindow));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/






}
