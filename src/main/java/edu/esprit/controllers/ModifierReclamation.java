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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifierReclamation {
    @FXML
    private ComboBox<Formation> formationComboBox;

    @FXML
    private ComboBox<Outil> outilComboBox;

    @FXML
    private TextArea descriptionTextArea;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();
    private ServiceOutil serviceOutil = new ServiceOutil();
    private ServiceFormation serviceFormation = new ServiceFormation();
    private AfficherReclamationBack afficherReclamationBackController;
    private AfficherReclamationClient afficherReclamationClientController;
    private User user = new User();
    private Reclamation selectedReclamation;
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

    public void updateReclamation(ActionEvent actionEvent) {

        // Retrieve selected values from ComboBoxes and TextArea
        Formation selectedFormation = formationComboBox.getValue();
        Outil selectedOutil = outilComboBox.getValue();
        String description = descriptionTextArea.getText();

        // Check if all required fields are selected/entered
        if (selectedFormation == null || selectedOutil == null || description.isEmpty()) {
            // Display an alert or handle the situation accordingly
            System.out.println("Invalid characters in the description.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" desription");
            alert.setContentText("the description must not be null");
            alert.show();
            System.out.println("Please fill in all fields.");
            return;
        }
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
        //user.setId_user(2);
        // Create a new Reclamation object
        Reclamation reclamation = new Reclamation(user, selectedOutil, selectedFormation, description);

        // Set the ID of the selected Reclamation
        reclamation.setId_reclamation(selectedReclamation.getId_reclamation());
        user.setId_user(selectedReclamation.getUser().getId_user());


        try {
            // Update the reclamation in the database
            serviceReclamation.modifier(reclamation);
            System.out.println("Reclamation modified successfully!");

            // Notify the AfficherReclamationBack controller to update the UI
            if (afficherReclamationBackController != null) {
                afficherReclamationBackController.updateReclamationList();
            }

            // Close the ModifierReclamation stage
            Stage stage = (Stage) formationComboBox.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Reclamation");
            alert.setContentText("Reclamation N'est pas modifiée");
            alert.show();
            e.printStackTrace(); // Handle database exception appropriately
            System.out.println("Error modifying reclamation.");
        }
    }
    public void setReclamationData(Reclamation reclamation, AfficherReclamationBack afficherReclamationBackController) {
        selectedReclamation = reclamation;
        this.afficherReclamationBackController = afficherReclamationBackController;

        formationComboBox.setValue(selectedReclamation.getFormation());
        outilComboBox.setValue(selectedReclamation.getOutil());
        descriptionTextArea.setText(selectedReclamation.getDescription());
    }
    public void setReclamationData(Reclamation reclamation, AfficherReclamationClient afficherReclamationClientController) {
        selectedReclamation = reclamation;
        this.afficherReclamationClientController = afficherReclamationClientController;

        formationComboBox.setValue(selectedReclamation.getFormation());
        outilComboBox.setValue(selectedReclamation.getOutil());
        descriptionTextArea.setText(selectedReclamation.getDescription());
    }
}
