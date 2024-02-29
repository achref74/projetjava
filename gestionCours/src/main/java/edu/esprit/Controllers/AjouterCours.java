package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.services.ServiceCours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AjouterCours implements Initializable {
    @FXML
    private Text Duree;

    @FXML
    private Label msg;


    @FXML
    private Button btnCours;

    @FXML
    private Button btnFormation;


    @FXML
    private ImageView image;



    @FXML
    private DatePicker date;

    @FXML
    private TextArea description;


    @FXML
    private TextField duree;


    @FXML
    private TextField nom;

    @FXML
    private Pane pnlOverview;

    @FXML
    private TextField prerequis;

    @FXML
    private TextField ressource;
    private final ServiceCours sp = new ServiceCours();

    private String selectedImagePath;

    @FXML
    void selectImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        File initialDirectory = new File("C:/Users/LENOVO/Desktop/gestionCours/src/main/resources/images");
        fileChooser.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Stockez uniquement le nom du fichier
            selectedImagePath = selectedFile.getName();
            Image newImage = new Image(selectedFile.toURI().toString());
            image.setImage(newImage);
            System.out.println("Nom de l'image sélectionnée : " + selectedImagePath);
        }
    }
    @FXML
    void AjouterCoursAction(ActionEvent event) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        Alert alert;


        if (nom.getText().isEmpty() || description.getText().isEmpty() || prerequis.getText().isEmpty() || ressource.getText().isEmpty() || duree.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        try {

            if (selectedImagePath == null || selectedImagePath.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez sélectionner une image.");
                alert.showAndWait();
                return;
            }


            if(!sp.isvalideduree(Integer.parseInt(duree.getText())))
            {  msg.setVisible(true);


            }else {
                sp.ajouter(new Cours(
                        nom.getText(),
                        description.getText(),
                        prerequis.getText(),
                        ressource.getText(),
                        sqlDate,
                        Integer.parseInt(duree.getText()),
                        selectedImagePath // Utilisez le nom de l'image
                ));

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Cours ajouté avec succès !");
            alert.show();}
        } catch (NumberFormatException e) {
            // Gérer l'exception si la conversion de la durée en entier échoue
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La durée doit être un nombre entier.");
            alert.show();
        } catch (Exception e) {
            // Gérer toute autre exception
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors de l'ajout du cours.");
            alert.show();
        }
    }
    @FXML
    void choisirRessource(MouseEvent event) {
        // Créer une instance de FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une ressource");

        // Définir le répertoire initial sur le bureau de l'utilisateur
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        // Afficher la boîte de dialogue de sélection de fichier
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Mettre à jour le champ de texte avec l'emplacement du fichier sélectionné
        if (selectedFile != null) {
            ressource.setText(selectedFile.getAbsolutePath());
        }
    }
    public void navigatetoAfficherCoursAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
            nom.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }
    public void navigatetoCours(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/DisplayCours.fxml"));
            btnCours.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        msg.setVisible(false);
    }
}
