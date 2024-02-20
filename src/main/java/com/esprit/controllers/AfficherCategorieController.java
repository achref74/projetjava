package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AfficherCategorieController {

    @FXML
    private Label lbNom;

    @FXML
    private Label lbdescription;

    public void setLbNom(String nom) {
        lbNom.setText(nom);
    }

    public void setLbdescription(String description) {
        lbdescription.setText(description);
    }
}

