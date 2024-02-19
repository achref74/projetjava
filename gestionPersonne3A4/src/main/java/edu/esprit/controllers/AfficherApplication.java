package edu.esprit.controllers;

import edu.esprit.service.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AfficherApplication implements Initializable {
    @FXML
    private Label fxusers;
    private final ServiceUser sp = new ServiceUser();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxusers.setText(sp.getAll().toString());
    }
}


