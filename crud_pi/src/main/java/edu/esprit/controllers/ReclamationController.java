package edu.esprit.controllers;
import edu.esprit.entites.Reclamation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.*;

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


    //private String [] colors = {"Red","BDB2FE","FB9AA8"};

    public void setData(Reclamation reclamation){
        ReclamationUser.setText(String.valueOf(reclamation.getUser().getId_user()));
        ReclamationOutil.setText(String.valueOf(reclamation.getOutil().getId_outil()));
        ReclamationFormation.setText(String.valueOf(reclamation.getFormation().getId_formation()));
        ReclamationDescription.setText(reclamation.getDescription());
        ReclamationDate.setText(String.valueOf(reclamation.getDate_reclamation()));

       // vboxx.setStyle("-fx-background-color: RED ");
    }


}
