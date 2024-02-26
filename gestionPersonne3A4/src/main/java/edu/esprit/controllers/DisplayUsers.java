package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;

import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
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
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayUsers implements Initializable {
    @FXML
    VBox Vbox;
    @FXML
    private TextField adresse;

    @FXML
    private TextField adresse1;

    @FXML
    private TextField cv;

    @FXML
    private TextField datenaissance;
    @FXML
    private TextField disponibilite1;

    @FXML
    private TextField dt1;

    @FXML
    private TextField email;

    @FXML
    private TextField email1;

    @FXML
    private TextField id;

    @FXML
    private TextField id1;
    @FXML
    private TextField niveauaca;

    @FXML
    private TextField niveausco;

    @FXML
    private TextField nom;

    @FXML
    private TextField nom1;

    @FXML
    private TextField numtel;

    @FXML
    private TextField numtel1;

    @FXML
    private TextField prenom;

    @FXML
    private TextField prenom1;

    @FXML
    private TextField pwd;

    @FXML
    private TextField pwd1;
    @FXML
    private TextField specialite;
    @FXML
    Button delete,update,navigate;
    @FXML
    private ScrollPane scrollepanne;

    ServiceUser serviceuser = new ServiceUser();

    private String selectedId;
    private String selectedpwd;
    private String selectednom;
    private String selectedEmail;
    private String selectedprenom;
    private String selecteddatenaiss;
    private String selectedadresse;
    private String selectednomtel;
    private String selectedniveauscolaire;
    private String selectedniveauacademique;
    private String    selectedspecialite   ;
    private String    selecteddisponibilite  ;
    private String selectedcv;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root, 800, 600); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) delete.getScene().getWindow();

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
        List<User> userlist = serviceuser.getAll();
        System.out.println("user List: " + userlist); // Print the list

        for (User user : userlist) {
            System.out.println("Adding reclamation to TitledPane: " + user);
            // Create layout for each user
            Label idLabel = new Label("ID: " + user.getId());
            Label namelabel = new Label("NomUser: " + user.getNom());
            Label prenomlabel = new Label("PrenomUser: " + user.getPrenom());
            Label datelabel = new Label("Date De Naissance: " + user.getDateNaissance());
            Label adresselabel = new Label("Adresse: " + user.getAdresse());
            Label numtellabel = new Label("Numero tel: " + user.getNumtel());
            Label password = new Label("mot de passe: " + user.getMdp());


            if (user instanceof Client c) {
                Label niveauscolairelabel = new Label("Niveau scolaire: " + c.getNiveau_scolaire());
                GridPane gridPane = new GridPane();
                gridPane.add(idLabel, 0, 0);
                gridPane.add(namelabel, 0, 1);
                gridPane.add(prenomlabel, 0, 2);
                gridPane.add(datelabel, 0, 3);
                gridPane.add(adresselabel, 0, 4);
                gridPane.add(numtellabel, 0, 5);

                gridPane.add(password, 0, 6);
                gridPane.add(niveauscolairelabel, 0, 7);
                TitledPane titledPane = new TitledPane("user " + user.getId(), gridPane);

                titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedId = "" + user.getId();
                        selectednom = "" + user.getNom();
                        selectedprenom = user.getPrenom();
                        selectedEmail = user.getEmail();
                        selecteddatenaiss = String.valueOf(user.getDateNaissance());
                        selectedadresse =user.getAdresse();
                        selectedpwd =user.getMdp();
                        selectednomtel =String.valueOf(user.getNumtel());
                        selectedniveauscolaire =c.getNiveau_scolaire();


                        // Perform any action with the selected values
                        System.out.println("Selected ID: " + selectedId);
                        System.out.println("Selected nom: " + selectednom);
                        System.out.println("Selected prenom: " + selectedprenom);
                        System.out.println("Selected Email: " + selectedEmail);
                        System.out.println("Selected Date de naissance: " + selecteddatenaiss);
                        System.out.println("Selected adresse: " + selectedadresse);
                        System.out.println("Selected adresse: " + selectednomtel);
                        System.out.println("Selected adresse: " + selectedniveauscolaire);


                        id.setText(selectedId);
                        nom.setText(selectednom);
                        prenom.setText(selectedprenom);
                        email.setText(selectedEmail);
                        datenaissance.setText(selecteddatenaiss);
                        adresse.setText(selectedadresse);
                        numtel.setText(selectednomtel);
                        pwd.setText(selectedpwd);
                        niveausco.setText(selectedniveauscolaire);

                    }
                });
                Vbox.getChildren().add(titledPane);

            } else if (user instanceof Formateur f) {
                Label niveauacademiquelabel = new Label("Date Rec: " + f.getNiveauAcademique());
                Label specialitelabel = new Label("Date Rec: " + f.getSpecialite());
                Label disponibilitelabel = new Label("Date Rec: " + f.getDisponibilite());
                Label cvlabel = new Label("CV: " + f.getCv());
                GridPane gridPane = new GridPane();
                gridPane.add(idLabel, 0, 0);
                gridPane.add(namelabel, 0, 1);
                gridPane.add(prenomlabel, 0, 2);
                gridPane.add(datelabel, 0, 3);
                gridPane.add(adresselabel, 0, 4);
                gridPane.add(numtellabel, 0, 5);
                gridPane.add(niveauacademiquelabel, 0, 6);
                gridPane.add(specialitelabel, 0, 7);
                gridPane.add(disponibilitelabel, 0, 8);
                gridPane.add(cvlabel, 0, 9);
                TitledPane titledPane2 = new TitledPane("user " + user.getId(), gridPane);

                titledPane2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedId = "" + user.getId();
                        selectednom = "" + user.getNom();
                        selectedprenom = user.getPrenom();
                        selectedEmail = user.getEmail();
                        selecteddatenaiss = String.valueOf(user.getDateNaissance());
                        selectedadresse =user.getAdresse();
                        selectedpwd =user.getMdp();
                        selectednomtel =String.valueOf(user.getNumtel());
                        selectedniveauacademique =f.getNiveauAcademique();
                        selectedspecialite =f.getSpecialite();
                        selecteddisponibilite =String .valueOf(f.getDisponibilite());
                        selectedcv =f.getCv();



                        // Perform any action with the selected values
                        System.out.println("Selected ID: " + selectedId);
                        System.out.println("Selected nom: " + selectednom);
                        System.out.println("Selected prenom: " + selectedprenom);
                        System.out.println("Selected Email: " + selectedEmail);
                        System.out.println("Selected Date de naissance: " + selecteddatenaiss);
                        System.out.println("Selected adresse: " + selectedadresse);
                        System.out.println("Selected adresse: " + selectednomtel);
                        System.out.println("Selected adresse: " + selectedniveauscolaire);


                        id1.setText(selectedId);
                        nom1.setText(selectednom);
                        prenom1.setText(selectedprenom);
                        email1.setText(selectedEmail);
                        dt1.setText(selecteddatenaiss);
                        adresse1.setText(selectedadresse);
                        numtel1.setText(selectednomtel);
                        pwd1.setText(selectedpwd);
                        niveauaca.setText(selectedniveauacademique);
                        disponibilite1.setText(selecteddisponibilite);
                        specialite.setText(selectedspecialite);
                        cv.setText(selectedcv);


                    }
                });
                Vbox.getChildren().add(titledPane2);

            }


    }
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(selectedId != null) {
                    serviceuser.supprimer(Integer.parseInt(selectedId));
                    loadReclamationData();
                }
            }
        });
       update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


              //  Client user = new Client(Integer.parseInt(id.getText()),nom.getText(),prenom.getText(),email.getText(), Date.valueOf(datenaissance.getText()),adresse.getText(),Integer.parseInt(numtel.getText()),pwd.getText(),niveausco.getText());

                if(selectedId != null) {
                    //serviceuser.modifier(user);
                    loadReclamationData();
                }
                //Formateur f=new Formateur(Integer.parseInt(id1.getText()),nom1.getText(),prenom1.getText(),email1.getText(),Date.valueOf(dt1.getText()),adresse1.getText(),Integer.parseInt(numtel1.getText()),pwd1.getText(),specialite.getText(),niveauaca.getText(),Integer.parseInt(disponibilite1.getText()),cv.getText());
                if(selectedId != null) {
                   // serviceuser.modifier(f);
                    loadReclamationData();
                }

            }
        });
    }
    private void loadReclamationData() {
        Vbox.getChildren().clear(); // Clear existing display

        List<User> userlist = serviceuser.getAll();
        System.out.println("user List: " + userlist); // Print the list

        for (User user : userlist) {
            System.out.println("Adding reclamation to TitledPane: " + user);
            // Create layout for each user
            Label idLabel = new Label("ID: " + user.getId());
            Label namelabel = new Label("NomUser: " + user.getNom());
            Label prenomlabel = new Label("PrenomUser: " + user.getPrenom());
            Label datelabel = new Label("Date De Naissance: " + user.getDateNaissance());
            Label adresselabel = new Label("Adresse: " + user.getAdresse());
            Label numtellabel = new Label("Numero tel: " + user.getNumtel());
            Label password = new Label("mot de passe: " + user.getMdp());


            if (user instanceof Client c) {
                Label niveauscolairelabel = new Label("Niveau scolaire: " + c.getNiveau_scolaire());
                GridPane gridPane = new GridPane();
                gridPane.add(idLabel, 0, 0);
                gridPane.add(namelabel, 0, 1);
                gridPane.add(prenomlabel, 0, 2);
                gridPane.add(datelabel, 0, 3);
                gridPane.add(adresselabel, 0, 4);
                gridPane.add(numtellabel, 0, 5);

                gridPane.add(password, 0, 6);
                gridPane.add(niveauscolairelabel, 0, 7);
                TitledPane titledPane = new TitledPane("user " + user.getId(), gridPane);

                titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedId = "" + user.getId();
                        selectednom = "" + user.getNom();
                        selectedprenom = user.getPrenom();
                        selectedEmail = user.getEmail();
                        selecteddatenaiss = String.valueOf(user.getDateNaissance());
                        selectedadresse =user.getAdresse();
                        selectedpwd =user.getMdp();
                        selectednomtel =String.valueOf(user.getNumtel());
                        selectedniveauscolaire =c.getNiveau_scolaire();


                        // Perform any action with the selected values
                        System.out.println("Selected ID: " + selectedId);
                        System.out.println("Selected nom: " + selectednom);
                        System.out.println("Selected prenom: " + selectedprenom);
                        System.out.println("Selected Email: " + selectedEmail);
                        System.out.println("Selected Date de naissance: " + selecteddatenaiss);
                        System.out.println("Selected adresse: " + selectedadresse);
                        System.out.println("Selected adresse: " + selectednomtel);
                        System.out.println("Selected adresse: " + selectedniveauscolaire);


                        id.setText(selectedId);
                        nom.setText(selectednom);
                        prenom.setText(selectedprenom);
                        email.setText(selectedEmail);
                        datenaissance.setText(selecteddatenaiss);
                        adresse.setText(selectedadresse);
                        numtel.setText(selectednomtel);
                        pwd.setText(selectedpwd);
                        niveausco.setText(selectedniveauscolaire);

                    }
                });
                Vbox.getChildren().add(titledPane);

            } else if (user instanceof Formateur f) {
                Label niveauacademiquelabel = new Label("Date Rec: " + f.getNiveauAcademique());
                Label specialitelabel = new Label("Date Rec: " + f.getSpecialite());
                Label disponibilitelabel = new Label("Date Rec: " + f.getDisponibilite());
                Label cvlabel = new Label("CV: " + f.getCv());
                GridPane gridPane = new GridPane();
                gridPane.add(idLabel, 0, 0);
                gridPane.add(namelabel, 0, 1);
                gridPane.add(prenomlabel, 0, 2);
                gridPane.add(datelabel, 0, 3);
                gridPane.add(adresselabel, 0, 4);
                gridPane.add(numtellabel, 0, 5);
                gridPane.add(niveauacademiquelabel, 0, 6);
                gridPane.add(specialitelabel, 0, 7);
                gridPane.add(disponibilitelabel, 0, 8);
                gridPane.add(cvlabel, 0, 9);
                TitledPane titledPane2 = new TitledPane("user " + user.getId(), gridPane);

                titledPane2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedId = "" + user.getId();
                        selectednom = "" + user.getNom();
                        selectedprenom = user.getPrenom();
                        selectedEmail = user.getEmail();
                        selecteddatenaiss = String.valueOf(user.getDateNaissance());
                        selectedadresse =user.getAdresse();
                        selectedpwd =user.getMdp();
                        selectednomtel =String.valueOf(user.getNumtel());
                        selectedniveauacademique =f.getNiveauAcademique();
                        selectedspecialite =f.getSpecialite();
                        selecteddisponibilite =String .valueOf(f.getDisponibilite());
                        selectedcv =f.getCv();



                        // Perform any action with the selected values
                        System.out.println("Selected ID: " + selectedId);
                        System.out.println("Selected nom: " + selectednom);
                        System.out.println("Selected prenom: " + selectedprenom);
                        System.out.println("Selected Email: " + selectedEmail);
                        System.out.println("Selected Date de naissance: " + selecteddatenaiss);
                        System.out.println("Selected adresse: " + selectedadresse);
                        System.out.println("Selected adresse: " + selectednomtel);
                        System.out.println("Selected adresse: " + selectedniveauscolaire);


                        id1.setText(selectedId);
                        nom1.setText(selectednom);
                        prenom1.setText(selectedprenom);
                        email1.setText(selectedEmail);
                        dt1.setText(selecteddatenaiss);
                        adresse1.setText(selectedadresse);
                        numtel1.setText(selectednomtel);
                        pwd1.setText(selectedpwd);
                        niveauaca.setText(selectedniveauacademique);
                        disponibilite1.setText(selecteddisponibilite);
                        specialite.setText(selectedspecialite);
                        cv.setText(selectedcv);


                    }
                });
                Vbox.getChildren().add(titledPane2);

            }



        }





    }

}