package edu.esprit.controllers;

import edu.esprit.entities.Formation;
import edu.esprit.services.ServiceFormation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AjouterFormation {
    @FXML
    private TextField nomF;

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

    @FXML
    void AjouterFormationAction(ActionEvent event) {
        if (dateDF.getValue() != null && dateFF.getValue() != null) {
            java.sql.Date dateDebut = java.sql.Date.valueOf(dateDF.getValue());
            java.sql.Date dateFin = java.sql.Date.valueOf(dateDF.getValue());

            try {
                sp.ajouter(new Formation(nomF.getText(),
                        descripF.getText(),
                        dateDebut,
                        dateFin,
                        Double.parseDouble(prixF.getText()),
                        Integer.parseInt(nbrCourF.getText())));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText("Formation ajoutée avec succès !");
                alert.show();

                // Charger le fichier FXML AfficherFormation.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affichage.fxml"));
                Parent root = loader.load();
                System.out.println("FXML chargé avec succès");

                // Créer une nouvelle scène avec le fichier FXML chargé
                Scene scene = new Scene(root);

                // Obtenir la scène actuelle à partir de n'importe quel nœud dans la hiérarchie de la scène
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Remplacer la scène actuelle par la nouvelle scène chargée
                stage.setScene(scene);
                stage.show();
            } catch (SQLException | IOException | NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Exception");
                alert.setContentText("Une erreur s'est produite : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void navigatetoAfficheFormationAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichage.fxml"));
            nomF.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }
}