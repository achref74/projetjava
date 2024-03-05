package com.esprit.controllers.Outils;

import com.esprit.models.Categorie;
import com.esprit.models.outil;
import com.esprit.services.CategorieService2;
import com.esprit.services.OutilsService2;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Random;

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
    private TextField validateCaptcha;
    @FXML
    private TextField captchaCodeValue;
    private  String a ="";
    public static String createCaptchaValue(){
        Random random = new Random();
        int length=7+(Math.abs(random.nextInt()) % 3);
        StringBuffer captchaStrBuffer = new StringBuffer() ;
        for (int i=0; i<length;i++) {
            int baseCharacterNumber = Math.abs(random.nextInt()%62);
            int characterNumber = 0 ;
            if(baseCharacterNumber < 26) {
                characterNumber= 65 + baseCharacterNumber ;
            } else if (baseCharacterNumber < 52) {
                characterNumber = 97 + (baseCharacterNumber - 26);
            }else {
                characterNumber = 48 + (baseCharacterNumber-52);
            }
            captchaStrBuffer.append((char)characterNumber);
        }
        return captchaStrBuffer.toString();
}

    @FXML
    public void initialize() {
        categorieService = new CategorieService2();
        outilsService = new OutilsService2();

        cbCategorie.getItems().addAll(categorieService.getAll());
        a = createCaptchaValue();
        captchaCodeValue.setText(a);
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
        if (validateCaptcha.getText().equals(a)) {


            // Validate numeric fields
            double prix;
            try {
                prix = Double.parseDouble(prixStr);
                if (prix <= 0) {
                    throw new NumberFormatException("Le prix total doit être supérieur à 0.");
                }
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

            //showAlert(Alert.AlertType.INFORMATION, "Outils Ajoutée", null, "Outil ajouté avec succès!");
            Notifications.create()
                    .styleClass(
                            "-fx-background-color: #28a745; " + // Couleur de fond
                                    "-fx-text-fill: white; " + // Couleur du texte
                                    "-fx-background-radius: 5px; " + // Bord arrondi
                                    "-fx-border-color: #ffffff; " + // Couleur de la bordure
                                    "-fx-border-width: 2px;" // Largeur de la bordure
                    )
                    .title("Outils Ajouté avec succès")
                    .position(Pos.TOP_RIGHT) // Modifier la position ici
                    .hideAfter(Duration.seconds(20))
                    .show();
            loadAfficherOutilsView();

        }else {  Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("code captcha invalide  : " );
               alert.show();}

        // After adding, load the AfficherOutils view


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
