package com.esprit.controllers;

import com.esprit.models.Categorie;
import com.esprit.services.CategorieService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjouterCategorieController {

        @FXML
        private TextField tfDescription;

        @FXML
        private TextField tfNom;


        @FXML
        void addCategorie(ActionEvent event) throws IOException {
                CategorieService2 cs = new CategorieService2();
                cs.ajouter(new Categorie(tfNom.getText(),tfDescription.getText()));
                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Categorie Ajouter");
                alert.setContentText("Categorie Ajouter !");
                alert.show();

                FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherCategorie.fxml"));
                Parent root= loader.load();
                tfNom.getScene().setRoot(root);

                AfficherCategorieController apc =loader.getController();
                apc.setLbNom(tfNom.getText());
                apc.setLbdescription(tfDescription.getText());

        }
}



