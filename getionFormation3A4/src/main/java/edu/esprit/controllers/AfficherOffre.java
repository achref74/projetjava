package edu.esprit.controllers;


import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOffre;
import edu.esprit.tests.MyListenerF;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

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

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherOffre implements Initializable {


    @FXML
    private VBox chosenFruitCard;

    @FXML
    private TextField dateDO;

    @FXML
    private TextField dateFO;

    @FXML
    private TextArea descripO;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Label fxerrordate1;

    @FXML
    private Label fxerrorprix1;

    @FXML
    private GridPane grid;

    @FXML
    private TextField idF;

    @FXML
    private Button modifierO;

    @FXML
    private TextField nomF;

    @FXML
    private TextField prixFO;

    @FXML
    private Button retour;

    @FXML
    private ScrollPane scroll1;

    @FXML
    private Button supprimerO;

    @FXML
    void navigatetoFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichage.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }




        private List<Offre> listO = new ArrayList<>() {
        };
        private MyListenerF myListenerO;
        private String selectedIdF ;

        private List<Offre> getData() {
            List<Offre> listO = new ArrayList<>();
            ServiceOffre sf =new ServiceOffre();

            try {
                listO=sf.getAll1();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return listO;
        }

        private void setChosenFormation(Offre offre) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

            String dateDString = sdf.format(offre.getDateD());
            String dateFString = sdf.format(offre.getDateF());
            dateDO.setText(dateDString);
            dateFO.setText(dateFString);
            descripO.setText(offre.getDescription());
            prixFO.setText(String.valueOf(offre.getPrixOffre()));
            selectedIdF = String.valueOf(offre.getIdOffre());
            idF.setText(String.valueOf(offre.getIdOffre()));
            //fruitPriceLabel.setText(MainFx.CURRENCY + cours.getDuree());


            List<String> colorPalette = new ArrayList<>();
            colorPalette.add("#D4A5A5");
            colorPalette.add("#A0522D");
            colorPalette.add("#8B4513");
            colorPalette.add("#CD853F");
            colorPalette.add("#D2B48C");
            colorPalette.add("#BC8F8F");
            colorPalette.add("#F4A460");
            colorPalette.add("#DAA520");
            colorPalette.add("#8B4513");
            colorPalette.add("#46637F");
            colorPalette.add("#44505E");
            colorPalette.add("#7D5147");
            colorPalette.add("#7F5A45");
            colorPalette.add("#7E8C6B");
            Random random = new Random();


            String color =colorPalette.get(random.nextInt(colorPalette.size()));

            chosenFruitCard.setStyle("-fx-background-color: " + color + ";\n" +
                    "    -fx-background-radius: 30;");
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {

fxerrorprix1.setVisible(false);
fxerrordate1.setVisible(false);
            idF.setVisible(false);
            //  tabPane.getSelectionModel().select(afficheF);
listO.clear();
            listO.addAll(getData());
            if (!listO.isEmpty()) {
                Iterator<Offre> iterator = listO.iterator();
                Offre firstOffre = iterator.next();
                setChosenFormation(firstOffre);

                myListenerO = new MyListenerF() {
                    @Override
                    public void onClickListener(Formation var1) {

                    }

                    @Override
                    public void onClickListener1(Offre offre) {
                        setChosenFormation(offre);
                    }


                };
            }
            int column = 0;
            int row = 1;
            try {


                int i = 0;
                for (Offre offre : listO) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ItemO.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemO itemO = fxmlLoader.getController();


                    if  (itemO != null) {

                        itemO.setData1(offre, myListenerO);
                    } else  System.err.println("itemController est null");
                    i++;
                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column++, row); //(child,column,row)
                    //set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(10));
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            modifierO.setOnAction(event -> modifierOffre());
            supprimerO.setOnAction(event -> supprimerOffre());




        }

    private void supprimerOffre() {
        // Vérifiez si l'ID du cours sélectionné n'est pas vide
        if (selectedIdF != null && !selectedIdF.isEmpty()) {
            System.out.println(selectedIdF);
            // Convertissez l'ID en entier si nécessaire
            int id = Integer.parseInt(selectedIdF);

            // Appelez la méthode de service pour supprimer le cours avec l'ID sélectionné
ServiceOffre so=new ServiceOffre();
            try {
                so.supprimer(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Mettez à jour la liste des cours après la suppression
            listO.clear();
            listO.addAll(getData());

            // Rafraîchissez l'affichage après la suppression
            grid.getChildren().clear(); // Effacez le contenu actuel du grid

            int column = 0;
            int row = 1;

            // Réinitialisez l'affichage avec la nouvelle liste de cours
            try {
                for (Offre offre : listO) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ItemO.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemO itemController = fxmlLoader.getController();

                    if (itemController != null) {
                        itemController.setData1(offre, myListenerO);
                    } else {
                        System.err.println("itemController est null");
                    }
                    column++;
                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column, row); //(child,column,row)
                    GridPane.setMargin(anchorPane, new Insets(10));
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Effacez les champs de la carte chosenFruitCard
            clearChosenCours();
        }
    }

    private void clearChosenCours() {

        descripO.clear();
        dateFO.clear();
        dateFO.clear();
        prixFO.clear();
        descripO.setText("");
    }

    private void modifierOffre() {
        if (selectedIdF != null && !selectedIdF.isEmpty()) {
            int id = Integer.parseInt(selectedIdF);
            String newDateD = dateDO.getText();
            String newDescription = descripO.getText();
            String newDateF = dateFO.getText();
            String newPrix = prixFO.getText();
            ServiceOffre serviceOffre = new ServiceOffre();

            if (!serviceOffre.isValidPrix(Double.valueOf(newPrix)) && !serviceOffre.isValidDate(java.sql.Date.valueOf(newDateD), java.sql.Date.valueOf(newDateF))) {
                fxerrorprix1.setVisible(true);
                fxerrordate1.setVisible(true);
            } else if (!serviceOffre.isValidPrix(Double.parseDouble(prixFO.getText()))) {
                fxerrorprix1.setVisible(true);
                fxerrordate1.setVisible(false);
            } else if (!serviceOffre.isValidDate(java.sql.Date.valueOf(newDateD), java.sql.Date.valueOf(newDateF))) {
                fxerrorprix1.setVisible(false);
                fxerrordate1.setVisible(true);
            } else {
                fxerrorprix1.setVisible(false);
                fxerrordate1.setVisible(false);

                try {
                    Offre offre = new Offre();
                    offre.setIdOffre(id);
                    offre.setDateD(java.sql.Date.valueOf(newDateD));
                    offre.setDateF(java.sql.Date.valueOf(newDateF));
                    offre.setDescription(newDescription);
                    offre.setPrixOffre(Double.parseDouble(newPrix));

                    serviceOffre.modifier(offre);

                    // Mettre à jour l'affichage
                    listO.clear();
                    listO.addAll(getData());
                    grid.getChildren().clear(); // Effacez le contenu actuel du grid
                    initialize(null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Gérer les erreurs ici
                }
            }
        }
    }


}



