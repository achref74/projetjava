package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.entities.Question;
import edu.esprit.services.ServiceCours;
import edu.esprit.services.ServiceEvaluation;
import edu.esprit.tests.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class AfficherCoursClient implements Initializable {
    @FXML
    private Label msg ;

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label date;


    @FXML
    private Label description;

    @FXML
    private Label duree;

    @FXML
    private ImageView fruitImg;


    @FXML
    private Label fruitNameLable;

    @FXML
    private GridPane grid;

    @FXML
    private Label prerequis;

    @FXML
    private Label ressource;
    @FXML
    private Button ajouterC;
  /*  @FXML
    private TextField image;*/

    @FXML
    private ScrollPane scroll;

    private Set<Cours> liste = new HashSet<>();

    @FXML
    private Button modifier;
    @FXML
    private Button evaluationf;
    @FXML
    private Button supprimer;
    @FXML
    private Button evaluation;
    private MyListener myListener;
    private String selectedId ;


    private Set<Cours> getData() {
        Set<Cours> liste = new HashSet<>();
        ServiceCours serviceCours =new ServiceCours();
        liste=serviceCours.getAll();
        return liste;
    }

    private void setChosenCours(Cours cours) {






        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(cours.getDate());
        date.setText(dateString);
        fruitNameLable.setText(cours.getNom());
        description.setText(cours.getDescrption());

        long heures = cours.getDuree() / 3600;
        long minutes = (cours.getDuree() % 3600) / 60;
        long secondes = cours.getDuree() % 60;
        duree.setText(String.format("%dh %dmin %dsc", heures, minutes, secondes));

        prerequis.setText(cours.getPrerequis());
        ressource.setText(cours.getRessource());

        String imagePath = "file:///C:/Users/LENOVO/Desktop/gestionCours/src/main/resources/images/" + cours.getImage();
        Image image = new Image(imagePath);
        fruitImg.setImage(image);
        currentImageName = cours.getImage();
        selectedId = String.valueOf(cours.getId_cours());

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

        String color = colorPalette.get(random.nextInt(colorPalette.size()));

        chosenFruitCard.setStyle("-fx-background-color: " + color + ";\n" +
                "    -fx-background-radius: 30;");
    }
    private  Cours cours = new Cours();
    private String selectedImageURL;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        liste.addAll(getData());
        if (!liste.isEmpty()) {
            Iterator<Cours> iterator = liste.iterator();
            Cours firstCours = iterator.next();

            setChosenCours(firstCours);

            myListener = new MyListener() {



                LocalDate dateActuelle = LocalDate.now();

                @Override
                public void onClickListener(Cours cours) {
                    // Convertir la date du cours en LocalDate
                    LocalDate dateCours = Instant.ofEpochMilli(cours.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                    if (dateCours.isAfter(dateActuelle)) {
                        // Afficher une alerte si la date du cours est après la date actuelle
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cours non disponible");
                        alert.setHeaderText(null);
                        alert.setContentText("Ce cours n'est pas encore disponible.");
                        alert.showAndWait();
                    } else {
                        setChosenCours(cours);
                    }
                }

                @Override
                public void onClickListner(Question var) {

                }
            };
        }
        int column = 0;
        int row = 1;
        try {


            int i = 0;
            for (Cours cours : liste) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();


                if  (itemController != null) {

                    itemController.setData(cours, myListener);
                } else  System.err.println("itemController est null");
                i++;
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }



    private String currentImageName;





    public void navigatetoEvaluation(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvaluation.fxml"));
            Parent root = loader.load();
            AfficherEvaluation controller = loader.getController();
            controller.setCoursId(selectedId);
            evaluation.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }









}

