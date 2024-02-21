package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.services.ServiceCours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;





public class AjouterCours {
    @FXML
    private Text Duree;

    @FXML
    private Button ajouterFormation;

    @FXML
    private Button btnCours;

    @FXML
    private Button btnFormation;

    @FXML
    private Button btnForum;

    @FXML
    private Button btnOutils;

    @FXML
    private Button btnReclamation;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnUser;

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
    void AjouterCoursAction(ActionEvent event) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(utilDate.getTime());

        Alert alert;
        sp.ajouter(new Cours(nom.getText(),
                description.getText(),
                prerequis.getText(),
                ressource.getText(),
                date,
                Integer.parseInt(duree.getText())));


        alert = new Alert(Alert.AlertType.INFORMATION);


        alert.setTitle("Success");
        alert.setContentText("Cours ajouté avec succès !");
        alert.show();

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
}
