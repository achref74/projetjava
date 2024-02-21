package edu.esprit.controllers;

import edu.esprit.entities.Formation;
import edu.esprit.services.ServiceFormation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherFormation {

    @FXML
    private VBox Vbox;

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
    private TextField dateD;

    @FXML
    private TextField dateF;

    @FXML
    private Button deleteF;

    @FXML
    private TextField descripF;

    @FXML
    private Button navigate;

    @FXML
    private TextField nbrC;

    @FXML
    private TextField nomF;

    @FXML
    private TextField idF;
    @FXML
    private Pane pnlOverview;

    @FXML
    private TextField prix;

    @FXML
    private Button updateF;
    ServiceFormation sf=new ServiceFormation();
    private String selecteNom;
    private String selecteIdF;
    private String selecteDescrip;
    private String selecteDateD;
    private String selecteDateF;
    private String selectePrix;
    private String selecteNbrC;



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
    public void initialize(URL url, ResourceBundle resourceBundle) {
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


        System.out.println("zzzzzzzzz");
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
            gridPane.add(idLabel, 0, 0);
            gridPane.add(nomLabel, 0, 1);
            gridPane.add(descripdLabel, 0, 2);
            gridPane.add(dateDLabel, 0, 3);
            gridPane.add(dateFLabel, 0, 4);
            gridPane.add(prixLabel, 0, 5);
            gridPane.add(nbrCLabel, 0, 6);

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
                    idF.setText(selectedId);
                    nomF.setText(selectedUserId);
                    descripF.setText(selectedEmail);
                    dateD.setText(selectedDateRec);
                    dateF.setText(selectedRec);
                    prix.setText(selectedObject);
                    prix.setText(selectedObject);
                }
            });


            Vbox.getChildren().add(titledPane);
        }

        loadReclamationData();


        // ----------------delete code --------------------------
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(selectedId != null) {
                    serviceReclamation.supprimer(Integer.parseInt(selectedId));
                    loadReclamationData();
                }
            }
        });
        //-----------------------------------


        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Reclamation reclamation = new Reclamation(Integer.parseInt(id.getText()),Integer.parseInt(userid.getText()),email.getText(),object.getText(),reclamationI.getText(),new Date(System.currentTimeMillis()));

                if(selectedId != null) {
                    serviceReclamation.update(reclamation);
                    loadReclamationData();
                }
            }
        });










    }




    //-------------refresh fucnction ---------------------------------------
    private void loadReclamationData() {
        Vbox.getChildren().clear(); // Clear existing display

        List<Reclamation> reclamationList = serviceReclamation.getAll();

        for (Reclamation reclamation : reclamationList) {
            // Create layout for each reclamation
            Label idLabel = new Label("ID: " + reclamation.getId_rec());
            Label userIdLabel = new Label("UserID: " + reclamation.getId());
            Label emailLabel = new Label("Email: " + reclamation.getEmail());
            Label objectLabel = new Label("Object: " + reclamation.getObject());
            Label recLabel = new Label("Rec: " + reclamation.getRec());
            Label dateLabel = new Label("Date Rec: " + reclamation.getDate_rec());

            GridPane gridPane = new GridPane();
            gridPane.add(idLabel, 0, 0);
            gridPane.add(userIdLabel, 0, 1);
            gridPane.add(emailLabel, 0, 2);
            gridPane.add(objectLabel, 0, 3);
            gridPane.add(recLabel, 0, 4);
            gridPane.add(dateLabel, 0, 5);

            TitledPane titledPane = new TitledPane("Reclamation " + reclamation.getId(), gridPane);

            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedId = "" + reclamation.getId_rec();
                    selectedUserId = "" + reclamation.getId();
                    selectedEmail = reclamation.getEmail();
                    selectedObject = reclamation.getObject();
                    selectedRec = reclamation.getRec();
                    selectedDateRec = String.valueOf(reclamation.getDate_rec());

                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selectedId);
                    System.out.println("Selected UserID: " + selectedUserId);
                    System.out.println("Selected Email: " + selectedEmail);
                    System.out.println("Selected Object: " + selectedObject);
                    System.out.println("Selected Rec: " + selectedRec);
                    System.out.println("Selected Date Rec: " + selectedDateRec);

                    id.setText(selectedId);
                    userid.setText(selectedUserId);
                    email.setText(selectedEmail);
                    date.setText(selectedDateRec);
                    reclamationI.setText(selectedRec);
                    object.setText(selectedObject);
                }
            });

            Vbox.getChildren().add(titledPane);
        }
    }
}


}
