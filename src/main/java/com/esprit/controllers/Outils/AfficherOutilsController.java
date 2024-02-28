package com.esprit.controllers.Outils;

import com.esprit.models.Categorie;
import com.esprit.models.outil;
import com.esprit.services.CategorieService2;
import com.esprit.services.OutilsService2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class AfficherOutilsController {

    @FXML
    private ListView<outil> outilsListView;
    private ObservableList<outil> outilsObservableList;

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfRessources;
    @FXML
    private TextField tfStock;
    @FXML
    private TextField tfEtat;
    @FXML private ComboBox<Categorie> cbCategorie;
    @FXML private TextField tfImagePath;
    @FXML
    private TextField searchField;

    @FXML
    private VBox detailsPane;
    @FXML
    private StackPane contentPane;
    private OutilsService2 outilsService;
    private CategorieService2 categorieService;
    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    @FXML
    private void initialize() {
        outilsObservableList = FXCollections.observableArrayList();
        outilsService = new OutilsService2();
        categorieService = new CategorieService2();

        outilsObservableList.addAll(outilsService.getAll());
        outilsListView.setItems(outilsObservableList);
        cbCategorie.getItems().addAll(categorieService.getAll());


        outilsListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(outil item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vBox = new VBox(5);
                    vBox.setAlignment(Pos.CENTER);

                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    imageView.setPreserveRatio(true);
                    try {

                        File file = new File("src/main/resources/images/" + item.getImage());
                        String imageUrl = file.toURI().toURL().toExternalForm();
                        Image image = new Image(imageUrl);
                        imageView.setImage(image);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    vBox.getChildren().add(imageView);
                    vBox.getChildren().add(new Label("Nom: " + item.getNom()));
                    vBox.getChildren().add(new Label("Description: " + item.getDescription()));
                    vBox.getChildren().add(new Label("Prix: " + item.getPrix()));
                    vBox.getChildren().add(new Label("Ressources: " + item.getRessources()));
                    vBox.getChildren().add(new Label("Stock: " + item.getStock()));
                    vBox.getChildren().add(new Label("État: " + item.getEtat()));
                    System.out.println(item.getCategorie());
                    vBox.getChildren().add(new Label("Catégorie: " + (item.getCategorie() != null ? item.getCategorie().getNom() : "N/A")));

                    vBox.setSpacing(4);

                    setGraphic(vBox);
                }
            }
        });

        detailsPane.setVisible(false);

        outilsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showOutilDetails(newValue);
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterList(newValue);
        });
    }


    private void showOutilDetails(outil selectedOutil) {
        tfNom.setText(selectedOutil.getNom());
        tfDescription.setText(selectedOutil.getDescription());
        tfPrix.setText(selectedOutil.getPrix().toString());
        tfRessources.setText(selectedOutil.getRessources());
        tfStock.setText(selectedOutil.getStock());
        tfEtat.setText(selectedOutil.getEtat());
        cbCategorie.setValue(selectedOutil.getCategorie());
        tfImagePath.setText(selectedOutil.getImage());
        detailsPane.setVisible(true);
    }
    private void filterList(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            outilsListView.setItems(outilsObservableList); // Reset to all items if search is empty
        } else {
            ObservableList<outil> filteredList = FXCollections.observableArrayList();
            for (outil item : outilsObservableList) {
                // Modify this condition to match your search criteria
                if (item.getNom().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            outilsListView.setItems(filteredList);
        }
    }
    @FXML
    private void addOutil(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Outils/AjouterOutils.fxml"));
            Node ajouterOutilsView = loader.load();


            contentPane.getChildren().setAll(ajouterOutilsView);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void updateOutil(ActionEvent event) {
        outil selectedOutil = outilsListView.getSelectionModel().getSelectedItem();
        if (selectedOutil != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment modifier cet outil?");
            confirmationAlert.setTitle("Confirmation de modification");
            confirmationAlert.setHeaderText(null);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                selectedOutil.setNom(tfNom.getText());
                selectedOutil.setDescription(tfDescription.getText());
                selectedOutil.setPrix(Double.parseDouble(tfPrix.getText()));
                selectedOutil.setRessources(tfRessources.getText());
                selectedOutil.setStock(tfStock.getText());
                selectedOutil.setEtat(tfEtat.getText());
                selectedOutil.setCategorie(cbCategorie.getValue());
                selectedOutil.setImage(tfImagePath.getText());
                outilsService.modifier(selectedOutil);
                refreshListView() ;
                clearDetails();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Outil modifié avec succès!");
                successAlert.setTitle("Modification réussie");
                successAlert.setHeaderText(null);
                successAlert.showAndWait();
            }
        }
    }

    @FXML
    private void deleteOutil(ActionEvent event) {
        outil selectedOutil = outilsListView.getSelectionModel().getSelectedItem();
        if (selectedOutil != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer cet outil?");
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                outilsService.supprimer(selectedOutil.getIdoutils());


                outilsObservableList.remove(selectedOutil);
                refreshListView() ;

                clearDetails();


                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Outil supprimé avec succès!");
                successAlert.setTitle("Suppression réussie");
                successAlert.setHeaderText(null);
                successAlert.showAndWait();
            }
        }
    }
    private void refreshListView() {
        OutilsService2 outilsService = new OutilsService2();
        outilsObservableList.setAll(outilsService.getAll());
    }
    private void clearDetails() {
        // Clear all text fields
        tfNom.clear();
        tfDescription.clear();
        tfPrix.clear();
        tfRessources.clear();
        tfStock.clear();
        tfEtat.clear();
        tfImagePath.clear();

        detailsPane.setVisible(false);
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {

                Path resourcesDir = Path.of("src/main/resources/images");
                if (!Files.exists(resourcesDir)) {
                    Files.createDirectories(resourcesDir);
                }

                Path imagePath = resourcesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                tfImagePath.setText(selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload image.");
                alert.showAndWait();
            }
        }
    }

}
