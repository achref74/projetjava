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

public class AfficherFormation implements Initializable {
@FXML
private ScrollPane scrollPane;
    @FXML
    private VBox Vbox;

    @FXML
    private Tab afficher_F;

    @FXML
    private Button ajouterF;

    @FXML
    private Button ajouterOffre;

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
    private TextField dateD;

    @FXML
    private TextField nomF_Offre;
@FXML
private TextField id_F_O;
    @FXML
    private DatePicker dateDO;

    @FXML
    private TextField dateF;

    @FXML
    private DatePicker dateFO;

    @FXML
    private Button deleteF;

    @FXML
    private TextField descripF;

    @FXML
    private TextArea descripO;

    @FXML
    private TextField idF;

    @FXML
    private Tab modif_supp_F;

    @FXML
    private Button navigate;

    @FXML
    private TextField nbrC;

    @FXML
    private TextField nomF;

    @FXML
    private TextField nomF1;

    @FXML
    private Button offreF;

    @FXML
    private Tab offre_F;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlOverview1;

    @FXML
    private Pane pnlOverview11;

    @FXML
    private TextField prix;

    @FXML
    private TextField prixO;

    @FXML
    private TextField searchF;

    @FXML
    private TextField searchF1;

    @FXML
    private TabPane tabPane_F;

    @FXML
    private Button updateF;
    ServiceFormation sf=new ServiceFormation();
    ServiceOffre so=new ServiceOffre();
    private String selecteNom;
    private String selecteIdF;
    private String selecteDescrip;
    private String selecteDateD;
    private String selecteDateF;
    private String selectePrix;
    private String selecteNbrC;
private String selecteNom1;
private String selecteIdO;

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
        idF.setVisible(false);
        id_F_O.setVisible(false);
        scrollPane.setFitToWidth(true); // Ajuster à la largeur
        scrollPane.setContent(Vbox);
        navigate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AfficherFormation.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) deleteF.getScene().getWindow();

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

        List<Formation> formationList = null;
        try {
            formationList = sf.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Formation List: " + formationList); // Print the list

        for (Formation formations : formationList) {
            System.out.println("Adding Formation to TitledPane: " + formations);
            // Create layout for each reclamation
            Label nomLabel = new Label("nom: " + formations.getNom());
            Label descripdLabel = new Label("Description: " + formations.getDescription());
            Label dateDLabel = new Label("Date Debut: " + formations.getDateDebut());
            Label dateFLabel = new Label("Date Fin: " + formations.getDateFin());
            Label prixLabel = new Label("Prix: " + formations.getPrix());
            Label nbrCLabel = new Label("Nombre Cours: " + formations.getNbrCours());
            Button offreButton = new Button("Offre");
            offreButton.setOnAction(event -> {
                tabPane_F.getSelectionModel().select(offre_F);
                 selecteNom1 = formations.getNom();
                 selecteIdO=String.valueOf(formations.getIdFormation());
                  id_F_O.setText(selecteIdO);
                nomF_Offre.setText(selecteNom1);
            });
            GridPane gridPane = new GridPane();
            gridPane.add(nomLabel, 0, 0);
            gridPane.add(descripdLabel, 0, 1);
            gridPane.add(dateDLabel, 0, 2);
            gridPane.add(dateFLabel, 0, 3);
            gridPane.add(prixLabel, 0, 4);
            gridPane.add(nbrCLabel, 0, 5);
            gridPane.add(offreButton, 0, 6);


            TitledPane titledPane = new TitledPane("Formation: " + formations.getIdFormation(), gridPane);
            //HBox content = new HBox(titledPane, offreButton);

            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    selecteIdF=String.valueOf(formations.getIdFormation());
                    selecteNom = formations.getNom();
                    selecteDescrip = formations.getDescription();
                    selecteDateD = String.valueOf(formations.getDateDebut());
                    selecteDateF = String.valueOf(formations.getDateFin());
                    selectePrix = String.valueOf(formations.getPrix());
                    selecteNbrC = String.valueOf(formations.getNbrCours());
                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selecteIdF);
                    System.out.println("Selected Nom : " + selecteNom);
                    System.out.println("Selected Description: " + selecteDescrip);
                    System.out.println("Selected Date Debut: " + selecteDateD);
                    System.out.println("Selected Date Fin: " + selecteDateF);
                    System.out.println("Selected Prix : " + selectePrix);
                    System.out.println("Selected Nombre Des Cours: " + selecteNbrC);
                    idF.setText(selecteIdF);
                    nomF.setText(selecteNom);
                    descripF.setText(selecteDescrip);
                    dateD.setText(selecteDateD);
                    dateF.setText(selecteDateF);
                    prix.setText(selectePrix);
                    nbrC.setText(selecteNbrC);
                    tabPane_F.getSelectionModel().select(modif_supp_F);
                }
            });
            Vbox.getChildren().add(titledPane);

        }
        // ----------------delete code --------------------------
       deleteF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(selecteIdF != null) {
                    try {
                        sf.supprimer(Integer.parseInt(selecteIdF));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    loadReclamationData();
                    tabPane_F.getSelectionModel().select(afficher_F);

                }
            }
        });

        //-------------update_formation----------------------
        updateF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format de date à utiliser
                try {
                    Date dateDebut = dateFormat.parse(dateD.getText());
                    Date dateFin = dateFormat.parse(dateF.getText());
                    Formation formation = new Formation(Integer.parseInt(idF.getText()),nomF.getText(),descripF.getText(),dateDebut,dateFin,Double.parseDouble(prix.getText()),Integer.parseInt(nbrC.getText()));
                    if(selecteIdF != null) {
                        try {

                            sf.modifier(formation);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        loadReclamationData();
                        tabPane_F.getSelectionModel().select(afficher_F);

                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


            }

        });
       //-------------------------ajouterOffre----------------------//
       ajouterOffre.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent actionEvent) {
               java.sql.Date dateDebut = java.sql.Date.valueOf(dateDO.getValue());
               java.sql.Date dateFin = java.sql.Date.valueOf(dateFO.getValue());


               try {
                   so.ajouter(new Offre(Double.parseDouble(prixO.getText()),
                                   descripO.getText(),
                                   dateDebut,
                                   dateFin,
                           Integer.parseInt(id_F_O.getText())));
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Success");
                   alert.setContentText("Offre ajoutée avec succès !");
                   alert.show();
                   tabPane_F.getSelectionModel().select(afficher_F);

               } catch (SQLException e) {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("SQL Exception");
                   alert.setContentText(e.getMessage());
                   alert.showAndWait();
               }
           }
                });
    }

    //-------------refresh fucnction ---------------------------------------
    private void loadReclamationData() {
        Vbox.getChildren().clear(); // Clear existing display
        List<Formation> formationList = null;
        try {
            formationList = sf.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Reclamation List: " + formationList); // Print the list

        for (Formation formations : formationList) {
            System.out.println("Adding reclamation to TitledPane: " + formations);
            // Create layout for each reclamation
            Label idLabel = new Label("id: " + formations.getIdFormation());
            Label nomLabel = new Label("nom: " + formations.getNom());
            Label descripdLabel = new Label("Description: " + formations.getDescription());
            Label dateDLabel = new Label("Email: " + formations.getDateDebut());
            Label dateFLabel = new Label("Object: " + formations.getDateFin());
            Label prixLabel = new Label("Rec: " + formations.getPrix());
            Label nbrCLabel = new Label("Date Rec: " + formations.getNbrCours());

            GridPane gridPane = new GridPane();
            gridPane.add(nomLabel, 0, 0);
            gridPane.add(descripdLabel, 0, 1);
            gridPane.add(dateDLabel, 0, 2);
            gridPane.add(dateFLabel, 0, 3);
            gridPane.add(prixLabel, 0, 4);
            gridPane.add(nbrCLabel, 0, 5);

            TitledPane titledPane = new TitledPane("Reclamation " + formations.getIdFormation(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selecteIdF = String.valueOf(formations.getIdFormation());
                    selecteNom = formations.getNom();
                    selecteDescrip = formations.getDescription();
                    selecteDateD = String.valueOf(formations.getDateDebut());
                    selecteDateF = String.valueOf(formations.getDateFin());
                    selectePrix = String.valueOf(formations.getPrix());
                    selecteNbrC = String.valueOf(formations.getNbrCours());


                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selecteIdF);
                    System.out.println("Selected Nom : " + selecteNom);
                    System.out.println("Selected Description: " + selecteDescrip);
                    System.out.println("Selected Date Debut: " + selecteDateD);
                    System.out.println("Selected Date Fin: " + selecteDateF);
                    System.out.println("Selected Prix : " + selectePrix);
                    System.out.println("Selected Nombre Des Cours: " + selecteNbrC);
                    idF.setText(selecteIdF);

                    nomF.setText(selecteNom);
                    descripF.setText(selecteDescrip);
                    dateD.setText(selecteDateD);
                    dateF.setText(selecteDateF);
                    prix.setText(selectePrix);
                    nbrC.setText(selecteNbrC);
                    tabPane_F.getSelectionModel().select(modif_supp_F);

                }
            });

            Vbox.getChildren().add(titledPane);
        }
    }
}



