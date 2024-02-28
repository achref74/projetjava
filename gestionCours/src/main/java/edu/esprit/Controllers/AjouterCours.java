package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.services.ServiceCours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.Date;
import java.sql.SQLException;





public class AjouterCours {
    @FXML
    private Text Duree;



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

    @FXML
    void selectImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String absolutePath = "C:\\Users\\LENOVO\\Desktop\\gestionCours\\src\\main\\resources\\images\\" + selectedFile.getName();
            Image newImage = new Image("file:///" + absolutePath);
            image.setImage(newImage);
            System.out.println("Chemin d'accès de l'image sélectionnée : " + absolutePath);
        }
    }
    @FXML
    void AjouterCoursAction(ActionEvent event) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        Alert alert;
        try {

            if (image.getImage() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez sélectionner une image.");
                alert.showAndWait();
                return;
            }


            sp.ajouter(new Cours(
                    nom.getText(),
                    description.getText(),
                    prerequis.getText(),
                    ressource.getText(),
                    sqlDate,
                    Integer.parseInt(duree.getText()),
                    image.getImage().getUrl()
            ));

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Cours ajouté avec succès !");
            alert.show();
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

}
