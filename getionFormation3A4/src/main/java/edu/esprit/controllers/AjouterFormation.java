package edu.esprit.controllers;

import edu.esprit.entities.Formation;
import edu.esprit.services.ServiceFormation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class AjouterFormation implements Initializable {

    @FXML
    public Label fxerrorprix;
    @FXML
    public Label fxerrordate;
@FXML
    private TextField nomF;

    @FXML
    private MediaView image;

    @FXML
    private TextArea descripF;
    @FXML
    private DatePicker dateDF;
    @FXML
    private DatePicker dateFF;
    @FXML
    private TextField prixF;
    @FXML
    private TextField nbrCourF;
    private final ServiceFormation sp = new ServiceFormation();
    @FXML
private Button btnFormation;

    private String selectedImagePath;

    private String imagePath;

    @FXML
    void selectVideo(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une vidéo");

        // Ensure the initial directory exists
        File initialDirectory = new File("C:\\Users\\DELL GAMING\\Desktop\\PI\\getionFormation3A4\\src\\main\\resources\\images");
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
    void AjouterFormationAction(ActionEvent event) {
        Alert alert;
        if (nomF.getText().isEmpty() || descripF.getText().isEmpty() || prixF.getText().isEmpty() || nbrCourF.getText().isEmpty() || dateDF.getValue() == null || dateFF.getValue() == null) {
             alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs vides");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
        } else {
            java.sql.Date dateDebut = java.sql.Date.valueOf(dateDF.getValue());
            java.sql.Date dateFin = java.sql.Date.valueOf(dateFF.getValue()); // Correction ici
            if (!sp.isValidPrix(Double.parseDouble(prixF.getText())) && !sp.isValidDate(dateDebut, dateFin)) {
                fxerrorprix.setVisible(true);
                fxerrordate.setVisible(true);
            } else if (!sp.isValidPrix(Double.parseDouble(prixF.getText()))) {
                fxerrorprix.setVisible(true);
                fxerrordate.setVisible(false);
            } else if (!sp.isValidDate(dateDebut, dateFin)) {
                fxerrorprix.setVisible(false);
                fxerrordate.setVisible(true);
            } else {
                fxerrorprix.setVisible(false);
                fxerrordate.setVisible(false);
                try {
                    if (selectedImagePath == null || selectedImagePath.isEmpty()) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setContentText("Veuillez sélectionner une image.");
                        alert.showAndWait();
                        return;
                    }
                    sp.ajouter(new Formation(nomF.getText(),
                            descripF.getText(),
                            dateDebut,
                            dateFin,
                            Double.parseDouble(prixF.getText()),
                            Integer.parseInt(nbrCourF.getText()),
                            selectedImagePath
                    ));
                         alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setContentText("Formation ajoutée avec succès !");
                    alert.show();

                    // Charger le fichier FXML AfficherFormation.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affichage.fxml"));
                    Parent root = loader.load();

                    // Créer une nouvelle scène avec le fichier FXML chargé
                    Scene scene = new Scene(root);

                    // Obtenir la scène actuelle à partir de n'importe quel nœud dans la hiérarchie de la scène
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    // Remplacer la scène actuelle par la nouvelle scène chargée
                    stage.setScene(scene);
                    stage.show();

                } catch (SQLException | IOException | NumberFormatException e) {
                     alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Exception");
                    alert.setContentText("Une erreur s'est produite : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }

    public void navigatetoAfficheFormationAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichage.fxml"));
            btnFormation.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxerrorprix.setVisible(false);
        fxerrordate.setVisible(false);

    }
}