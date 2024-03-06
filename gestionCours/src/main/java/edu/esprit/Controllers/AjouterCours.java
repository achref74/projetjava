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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
    private MediaView image;

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

    private String imagePath;
    @FXML
    void selectVideo(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une vidéo");

        // Ensure the initial directory exists
        File initialDirectory = new File("C:/Users/LENOVO/Desktop/gestionCours/src/main/resources/videos");
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(initialDirectory);
        } else {
            System.out.println("The specified initial directory does not exist or is not a directory.");
        }

        // Set the extension filter for video files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Vidéos (*.mp4, *.avi, *.mov)", "*.mp4", "*.avi", "*.mov");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Stockez uniquement le chemin relatif ou le nom de la vidéo sélectionnée
            selectedImagePath = selectedFile.getName();
            Media media = new Media(new File(String.valueOf(selectedFile)).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            image.setMediaPlayer(mediaPlayer);


            // Ici, vous pouvez ajouter la logique pour utiliser la vidéo sélectionnée, comme la charger dans un lecteur multimédia.
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

                String nomCours = nom.getText();
                if (sp.coursExiste(nomCours)) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cours existant");
                    alert.setHeaderText(null);
                    alert.setContentText("Le cours avec le nom " + nomCours + " existe déjà !");
                    alert.showAndWait();
                } else {
                sp.ajouter(new Cours(
                        nom.getText(),
                        description.getText(),
                        prerequis.getText(),
                        ressource.getText(),
                        sqlDate,
                        Integer.parseInt(duree.getText()),
                        selectedImagePath
                )); alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Cours ajouté avec succès !");
                    alert.show();
                    navigatetoAfficherCoursAction(event);
                }

            }
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

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            ressource.setText(selectedFile.getAbsolutePath());
        }
    }
    public void navigatetoAfficherCoursAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Market.fxml"));
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

    @FXML
    private Button retour;
    public void retour(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Market.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }


}
