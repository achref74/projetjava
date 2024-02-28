package com.esprit.controllers.Outils;

import com.esprit.models.Categorie;
import com.esprit.models.outil;
import com.esprit.services.CategorieService2;
import com.esprit.services.OutilsService2;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AjouterOutilsController {

    @FXML private TextField tfNom;
    @FXML private TextField tfDescription;
    @FXML private TextField tfPrix;
    @FXML private TextField tfRessources;
    @FXML private TextField tfStock;
    @FXML private TextField tfEtat;
    @FXML private ComboBox<Categorie> cbCategorie;
    @FXML private TextField tfImagePath;
    private CategorieService2 categorieService;
    private OutilsService2 outilsService;
    @FXML
    private StackPane mainContentPane;

    @FXML
    public void initialize() {
        categorieService = new CategorieService2();
        outilsService = new OutilsService2();

        cbCategorie.getItems().addAll(categorieService.getAll());
    }

    @FXML
    public void handleAjouterOutil() {
        String nom = tfNom.getText();
        String description = tfDescription.getText();
        String prixStr = tfPrix.getText();
        String ressources = tfRessources.getText();
        String stockStr = tfStock.getText();
        String etat = tfEtat.getText();
        Categorie categorie = cbCategorie.getValue();
        String imagePath = tfImagePath.getText();

        // Validate fields
        if (nom.isEmpty() || description.isEmpty() || prixStr.isEmpty() || ressources.isEmpty() || stockStr.isEmpty() || etat.isEmpty() || categorie == null || imagePath.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez remplir tous les champs.");
            return;
        }

        // Validate numeric fields
        double prix;
        try {
            prix = Double.parseDouble(prixStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Le champ Prix doit être un nombre valide.");
            return;
        }

        String stock;
        try {
            stock = stockStr; // Assuming stock is a String. If it's numeric, similar parsing as 'prix' can be applied.
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Le champ Stock doit être un nombre valide.");
            return;
        }

        // If all validations pass, proceed to add the outil
        outil newOutil = new outil(nom, description, prix, ressources, stock, etat, categorie, imagePath);
        outilsService.ajouter(newOutil);

        showAlert(Alert.AlertType.INFORMATION, "Outils Ajoutée", null, "Outil ajouté avec succès!");

        // After adding, load the AfficherOutils view
        loadAfficherOutilsView();
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadAfficherOutilsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Outils/AfficherOutils.fxml"));
            Parent afficherOutilsView = loader.load();

            AfficherOutilsController afficherOutilsController = loader.getController();
            afficherOutilsController.setMainContent(mainContentPane);

            mainContentPane.getChildren().setAll(afficherOutilsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {

                Path resourcesDir = Path.of("src/main/resources/images");
                if (!Files.exists(resourcesDir)) {
                    Files.createDirectories(resourcesDir);
                }


                Path imagePath = resourcesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);


                tfImagePath.setText(selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload image.");
                alert.showAndWait();
            }
        }
    }

}
