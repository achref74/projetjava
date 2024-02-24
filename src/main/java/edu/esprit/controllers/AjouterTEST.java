package edu.esprit.controllers;

import edu.esprit.entities.Formation;
import edu.esprit.entities.Forum;
import edu.esprit.services.ServiceForum;
import edu.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AjouterTEST {

    @FXML
    private ComboBox<Formation> AfficherFormationNom;

    @FXML
    private TextField TItreForum;

    @FXML
    private TextArea descriptionForum;
    private final ServiceForum serviceForum=new ServiceForum();
    private DataSource MyConnection;



    @FXML
    void AjouterForum(ActionEvent event) {
        try {
            Formation formation = (Formation) AfficherFormationNom.getValue();
            int idFormation = formation.getIdFormation();

            serviceForum.ajouter(new Forum(TItreForum.getText(), descriptionForum.getText(), formation));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("GG");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void AnnulerSaisie(ActionEvent event) {
        TItreForum.clear();
        descriptionForum.clear();

    }

    @FXML
    void retour(ActionEvent event) {
        Node source = (Node) event.getSource();
        Scene currentScene = source.getScene();
        Stage stage = (Stage) currentScene.getWindow();
        stage.close();

    }
    @FXML
    void initialize() {
        List<Formation> formations = new ArrayList<>();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery("SELECT formation.idFormation, formation.nom FROM formation LEFT JOIN forum ON formation.idFormation = forum.idFormation WHERE forum.idFormation IS NULL");
            while (rs.next()) {
                int idFormation = rs.getInt("idFormation");
                String nomFormation = rs.getString("nom");
                formations.add(new Formation(idFormation, nomFormation));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ObservableList<Formation> observableFormations = FXCollections.observableArrayList(formations);
        AfficherFormationNom.setItems(observableFormations);


    }
}
