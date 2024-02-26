package edu.esprit.controllers;


import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOffre;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherOffre implements Initializable {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox Vbox;

    @FXML
    private Tab afficher_O;

    @FXML
    private Button ajouterO;



    @FXML
    private Button btnCours1;

    @FXML
    private Button btnCours11;

    @FXML
    private Button btnCours111;

    @FXML
    private Button btnFormation;

    @FXML
    private Button btnFormation11;

    @FXML
    private Button btnFormation111;

    @FXML
    private Button btnForum1;

    @FXML
    private Button btnForum11;

    @FXML
    private Button btnForum111;

    @FXML
    private Button btnOutils1;

    @FXML
    private Button btnOutils11;

    @FXML
    private Button btnOutils111;

    @FXML
    private Button btnReclamation1;

    @FXML
    private Button btnReclamation11;

    @FXML
    private Button btnReclamation111;

    @FXML
    private Button btnSignout1;

    @FXML
    private Button btnSignout11;

    @FXML
    private Button btnSignout111;

    @FXML
    private Button btnUser1;

    @FXML
    private Button btnUser11;

    @FXML
    private Button btnUser111;

    @FXML
    private Button certificatF;

    @FXML
    private TextField dateD_O;
    @FXML
    private TextField id_O;

@FXML
private TextField prix_O;
    @FXML
    private TextField dateF_O;

    @FXML
    private TextField descrip_O;

    @FXML
    private Button deleteO;

    @FXML
    private Tab modif_supp_O;

    @FXML
    private Button navigate_O;


    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlOverview1;

    @FXML
    private Pane pnlOverview11;



    @FXML
    private TextField searchF;

    @FXML
    private TextField searchF1;

    @FXML
    private TabPane tabPane_F;

    @FXML
    private Button updateO;
    ServiceOffre so=new ServiceOffre();

    private String selectPrixO;
    private String selecteIdO;
    private String selecteDescripO;
    private String selecteDateDO;
    private String selecteDateFO;


    @FXML
    void navigatetoAfficheFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFormation.fxml"));
            btnFormation.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
    @FXML
    void navigatetoAjouterFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterFormation.fxml"));
            btnFormation.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
    @FXML
    void navigatetoOffreFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterOffre.fxml"));
            btnFormation.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
    @FXML
    void navigatetoCertificatFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterCertificat.fxml"));
            btnFormation.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_O.setVisible(false);
        scrollPane.setFitToWidth(true); // Ajuster à la largeur
        scrollPane.setContent(Vbox);
        navigate_O.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) deleteO.getScene().getWindow();

                    // Set the new scene to the stage
                    stage.setScene(scene);


                } catch (IOException var4) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sorry");
                    alert.setTitle("Error");
                    alert.show();
                }

            }
        });
        Vbox.getChildren().clear();

        List<Offre> offreList = null;
        try {
            offreList = so.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Formation List: " + offreList); // Print the list

        for (Offre offres :offreList) {
            System.out.println("Adding Offre to TitledPane: " + offres);
            // Create layout for each reclamation
            Label prix_oLabel = new Label("prix: " + offres.getPrixOffre());
            Label descrip_OLabel = new Label("Description: " + offres.getDescription());
            Label dateD_OLabel = new Label("Date Debut: " + offres.getDateD());
            Label dateF_OLabel = new Label("Date Fin: " + offres.getDateF());

            GridPane gridPane = new GridPane();
            gridPane.add(prix_oLabel, 0, 0);
            gridPane.add(descrip_OLabel, 0, 1);
            gridPane.add(dateD_OLabel, 0, 2);
            gridPane.add(dateF_OLabel, 0, 3);



            TitledPane titledPane = new TitledPane("Offre: " + offres.getIdOffre(), gridPane);
            //HBox content = new HBox(titledPane, offreButton);

            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    selecteIdO=String.valueOf(offres.getIdOffre());
                    selectPrixO = String.valueOf(offres.getPrixOffre());
                    selecteDescripO = offres.getDescription();
                    selecteDateDO = String.valueOf(offres.getDateD());
                    selecteDateFO = String.valueOf(offres.getDateF());

                    id_O.setText(selecteIdO);
                    prix_O.setText(selectPrixO);
                    descrip_O.setText(selecteDescripO);
                    dateD_O.setText(selecteDateDO);
                    dateF_O.setText(selecteDateFO);

                    tabPane_F.getSelectionModel().select(modif_supp_O);
                }
            });
            Vbox.getChildren().add(titledPane);

        }
        // ----------------delete code --------------------------
        deleteO.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(selecteIdO != null) {
                    try {
                        so.supprimer(Integer.parseInt(selecteIdO));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    loadReclamationData();
                    tabPane_F.getSelectionModel().select(afficher_O);

                }
            }
        });

        //-------------update_formation----------------------
        updateO.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format de date à utiliser
                try {
                    Date dateDebut = dateFormat.parse(dateD_O.getText());
                    Date dateFin = dateFormat.parse(dateF_O.getText());
                    Offre offre = new Offre(Integer.parseInt(id_O.getText()),Double.parseDouble(prix_O.getText()),descrip_O.getText(),dateDebut,dateFin);
                    if(selecteIdO != null) {
                        try {

                            so.modifier(offre);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        loadReclamationData();
                        tabPane_F.getSelectionModel().select(afficher_O);

                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


            }

        });
        //-------------------------ajouterOffre----------------------//

    }

    //-------------refresh fucnction ---------------------------------------
    private void loadReclamationData() {
        Vbox.getChildren().clear(); // Clear existing display
        List<Offre> offreList = null;
        try {
            offreList = so.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("offre List: " + offreList); // Print the list

        for (Offre offres : offreList) {
            System.out.println("Adding Offre to TitledPane: " + offres);
            // Create layout for each reclamation
            Label prix_oLabel = new Label("nom: " + offres.getPrixOffre());
            Label descrip_OLabel = new Label("Description: " + offres.getDescription());
            Label dateD_OLabel = new Label("Date Debut: " + offres.getDateD());
            Label dateF_OLabel = new Label("Date Fin: " + offres.getDateF());

            GridPane gridPane = new GridPane();
            gridPane.add(prix_oLabel, 0, 0);
            gridPane.add(descrip_OLabel, 0, 1);
            gridPane.add(dateD_OLabel, 0, 2);
            gridPane.add(dateF_OLabel, 0, 3);



            TitledPane titledPane = new TitledPane("Offre: " + offres.getIdOffre(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selecteIdO=String.valueOf(offres.getIdOffre());
                    selectPrixO = String.valueOf(offres.getPrixOffre());
                    selecteDescripO = offres.getDescription();
                    selecteDateDO = String.valueOf(offres.getDateD());
                    selecteDateFO = String.valueOf(offres.getDateF());

                    id_O.setText(selecteIdO);
                    prix_O.setText(selectPrixO);
                    descrip_O.setText(selecteDescripO);
                    dateD_O.setText(selecteDateDO);
                    dateF_O.setText(selecteDateFO);

                    tabPane_F.getSelectionModel().select(modif_supp_O);

                }
            });

            Vbox.getChildren().add(titledPane);
        }
    }
}



