package com.esprit.controllers.Menu;

import com.esprit.controllers.Achats.AfficherAchatsController;
import com.esprit.controllers.Categorie.AfficherCategorieController;
import com.esprit.controllers.Outils.AfficherOutilsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class MenuController {

    @FXML
    private StackPane contentPane;


    private void loadContent(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node view = loader.load();

            if (fxml.equals("/Categorie/AfficherCategorie.fxml")) {
                AfficherCategorieController controller = loader.getController();
                controller.setMainContent(contentPane);
            }
            if (fxml.equals("/Outils/AfficherOutils.fxml")) {
                AfficherOutilsController controller = loader.getController();
                controller.setMainContent(contentPane);
            }
            if (fxml.equals("/Achats/AfficherAchats.fxml")) {
                AfficherAchatsController controller = loader.getController();
                controller.setMainContent(contentPane);
            }
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onCategoriesClick(ActionEvent event) {
        loadContent("/Categorie/AfficherCategorie.fxml");
    }

    public void onOutilsClick(ActionEvent actionEvent) {
        loadContent("/Outils/AfficherOutils.fxml") ;
    }

    public void onAchatsClick(ActionEvent actionEvent) {
        loadContent("/Achats/AfficherAchats.fxml") ;
    }
}