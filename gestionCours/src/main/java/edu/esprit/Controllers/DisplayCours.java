package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.services.ServiceCours;
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
import java.util.Set;

public class DisplayCours implements Initializable {
    @FXML
    private VBox Vbox;

    @FXML
    private TextField date;

    @FXML
    private Button delete;

    @FXML
    private TextField description;

    @FXML
    private TextField duree;

    @FXML
    private TextField id_cours;

    @FXML
    private Button navigate;

    @FXML
    private TextField nom;

    @FXML
    private TextField prerequis;

    @FXML
    private TextField ressource;

    @FXML
    private Button update;

    ServiceCours serviceCours = new ServiceCours();

    private String selectedId;
    private String selectedNom;
    private String selectedDescription;
    private String selecteddate;
    private String selectedduree;
    private String selectedPrerequis;
    private String selectedRessource;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/DisplayCours.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

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



        Set<Cours> coursSet = serviceCours.getAll();
        System.out.println("Reclamation List: " + coursSet); // Print the list

        for (Cours cours : coursSet) {
            System.out.println("Adding Cours to TitledPane: " + cours);
            // Create layout for each reclamation
            Label id_cours = new Label("ID: " + cours.getId_cours());
            Label nom = new Label("Nom : " + cours.getNom());
            Label description = new Label("Discription: " + cours.getDescrption());
            Label date = new Label("Date : " + cours.getDate());
            Label duree = new Label("Rec: " + cours.getDuree());
            Label prerequis = new Label("Date Rec: " + cours.getPrerequis());
            Label ressource = new Label("Date Rec: " + cours.getRessource());

            GridPane gridPane = new GridPane();
            gridPane.add(id_cours, 0, 0);
            gridPane.add(nom, 0, 1);
            gridPane.add(description, 0, 2);
            gridPane.add(date, 0, 3);
            gridPane.add(duree, 0, 4);
            gridPane.add(prerequis, 0, 5);
            gridPane.add(ressource, 0, 6);

            TitledPane titledPane = new TitledPane("Cours " + cours.getId_cours(), gridPane);

            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Récupérer les attributs du cours sélectionné
                    selectedId = String.valueOf(cours.getId_cours());
                    selectedNom = cours.getNom();
                    selectedDescription = cours.getDescrption();
                    selecteddate = String.valueOf(cours.getDate());
                    selectedduree = String.valueOf(cours.getDuree());
                    selectedPrerequis = cours.getPrerequis();
                    selectedRessource = cours.getRessource();

                    // Afficher les attributs dans les champs texte correspondants
                  id_cours.setText(selectedId);
                    nom.setText(selectedNom);
                    description.setText(selectedDescription);
                    date.setText(selecteddate);
                    duree.setText(selectedduree);
                    prerequis.setText(selectedPrerequis);
                    ressource.setText(selectedRessource);

                    // Afficher les attributs dans la console pour vérification
                    System.out.println("Selected ID: " + selectedId);
                    System.out.println("Selected Nom : " + selectedNom);
                    System.out.println("Selected Description: " + selectedDescription);
                    System.out.println("Selected Date: " + selecteddate);
                    System.out.println("Selected Duree: " + selectedduree);
                    System.out.println("Selected Prerequis: " + selectedPrerequis);
                    System.out.println("Selected Ressource: " + selectedRessource);
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
                    serviceCours.supprimer(Integer.parseInt(selectedId));
                    loadReclamationData();
                }
            }
        });
        //-----------------------------------




        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    // Convertir le texte de date en objet Date
                    Date dateValue = Date.valueOf(date.getText());

                    // Créer un objet Cours avec les valeurs des champs texte
                    Cours cours = new Cours(nom.getText(), description.getText(), prerequis.getText(), ressource.getText(), dateValue, Integer.parseInt(duree.getText()));

                    if (selectedId != null) {
                        // Appeler la méthode de modification dans le service
                        serviceCours.modifier(cours);

                        // Mise à jour de l'affichage après la modification
                        loadReclamationData();
                    }
                } catch (IllegalArgumentException e) {
                    // Afficher un message d'erreur si la conversion de la date échoue
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Format de date invalide. Utilisez le format YYYY-MM-DD.");
                    alert.setTitle("Erreur de date");
                    alert.show();
                }
            }
        });







    }




    //-------------refresh fucnction ---------------------------------------
    private void loadReclamationData() {
        Vbox.getChildren().clear(); // Clear existing display

        Set<Cours> coursSet = serviceCours.getAll();

        for (Cours cours : coursSet) {
            // Create layout for each cours
            Label id = new Label("ID: " + cours.getId_cours());
            Label nomLabel = new Label("Nom : " + cours.getNom());
            Label descriptionLabel = new Label("Description: " + cours.getDescrption());
            Label dateLabel = new Label("Date : " + cours.getDate());
            Label dureeLabel = new Label("Durée: " + cours.getDuree());
            Label prerequisLabel = new Label("Prérequis: " + cours.getPrerequis());
            Label ressourceLabel = new Label("Ressource: " + cours.getRessource());

            GridPane gridPane = new GridPane();
            gridPane.add(id, 0, 0);
            gridPane.add(nomLabel, 0, 1);
            gridPane.add(descriptionLabel, 0, 2);
            gridPane.add(dateLabel, 0, 3);
            gridPane.add(dureeLabel, 0, 4);
            gridPane.add(prerequisLabel, 0, 5);
            gridPane.add(ressourceLabel, 0, 6);

            TitledPane titledPane = new TitledPane("Cours " + cours.getId_cours(), gridPane);

            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Récupérer les attributs du cours sélectionné
                    selectedId = String.valueOf(cours.getId_cours());
                    selectedNom = cours.getNom();
                    selectedDescription = cours.getDescrption();
                    selecteddate = String.valueOf(cours.getDate());
                    selectedduree = String.valueOf(cours.getDuree());
                    selectedPrerequis = cours.getPrerequis();
                    selectedRessource = cours.getRessource();

                    // Afficher les attributs dans les champs texte correspondants
                    id_cours.setText(selectedId);
                    nom.setText(selectedNom);
                    description.setText(selectedDescription);
                    date.setText(selecteddate);
                    duree.setText(selectedduree);
                    prerequis.setText(selectedPrerequis);
                    ressource.setText(selectedRessource);

                    // Afficher les attributs dans la console pour vérification
                    System.out.println("Selected ID: " + selectedId);
                    System.out.println("Selected Nom : " + selectedNom);
                    System.out.println("Selected Description: " + selectedDescription);
                    System.out.println("Selected Date: " + selecteddate);
                    System.out.println("Selected Durée: " + selectedduree);
                    System.out.println("Selected Prérequis: " + selectedPrerequis);
                    System.out.println("Selected Ressource: " + selectedRessource);
                }
            });

            Vbox.getChildren().add(titledPane);
        }
    }
}
