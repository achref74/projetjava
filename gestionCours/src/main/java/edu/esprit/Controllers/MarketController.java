package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.services.ServiceCours;
import edu.esprit.tests.MainFx;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;
public class MarketController implements Initializable {

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private TextField date;


    @FXML
    private TextField description;

    @FXML
    private TextField duree;

    @FXML
    private ImageView fruitImg;


    @FXML
    private TextField fruitNameLable;

    @FXML
    private GridPane grid;

    @FXML
    private TextField prerequis;

    @FXML
    private TextField ressource;
  /*  @FXML
    private TextField image;*/

    @FXML
    private ScrollPane scroll;

    private Set<Cours> liste = new HashSet<>();

    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
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
        duree.setText(String.format("%dh %dmin %dsc", heures, minutes, secondes)); // Formatage de la durée

        prerequis.setText(cours.getPrerequis());
        ressource.setText(cours.getRessource());

        // Mettre à jour l'image
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
                @Override
                public void onClickListener(Cours cours) {
                    setChosenCours(cours);
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
        fruitImg.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                selectedImageURL = selectedFile.toURI().toString();
                Image newImage = new Image(selectedImageURL);
                fruitImg.setImage(newImage);
            }
        });
        modifier.setOnAction(event -> modifierCours());
        supprimer.setOnAction(event -> supprimerCours());

    }


@FXML
    private void supprimerCours() {
        // Vérifiez si l'ID du cours sélectionné n'est pas vide
        if (selectedId != null && !selectedId.isEmpty()) {
            // Convertissez l'ID en entier si nécessaire
            int id = Integer.parseInt(selectedId);

            // Appelez la méthode de service pour supprimer le cours avec l'ID sélectionné
            ServiceCours serviceCours = new ServiceCours();
            serviceCours.supprimer(id);

            // Mettez à jour la liste des cours après la suppression
            liste.clear();
            liste.addAll(getData());

            // Rafraîchissez l'affichage après la suppression
            grid.getChildren().clear(); // Effacez le contenu actuel du grid

            int column = 0;
            int row = 1;

            // Réinitialisez l'affichage avec la nouvelle liste de cours
            try {
                for (Cours cours : liste) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Item.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();

                    if (itemController != null) {
                        itemController.setData(cours, myListener);
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
        fruitNameLable.clear();
        date.clear();
        description.clear();
        duree.clear();
        prerequis.clear();
        ressource.clear();
       /* image.clear();*/
        fruitNameLable.setText("");
    }

////

    private String currentImageName;
    private void modifierCours() {
        // Vérifiez si l'ID du cours sélectionné n'est pas vide
        if (selectedId != null && !selectedId.isEmpty()) {
            // Convertissez l'ID en entier si nécessaire
            int id = Integer.parseInt(selectedId);

            // Obtenez les nouvelles valeurs des champs de l'interface utilisateur
            String newName = fruitNameLable.getText();
            String newDate = date.getText();
            String newDescription = description.getText();
            String newDuree = duree.getText();
            String newPrerequis = prerequis.getText();
            String newRessource = ressource.getText();

            try {
                // Créez un nouvel objet Cours avec les valeurs mises à jour
                Cours cours = new Cours();

                cours.setId_cours(id);
                cours.setNom(newName);
                cours.setDate(java.sql.Date.valueOf(newDate)); // Conversion String vers java.sql.Date
                cours.setDescrption(newDescription);
                String regex = "\\d+h\\s*\\d+min\\s*\\d+sc";
                if (!newDuree.matches(regex)) {
                    // Afficher un message d'erreur à l'utilisateur ou gérer la validation d'une autre manière
                    System.err.println("La durée doit être au format 'xh ymin zsc'.");
                    return; // Sortir de la méthode si la validation échoue
                }

                // Convertir la durée en entier après validation réussie
                cours.setDuree(Integer.parseInt(newDuree.replaceAll("[^0-9]", "")));
                cours.setPrerequis(newPrerequis);
                cours.setRessource(newRessource);
                if (selectedImageURL != null && !selectedImageURL.isEmpty()) {
                    String imageName = selectedImageURL.substring(selectedImageURL.lastIndexOf("/") + 1);
                    cours.setImage(imageName);
                } else {
                    // Conservez l'image actuelle si aucune nouvelle image n'est sélectionnée.
                    cours.setImage(currentImageName);
                }

                // Appelez la méthode de service pour mettre à jour le cours
                ServiceCours serviceCours = new ServiceCours();
                serviceCours.modifier(cours);

                // Mettez à jour l'affichage global



                liste.clear();
                liste.addAll(getData());
                grid.getChildren().clear();
               // setChosenCours(cours);
  refreshGrid();
            } catch (Exception e) {
                e.printStackTrace();
                // Gérez les erreurs ici
            }
        }
    }

    // Méthode pour afficher les cours dans la grille
    private void refreshGrid() {
        grid.getChildren().clear(); // Effacer la grille pour la rafraîchir
        int column = 0;
        int row = 1;
        for (Cours cours : liste) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                if (itemController != null) {
                    itemController.setData(cours, myListener);
                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                    grid.add(anchorPane, column++, row); //(child,column,row)
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


//////////
////////////
/////////
////////////
/////////////
/////////////
///////////
/* arkah*/