package edu.esprit.controllers;

import edu.esprit.entities.Forum;
import edu.esprit.services.ServiceForum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherForum implements Initializable {

    @FXML
    private ListView<Forum> TableauForum;
    private final ServiceForum serviceForum=new ServiceForum();

    public void afficheForum(javafx.event.ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Forum> forumList;

        try {
            forumList = serviceForum.getAll();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de base de données !");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }

        // Créez une liste observable pour les éléments du ListView
        ObservableList<Forum> observableForumList = FXCollections.observableArrayList(forumList);

        // Utilisez le ListCell personnalisé (ForumListCell)
        //TableauForum.setCellFactory(param -> new ForumListCell());

        // Ajoutez les éléments au ListView
        TableauForum.setItems(observableForumList);

    }
}