package com.esprit.controllers.Achats;

import com.esprit.models.Achat;
import com.esprit.models.Formation;
import com.esprit.models.outil;
import com.esprit.services.AchatService2;
import com.esprit.services.OutilsService2;
import com.esprit.services.ServiceFormation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.io.IOException;


public class AfficherAchatsController {

    @FXML
    private ListView<Achat> achatsListView;
    private ObservableList<Achat> achatsObservableList;

    @FXML
    private ComboBox<outil> cbOutils;

    @FXML
    private ComboBox<Formation> cbFormation;

    @FXML
    private TextField tfTotalePrice;

    @FXML
    private DatePicker datePicker;

    @FXML
    private VBox detailsPane;

    private AchatService2 achatService;
    private OutilsService2 outilsService;
    private ServiceFormation formationService;
    @FXML
    private StackPane contentPane;
    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }
    @FXML
    public void initialize() {
        achatService = new AchatService2();
        outilsService = new OutilsService2();
        formationService = new ServiceFormation() ;

        achatsObservableList = FXCollections.observableArrayList();
        achatsListView.setItems(achatsObservableList);
        refreshAchatsList();

        cbOutils.setItems(FXCollections.observableArrayList(outilsService.getAll()));
        cbFormation.setItems(FXCollections.observableArrayList(formationService.getAll()));

        detailsPane.setVisible(false);

        achatsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Achat item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    System.out.println(item);
                    VBox vBox = new VBox(
                            new Label("Formation: " + (item.getidFormation() != null ? item.getidFormation().getNom() : "N/A")),
                            new Label("Outil: " + (item.getOutil() != null ? item.getOutil().getNom() : "N/A")),
                            new Label("Prix Total: " + item.getTotal()),
                            new Label("Date: " + item.getDate())
                    );
                    vBox.setSpacing(4);
                    setGraphic(vBox);
                }
            }
        });

        achatsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfTotalePrice.setText(String.valueOf(newValue.getTotal()));
                datePicker.setValue(newValue.getDate());
                cbOutils.setValue(newValue.getOutil());
                cbFormation.setValue(newValue.getidFormation());
                detailsPane.setVisible(true);
            }
        });
    }

    private void refreshAchatsList() {
        achatsObservableList.setAll(achatService.getAll());
    }

    @FXML
    public void addAchats(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Achats/AjouterAchats.fxml"));
            Node ajouterCategorieView = loader.load();
            contentPane.getChildren().setAll(ajouterCategorieView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateAchats(ActionEvent event) {
        Achat selected = achatsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Tu veux vraiment modifier?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    selected.setTotal(Double.parseDouble(tfTotalePrice.getText()));
                    selected.setDate(datePicker.getValue());
                    selected.setOutil(cbOutils.getValue());
                    selected.setFormation(cbFormation.getValue());
                    achatService.modifier(selected);
                    refreshAchatsList();
                    detailsPane.setVisible(false);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Achats modifiée avec succès!");
                    successAlert.showAndWait();
                }
            }) ;
        }
    }

    @FXML
    private void deleteAchats(ActionEvent event) {
        Achat selected = achatsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Vous êtes sur de supprimer?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
            achatService.supprimer(selected.getIdAchat());
            refreshAchatsList();
            detailsPane.setVisible(false);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Achats supprimée avec succès!");
            successAlert.showAndWait();
                }
            });
        }
    }


}
