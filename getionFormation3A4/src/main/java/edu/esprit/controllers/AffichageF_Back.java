package edu.esprit.controllers;

import edu.esprit.entities.Certificat;
import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.services.ServiceCertificat;
import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOffre;
import edu.esprit.tests.MyListenerF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AffichageF_Back implements Initializable {
    private String selectedImageUrl;
    @FXML
    private GridPane grid;
    @FXML
    private GridPane grid1;
    @FXML
    private ImageView fruitImg;
    @FXML
    private GridPane grid2;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button offreF;
    @FXML
    private Button certificatF;
    @FXML
    private Tab tabO;
    @FXML
    private Tab tabC;
    @FXML
    private Tab tabF;



    @FXML
    void navigatetoOffre(ActionEvent event) {
        // Sélectionner l'onglet Formation (tab index 0)
        tabPane.getSelectionModel().select(tabO);
    }
    @FXML
    void navigatetoCertif(ActionEvent event) {
        // Sélectionner l'onglet Formation (tab index 0)
        tabPane.getSelectionModel().select(tabC);
    }
    @FXML
    void navigatetoFormation(ActionEvent event) {
        // Sélectionner l'onglet Formation (tab index 0)
        tabPane.getSelectionModel().select(tabF);
    }
    private Set<Formation> listF = new HashSet<>();
    private MyListenerF myListener1;
    private Set<Formation> getData() {
        Set<Formation> listF = new HashSet<>();
        ServiceFormation sf =new ServiceFormation();

        try {
            listF=sf.getAll4();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listF;
    }

    private Set<Offre> listO = new HashSet<>();
    private List<Offre> getData1() {
        List<Offre> listO = new ArrayList<>();
        ServiceOffre sf =new ServiceOffre();

        try {
            listO=sf.getAll1();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listO;
    }
    private List<Certificat> listC = new ArrayList<>();

    private List<Certificat> getData2() {
        List<Certificat> listC = new ArrayList<>();
        ServiceCertificat sc =new ServiceCertificat();

        try {
            listC=sc.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listC;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listF.addAll(getData());

        int column = 0;
        int row = 1;
        try {


            int i = 0;
            for (Formation formation : listF) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemF_Back.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemF_Back itemF = fxmlLoader.getController();


                if  (itemF != null) {

                    itemF.setData2(formation, myListener1);
                } else  System.err.println("itemController est null");
                i++;
                if (column == 1) {
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
     offreF.setOnAction(event -> afficherOffre());
        certificatF.setOnAction(event -> afficherCertif());
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate( new OfferCleanupTask(), 0, 1, TimeUnit.DAYS);

    }



    private void afficherOffre() {
        tabPane.getSelectionModel().select(1);

        listO.addAll(getData1());

        int column = 0;
        int row = 1;
        try {


            int i = 0;
            for (Offre offre : listO) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemF_Back.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemF_Back itemF = fxmlLoader.getController();


                if  (itemF != null) {

                    itemF.setData3(offre, myListener1);
                } else  System.err.println("itemController est null");
                i++;
                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid1.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid1.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid1.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid1.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid1.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid1.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid1.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void afficherCertif() {
        tabPane.getSelectionModel().select(2);


        listC.addAll(getData2());

        int column = 0;
        int row = 1;
        try {


            int i = 0;
            for (Certificat certificat : listC) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemF_Back.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemF_Back itemF = fxmlLoader.getController();


                if  (itemF != null) {

                    itemF.setData4(certificat, myListener1);
                } else  System.err.println("itemController est null");
                i++;
                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid2.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid2.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid2.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid2.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid2.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid2.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid2.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
