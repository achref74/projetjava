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
import javafx.scene.Parent;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class AffichageF_Formateur implements Initializable{
    private String currentImageName;

    private String selectedImageUrl;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private TextField prixF;
    @FXML
    private TextField dateD;

    @FXML
    private TextField dateF;

    @FXML
    private TextArea descripF;

    @FXML
    private ImageView fruitImg;

    @FXML
    private TextField nomF;

    @FXML
    private GridPane grid;
@FXML
private Button certificatF;
    @FXML
    private Button modifierF;
    @FXML
    private Button retour;
    @FXML
    private TextField nbrCours;

    @FXML
    private TextField ressource;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button supprimerF;
    @FXML
    private Button ok1;
    private Set<Formation> listF = new HashSet<>();
    private MyListenerF myListener;
    private String selectedIdF ;
    private String selectedNomF ;
    private String selectedNbrCF ;
    private String selectedDescripF ;


    private List<Offre> listO = new ArrayList<>();

    private List<Offre> getData_offre() {
        List<Offre> listO = new ArrayList<>();
        ServiceOffre sf =new ServiceOffre();

        try {
            listO=sf.getAll2();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listO;
    }
    @FXML
    void navigatetoMesFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichage.fxml"));
            prixF.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
    private Set<Formation> getData() {
        Set<Formation> listF = new HashSet<>();
        ServiceFormation sf =new ServiceFormation();

        try {
            listF=sf.getAll1();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listF;
    }

    private void setChosenFormation(Formation formation) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        String dateDString = sdf.format(formation.getDateDebut());
        String dateFString = sdf.format(formation.getDateFin());
        dateD.setText(dateDString);
        dateF.setText(dateFString);
        nomF.setText(formation.getNom());
        nbrCours.setText(String.valueOf(formation.getNbrCours()));
        descripF.setText(formation.getDescription());
        prixF.setText(String.valueOf(formation.getPrix()));
        selectedNbrCF=String.valueOf(formation.getNbrCours());
        selectedNomF=String.valueOf(formation.getNom());
        selectedDescripF=String.valueOf(formation.getDescription());
        selectedIdF = String.valueOf(formation.getIdFormation());
        //fruitPriceLabel.setText(MainFx.CURRENCY + cours.getDuree());

        String imagePath = "file:///C:/Users/DELL GAMING/Desktop/PI/getionFormation3A4/src/main/resources/images/" + formation.getImageUrl();
        Image image = new Image(imagePath);
        fruitImg.setImage(image);
        currentImageName = formation.getImageUrl();
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


        listF.addAll(getData());
        if (!listF.isEmpty()) {
            Iterator<Formation> iterator = listF.iterator();
            Formation firstFormation = iterator.next();
            setChosenFormation(firstFormation);

            myListener = new MyListenerF() {
                @Override
                public void onClickListener(Formation formation) {
                    setChosenFormation(formation);
                }

                @Override
                public void onClickListener1(Offre var2) {

                }

                @Override
                public void onClickListener2(Formation var3) {

                }


            };
        }
       refreshDisplayAfterOffer();
        certificatF.setOnAction(event -> ajouterCertificat());
        fruitImg.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");

            // Définir le répertoire initial sur le dossier "images" de votre projet
            String userDirectoryString = System.getProperty("user.dir") + "/src/main/resources/images";
            File userDirectory = new File(userDirectoryString);
            fileChooser.setInitialDirectory(userDirectory);

            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                selectedImageUrl = selectedFile.toURI().toString();
                Image newImage = new Image(selectedImageUrl);
                fruitImg.setImage(newImage);
            }
        });

    }
    public  void ajouterCertificat()
    {
        LocalDate currentDate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(currentDate);

        ServiceCertificat sc=new ServiceCertificat();

        try {
            sc.ajouter(new Certificat(
                    selectedNomF, selectedDescripF, date, Integer.parseInt(selectedNbrCF), Integer.parseInt(selectedIdF)
            ));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Certificat ajoutée avec succès !");
            alert.show();
            Pdf generator = new Pdf();
            generator.createPdfWithPDFBox("C:\\Users\\DELL GAMING\\Desktop\\PI\\getionFormation3A4\\src\\main\\resources\\Pdf\\certificat.pdf", new Certificat(
                    selectedNomF, selectedDescripF, date, Integer.parseInt(selectedNbrCF), Integer.parseInt(selectedIdF)
            ));
            // Appeler la méthode pour ouvrir le répertoire après la création du PDF
            try {
                openDirectoryInWindows();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    private static void openDirectoryInWindows() throws Exception {
        Runtime.getRuntime().exec("explorer C:\\Users\\DELL GAMING\\Desktop\\PI\\getionFormation3A4\\src\\main\\resources\\Pdf");
    }

    private void clearChosenCours() {
        nomF.clear();
        dateF.clear();
        dateD.clear();
        descripF.clear();
        prixF.clear();
        nbrCours.clear();
        nomF.setText("");
    }

    public void refreshDisplayAfterOffer() {

        // Clear existing items
        grid.getChildren().clear();

        // Reload data
        listF = getData();
        listO = getData_offre();// Suppose this fetches the updated list of formations
        // Recreate display items for each formation
        int column = 0, row = 1; int i=0;
        for (Formation formation : listF) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemF.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemF itemController = fxmlLoader.getController();

                if (itemController != null) {
                    itemController.setData(formation, myListener);
                    for (Offre offre : listO){

                        if (itemController.getFormation().getIdFormation() == offre.getIdFormation()) {
                            // Update display with new price if needed
                            itemController.updatePrixDisplay(offre.getPrixOffre());
                            break;// This assumes you want to update all displayed formations
                        }}}
                i++;
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}