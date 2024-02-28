package com.esprit.controllers.Categorie;

import com.esprit.models.Categorie;
import com.esprit.services.CategorieService2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class AfficherCategorieController {

    @FXML
    private ListView<Categorie> categoriesListView;
    private ObservableList<Categorie> categoriesObservableList;
    @FXML
    private StackPane contentPane;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDescription;

    @FXML
    private VBox detailsPane;
    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }
    public void initialize() {
        categoriesObservableList = FXCollections.observableArrayList();
        CategorieService2 categorieService = new CategorieService2();
        categoriesObservableList.addAll(categorieService.getAll());
        categoriesListView.setItems(categoriesObservableList);
        detailsPane.setVisible(false);

        categoriesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Categorie categorie, boolean empty) {
                super.updateItem(categorie, empty);
                if (empty || categorie == null || categorie.getNom() == null) {
                    setText(null);
                } else {
                    VBox vBox = new VBox(new Label("Nom: " + categorie.getNom()), new Label("Description: " + categorie.getDescription()));
                    vBox.setSpacing(4);


                    setGraphic(vBox);
                }
            }
        });
        categoriesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            onCategorySelected();
        });
    }

    @FXML
    private void onCategorySelected() {
        Categorie selected = categoriesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tfNom.setText(selected.getNom());
            tfDescription.setText(selected.getDescription());
            detailsPane.setVisible(true);
        } else {
            detailsPane.setVisible(false);
        }
    }
    @FXML
    private void updateCategory(ActionEvent event) {
        Categorie selected = categoriesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Tu veux vraiment modifier?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    selected.setNom(tfNom.getText());
                    selected.setDescription(tfDescription.getText());
                    CategorieService2 categorieService = new CategorieService2();
                    categorieService.modifier(selected);
                    refreshListView();
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Catégorie modifiée avec succès!");
                    successAlert.showAndWait();
                }
            });
        }
    }

    @FXML
    private void deleteCategory(ActionEvent event) {
        Categorie selected = categoriesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Vous êtes sur de supprimer?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    CategorieService2 categorieService = new CategorieService2();
                    categorieService.supprimer(selected.getIdcategorie());
                    refreshListView();
                    detailsPane.setVisible(false);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Catégorie supprimée avec succès!");
                    successAlert.showAndWait();
                }
            });
        }
    }

    private void refreshListView() {
        CategorieService2 categorieService = new CategorieService2();
        categoriesObservableList.setAll(categorieService.getAll());
    }


    public void addCategory(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Categorie/AjouterCategorie.fxml"));
            Node ajouterCategorieView = loader.load();
            contentPane.getChildren().setAll(ajouterCategorieView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
