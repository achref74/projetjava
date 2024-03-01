package com.esprit.controllers.Categorie;

import com.esprit.models.Categorie;
import com.esprit.services.CategorieService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class AjouterCategorieController {

        @FXML
        private TextField tfDescription;
        @FXML
        private TextField tfNom;
        @FXML
        private StackPane mainContentPane;

        @FXML
        void addCategorie(ActionEvent event) {
                CategorieService2 cs = new CategorieService2();

                String nom = tfNom.getText().trim();
                String description = tfDescription.getText().trim();

                // Check if the fields are not empty
                if (nom.isEmpty() || description.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
                        return;
                }

                // Example of additional validation: check length constraints
                if (nom.length() < 5 || nom.length() > 200) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom doit contenir entre 3 et 50 caractères.");
                        return;
                }

                if (description.length() < 5 || description.length() > 200) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "La description doit contenir entre 10 et 200 caractères.");
                        return;
                }


                cs.ajouter(new Categorie(nom, description));
                //showAlert(Alert.AlertType.INFORMATION, "Categorie Ajoutée", "La catégorie a été ajoutée avec succès !");


                Notifications.create()
                        .styleClass(
                                "-fx-background-color: #28a745; " + // Couleur de fond
                                        "-fx-text-fill: white; " + // Couleur du texte
                                        "-fx-background-radius: 5px; " + // Bord arrondi
                                        "-fx-border-color: #ffffff; " + // Couleur de la bordure
                                        "-fx-border-width: 2px;" // Largeur de la bordure
                        )
                        .title("CATEGORIE Ajouté avec succès")
                        .position(Pos.TOP_RIGHT) // Modifier la position ici
                        .hideAfter(Duration.seconds(20))
                        .show();


                loadAfficherCategorieView();
        }

        private void showAlert(Alert.AlertType type, String title, String content) {
                Alert alert = new Alert(type);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(content);
                alert.showAndWait();
        }

        private void loadAfficherCategorieView() {
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Categorie/AfficherCategorie.fxml"));
                        Parent afficherCategorieView = loader.load();

                        AfficherCategorieController afficherCategorieController = loader.getController();
                        afficherCategorieController.setMainContent(mainContentPane);

                        mainContentPane.getChildren().setAll(afficherCategorieView);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}




