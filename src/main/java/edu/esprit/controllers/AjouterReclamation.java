

package edu.esprit.controllers;

import edu.esprit.entites.Formation;
import edu.esprit.entites.Outil;
import edu.esprit.entites.Reclamation;
import edu.esprit.entites.User;
import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOutil;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class AjouterReclamation {

    @FXML
    private ComboBox<Formation> formationComboBox;

    @FXML
    private ComboBox<Outil> outilComboBox;

    @FXML
    private TextArea descriptionTextArea;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();
    private ServiceOutil serviceOutil = new ServiceOutil();
    private ServiceFormation serviceFormation = new ServiceFormation();
    @FXML
    private Button afficherReclamationButton;
    private AfficherReclamationBack afficherReclamationBackController;
    Connection cnx = DataSource.getInstance().getCnx();


    public void setAfficherReclamationBackController(AfficherReclamationBack afficherReclamationBackController) {
        this.afficherReclamationBackController = afficherReclamationBackController;
    }

    @FXML
    public void initialize() throws SQLException {
        // Populate ComboBoxes with data from the database
        populateFormationComboBox();
        populateOutilComboBox();
    }

    private void populateFormationComboBox() {
        Set<Formation> formations = new HashSet<>();
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `formation` WHERE 1");
            while (rs.next()) {
                int idFormation = rs.getInt("idFormation");
                String nomFormation = rs.getString("nom");
                formations.add(new Formation(idFormation, nomFormation));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        formationComboBox.getItems().addAll(formations);
        // Utilisez un StringConverter pour afficher uniquement les noms et prénoms dans le ChoiceBox
        formationComboBox.setConverter(new StringConverter<Formation>() {

            @Override
            public String toString(Formation formation) {
                // Affichez le nom et prénom de l'utilisateur dans le ChoiceBox
                return formation != null ? formation.getNom() : " " ;
            }

            @Override
            public Formation fromString(String string) {
                // Vous n'avez probablement pas besoin d'implémenter cette méthode pour un ChoiceBox
                return null;
            }
        });


    }
    private void populateOutilComboBox() throws SQLException {

        Set<Outil> outils = new HashSet<>();
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `outil` WHERE 1");
            while (rs.next()) {
                int id = rs.getInt("idoutils");
                String nom =rs.getString("nom");
                Outil f = new Outil(id,nom);
                outils.add(f);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        outilComboBox.getItems().addAll(outils);
        // Utilisez un StringConverter pour afficher uniquement les noms et prénoms dans le ChoiceBox
        outilComboBox.setConverter(new StringConverter<Outil>() {

            @Override
            public String toString(Outil outil) {
                // Affichez le nom et prénom de l'utilisateur dans le ChoiceBox
                return outil != null ? outil.getNom() : " " ;
            }

            @Override
            public Outil fromString(String string) {
                // Vous n'avez probablement pas besoin d'implémenter cette méthode pour un ChoiceBox
                return null;
            }
        });

    }

    @FXML
    public void addReclamation() {
        // Retrieve selected values from ComboBoxes and TextArea
        Formation selectedFormation = formationComboBox.getValue();
        Outil selectedOutil = outilComboBox.getValue();
        String description = descriptionTextArea.getText();
        User user1 = new User();
        user1.setId_user(2);

        // Check if either formation or outil is selected
        if ((selectedFormation == null && selectedOutil == null) || description.isEmpty()) {
            // Display an alert or handle the situation accordingly
            System.out.println("Please select either formation or outil and fill in description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Description");
            alert.setContentText("Please select either formation or outil and fill in description.");
            alert.show();
            return;
        }

        // Validate the description using regex
        String descriptionPattern = "^[a-zA-Z0-9\\s]+$";
        Pattern pattern = Pattern.compile(descriptionPattern);
        Matcher matcher = pattern.matcher(description);

        if (!matcher.matches()) {
            // Display an alert or handle the situation accordingly
            System.out.println("Invalid characters in the description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Description");
            alert.setContentText("The description only contains numbers and letters.");
            alert.show();
            return;
        }

        // Create a new Reclamation object only if either formation or outil is selected
        Reclamation reclamation;
        if (selectedFormation.getId_formation()!=-1) {
            reclamation = new Reclamation(user1, new Outil(-1), selectedFormation, description);
        } else {
            reclamation = new Reclamation(user1, selectedOutil, new Formation(-1), description);
        }

        try {
            // Add the reclamation to the database
            serviceReclamation.ajouter(reclamation);
            /*Notifications notificationBuilder = Notifications.create().title("test").text("sumbitted").graphic(null).
                    hideAfter(javafx.util.Duration.seconds(5)).position(Pos.CENTER_LEFT);
            notificationBuilder.show();*/
            System.out.println("Reclamation added successfully!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reclamation");
            alert.setContentText("Reclamation added successfully.");
            alert.show();

            // Notify the AfficherReclamationBack controller to update the UI
            if (afficherReclamationBackController != null) {
                afficherReclamationBackController.updateReclamationList();
            }

            // Close the AjouterReclamation stage
            Stage stage = (Stage) formationComboBox.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reclamation");
            alert.setContentText("Failed to add reclamation.");
            alert.show();
            e.printStackTrace(); // Handle database exception appropriately
            System.out.println("Error adding reclamation.");
        }
    }
    /*public void addReclamation() {
        // Retrieve selected values from ComboBoxes and TextArea
        Formation selectedFormation = formationComboBox.getValue();
        Outil selectedOutil = outilComboBox.getValue();
        String description = descriptionTextArea.getText();
        User user1= new User();
        user1.setId_user(2);

        // Check if all required fields are selected/entered
       // if (selectedFormation == null || selectedOutil == null || description.isEmpty()) {

            // Display an alert or handle the situation accordingly
        if ((selectedFormation == null && selectedOutil == null) || description.isEmpty()) {

            System.out.println("Please fill in all fields.");
            System.out.println("Invalid characters in the description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" desription");
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return;
        }
        // Validate the description using regex
        String descriptionPattern = "^[a-zA-Z0-9\\s]+$";
        Pattern pattern = Pattern.compile(descriptionPattern);
        Matcher matcher = pattern.matcher(description);

        if (!matcher.matches()) {
            // Display an alert or handle the situation accordingly
            System.out.println("Invalid characters in the description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" desription");
            alert.setContentText("the description only contains numbers and letters");
            alert.show();
            return;
        }

        // Create a new Reclamation object
        Reclamation reclamation = new Reclamation(user1, selectedOutil, selectedFormation, description);

        try {
            // Add the reclamation to the database
            serviceReclamation.ajouter(reclamation);
            System.out.println("Reclamation added successfully!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Reclamation");
            alert.setContentText("Reclamation Bien Ajouté");
            alert.show();

            // Notify the AfficherReclamationBack controller to update the UI
            if (afficherReclamationBackController != null) {
                afficherReclamationBackController.updateReclamationList();
            }

            // Close the AjouterReclamation stage
            Stage stage = (Stage) formationComboBox.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Reclamation");
            alert.setContentText("Reclamation N'est pas Ajoutée");
            alert.show();
            e.printStackTrace(); // Handle database exception appropriately
            System.out.println("Error adding reclamation.");
        }
    }*/
    @FXML
    public void openAfficherReclamation() {
        try {
            // Load the FXML file for AfficherReclamationBack
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationBack.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the current button
            Stage stage = (Stage) afficherReclamationButton.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.setTitle("Afficher Reclamation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void setReclamationData(Reclamation reclamation) {
        formationComboBox.setValue(reclamation.getFormation());
        outilComboBox.setValue(reclamation.getOutil());
        descriptionTextArea.setText(reclamation.getDescription());
    }
}

