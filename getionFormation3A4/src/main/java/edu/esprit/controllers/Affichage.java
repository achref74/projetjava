package edu.esprit.controllers;
import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOffre;
import edu.esprit.tests.MyListenerF;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Affichage implements Initializable {
    @FXML
    private TextField idFO;
    @FXML
    private Button ajouterF;
    @FXML
    private Button afficheF;
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
    private Button retour;
    @FXML
    private TextField nomF;
    @FXML
    private Tab tabOffre;

    @FXML
    private GridPane grid;

    @FXML
    private Button modifierF;

    @FXML
    private TextField nbrCours;
    @FXML
    private TextField nomFO;

    @FXML
    private TextField chercherF_C;

    @FXML
    private TextField ressource;

    @FXML
    private Button ajouterOffre;

    @FXML
    private Button btnCours1;

    @FXML
    private Button btnFormation;

    @FXML
    private Button btnForum1;

    @FXML
    private Button btnOutils1;

    @FXML
    private Button btnReclamation1;

    @FXML
    private Button btnSignout1;

    @FXML
    private Button btnUser1;


    @FXML
    private DatePicker dateDO;

    @FXML
    private Button offreF;

    @FXML
    private DatePicker dateFO;

    @FXML
    private TextArea descripO;


    @FXML
    private TextField idF;


    @FXML
    private Button ok1;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Tab tabF;

    @FXML
    private TextField prixO;


    @FXML
    private ScrollPane scroll;

    @FXML
    private Button supprimerF;

    @FXML
    private TabPane tabPane;
    @FXML
    public Label fxerrorprix1;
    @FXML
    public Label fxerrordate1;

    @FXML
    public Label fxerrorprix2;
    @FXML
    public Label fxerrordate2;
private String selectedImageUrl;
    @FXML
    public Button offre;

    @FXML
    private VBox chosenFruitCard1;

    @FXML
    private TextField dateDO1;

    @FXML
    private TextField dateFO1;

    @FXML
    private TextArea descripO1;

    ServiceFormation sf=new ServiceFormation();


    @FXML
    private Label fxerrordate11;

    @FXML
    private Label fxerrorprix11;

    @FXML
    private GridPane grid1;

    @FXML
    private TextField idF1;

    @FXML
    private Button modifierO;


    @FXML
    private Label fxerrordate12;

private String selectedNomF;
    private String selectedPrix;
    @FXML
    private Label fxerrorprix12;
    @FXML
    private TextField prixFO1;
    @FXML
    private Tab tab3;
    @FXML
    private Button retour1;
    private String currentImageName;

    @FXML
    private ScrollPane scroll1;
private String selectedVideoUrl;
    @FXML
    private Button supprimerO;
    @FXML
    private MediaView video;
    @FXML
    private Button playPauseButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider progressSlider;
    @FXML
    void navigatetoFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffichageF_Formateur.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    void navigatetoOffreAction(ActionEvent event) {
        tabPane.getSelectionModel().select(tabOffre);

        nomFO.setText(selectedNomF);
    }

    @FXML
    void navigatetoAjouterFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterFormation.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    void navigatetoOffre(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

 @FXML
    void navigatetoFormation(ActionEvent event) {
        // Sélectionner l'onglet Formation (tab index 0)
        tabPane.getSelectionModel().select(0);
    }

    private Set<Formation> listF = new HashSet<>();
    private List<Offre> listO = new ArrayList<>();
    private MyListenerF myListener;
    private String selectedIdF1;
    private String selectedIdF;

    private Set<Formation> getData() {
        Set<Formation> listF = new HashSet<>();
        ServiceFormation sf = new ServiceFormation();

        try {
            listF = sf.formationsExistForUser(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listF;
    }
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
    private void setChosenFormation(Formation formation) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        String dateDString = sdf.format(formation.getDateDebut());
        String dateFString = sdf.format(formation.getDateFin());
        idF.setText(String.valueOf(formation.getIdFormation()));
        dateD.setText(dateDString);
        dateF.setText(dateFString);
        nomF.setText(formation.getNom());
        nbrCours.setText(String.valueOf(formation.getNbrCours()));
        descripF.setText(formation.getDescription());
        prixF.setText(String.valueOf(formation.getPrix()));
        selectedNomF= String.valueOf(formation.getNom());
        selectedIdF=String.valueOf(formation.getIdFormation());
        selectedPrix=String.valueOf(formation.getPrix());

        String videoFile = "C:\\Users\\DELL GAMING\\Desktop\\PI\\getionFormation3A4\\src\\main\\resources\\images\\" + formation.getImageUrl();
        currentImageName = formation.getImageUrl();
        //fruitPriceLabel.setText(MainFx.CURRENCY + cours.getDuree());
        // Remplacez par le chemin de votre vidéo

        Media media = new Media(new File(videoFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        video.setMediaPlayer(mediaPlayer);
        playPauseButton.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                playPauseButton.setText("Play");
            } else {
                mediaPlayer.play();
                playPauseButton.setText("Pause");
            }
        });

        // Curseur de volume
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mediaPlayer.setVolume(volumeSlider.getValue() / 100);
        });

        // Barre de progression
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!progressSlider.isValueChanging()) {
                progressSlider.setValue(newTime.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
            }
        });
        progressSlider.setOnMouseReleased(e -> {
            mediaPlayer.seek(Duration.millis(progressSlider.getValue() / 100 * mediaPlayer.getTotalDuration().toMillis()));
        });

        mediaPlayer.setOnReady(() -> {
            progressSlider.setMax(100);
        });




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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fxerrorprix1.setVisible(false);
        fxerrorprix2.setVisible(false);
        fxerrordate1.setVisible(false);
        fxerrordate2.setVisible(false);
        idFO.setVisible(false);

        idF.setVisible(false);
        //  tabPane.getSelectionModel().select(afficheF);
        listF.clear();

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
        clearChosenCours();

        refreshDisplayAfterOffer(getData());
        modifierF.setOnAction(event -> modifierFormation());
        supprimerF.setOnAction(event -> supprimerFormations());
        ajouterOffre.setOnAction(event -> AjouterOffre());



        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate( new OfferCleanupTask(), 0, 1, TimeUnit.DAYS);
    }
    @FXML
    void selectVideo(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une vidéo");

        // Ensure the initial directory exists
        File initialDirectory = new File("C:\\Users\\DELL GAMING\\Desktop\\PI\\getionFormation3A4\\src\\main\\resources\\images");
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(initialDirectory);
        } else {
            System.out.println("The specified initial directory does not exist or is not a directory.");
        }

        // Set the extension filter for video files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Vidéos (*.mp4, *.avi, *.mov)", "*.mp4", "*.avi", "*.mov");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Stockez uniquement le chemin relatif ou le nom de la vidéo sélectionnée
            selectedVideoUrl = selectedFile.getName();
            Media media = new Media(new File(String.valueOf(selectedFile)).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            video.setMediaPlayer(mediaPlayer);


            // Ici, vous pouvez ajouter la logique pour utiliser la vidéo sélectionnée, comme la charger dans un lecteur multimédia.
        }
    }
    @FXML
    private void supprimerFormations() {
        // Vérifiez si l'ID du cours sélectionné n'est pas vide
        if (selectedIdF != null && !selectedIdF.isEmpty()) {
            // Convertissez l'ID en entier si nécessaire
            int id = Integer.parseInt(selectedIdF);

            // Appelez la méthode de service pour supprimer le cours avec l'ID sélectionné
            ServiceFormation sf = new ServiceFormation();
            try {
                sf.supprimer(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Mettez à jour la liste des cours après la suppression
            listF.clear();
            listF.addAll(getData());

            // Rafraîchissez l'affichage après la suppression
            grid.getChildren().clear(); // Effacez le contenu actuel du grid

            int column = 0;
            int row = 1;

            // Réinitialisez l'affichage avec la nouvelle liste de cours
            try {
                for (Formation formation : listF) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ItemF.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemF itemF = fxmlLoader.getController();

                    if (itemF != null) {
                        itemF.setData(formation, myListener);
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
                    String selecteIdO = String.valueOf(idF);
                    idFO.setText(selecteIdO);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Effacez les champs de la carte chosenFruitCard
            clearChosenCours();
        }
    }

    private void clearChosenCours() {
        idF.clear();
        nomF.clear();
        dateF.clear();
        dateD.clear();
        descripF.clear();
        prixF.clear();
        nbrCours.clear();
        nomF.setText("");
    }


    private void modifierFormation() {
        // Vérifiez si l'ID du cours sélectionné n'est pas vide
        if (selectedIdF != null && !selectedIdF.isEmpty()) {

            // Convertissez l'ID en entier si nécessaire
            int id = Integer.parseInt(selectedIdF);

            // Obtenez les nouvelles valeurs des champs de l'interface utilisateur
            String newName = nomF.getText();
            String newDateD = dateD.getText();
            String newDescription = descripF.getText();
            String newDateF = dateF.getText();
            String newPrix = prixF.getText();
            String newNbrCours = nbrCours.getText();

            ServiceFormation sp = new ServiceFormation();

            if (!sp.isValidPrix(Double.valueOf(newPrix)) && !sp.isValidDate(java.sql.Date.valueOf(newDateD), java.sql.Date.valueOf(newDateF))) {
                fxerrorprix1.setVisible(true);
                fxerrordate1.setVisible(true);
            } else if (!sp.isValidPrix(Double.parseDouble(prixF.getText()))) {
                fxerrorprix1.setVisible(true);
                fxerrordate1.setVisible(false);
            } else if (!sp.isValidDate(java.sql.Date.valueOf(newDateD), java.sql.Date.valueOf(newDateF))) {
                fxerrorprix1.setVisible(false);
                fxerrordate1.setVisible(true);
            } else {
                fxerrorprix1.setVisible(false);
                fxerrordate1.setVisible(false);
                try {
                    // Créez un nouvel objet Formation avec les valeurs mises à jour
                    Formation formation = new Formation();
                    formation.setIdFormation(id);
                    formation.setNom(newName);
                    formation.setDateDebut(java.sql.Date.valueOf(newDateD));
                    formation.setDateFin(java.sql.Date.valueOf(newDateF));
                    formation.setDescription(newDescription);
                    formation.setPrix(Double.parseDouble(newPrix));
                    formation.setNbrCours(Integer.parseInt(newNbrCours));
                    System.out.println(selectedVideoUrl);
                    if (selectedVideoUrl != null && !selectedVideoUrl.isEmpty()) {
                        String videoName = selectedVideoUrl.substring(selectedVideoUrl.lastIndexOf("/") + 1);
                        formation.setImageUrl(videoName); // Supposons que vous avez ajouté un champ pour l'URL de la vidéo
                    } else {
                        formation.setImageUrl(currentImageName); // Supposons que vous avez une variable pour le nom de la vidéo actuelle
                    }
                    // Appelez la méthode de service pour mettre à jour la formation
                    sp.modifier(formation);

                    // Mettez à jour l'affichage global
                    listF.clear();
                    listF.addAll(getData());

                    grid.getChildren().clear();
                    initialize(null, null); // Réinitialisez l'affichage

                } catch (Exception e) {
                    e.printStackTrace();
                    // Gérez les erreurs ici
                }
            }
        }
    }




    public void AjouterOffre() {
        // Vérifiez que tous les champs sont remplis
        if (dateDO.getValue() == null || dateFO.getValue() == null ||
                prixO.getText().isEmpty() || descripO.getText().isEmpty() ||
                selectedIdF.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
        } else {
            java.sql.Date dateDebut = java.sql.Date.valueOf(dateDO.getValue());
            java.sql.Date dateFin = java.sql.Date.valueOf(dateFO.getValue());
            ServiceOffre sp = new ServiceOffre();
            double nouveauPrix = Double.parseDouble(prixO.getText()); // Obtenez le nouveau prix de l'offre
            int idFormation = Integer.parseInt(selectedIdF); // Supposons que selectedIdF est l'ID de la formation sélectionnée

            try {
                // Vérifiez si une offre existe déjà pour cette formation
                if (sp.offreExistsForFormation(idFormation)) {
                    // Si une offre existe déjà, affichez un message d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Offre existante");
                    alert.setContentText("Une offre existe déjà pour cette formation.");
                    alert.show();
                } else {
                    // Si aucune offre n'existe, créez une nouvelle offre et ajoutez-la via votre service
                    Offre offre = new Offre(
                            Double.parseDouble(prixO.getText()),
                            descripO.getText(),
                            dateDebut,
                            dateFin,
                            Integer.parseInt(selectedIdF) // Assurez-vous que idFO a une valeur valide
                    );

                    sp.ajouter(offre);
                    refreshDisplayAfterOffer(getData()); // Rafraîchissez l'affichage avec le nouveau prix
                    // Mise à jour de l'interface utilisateur pour refléter le nouveau prix
                    // Affichez un message de succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setContentText("Offre ajoutée avec succès !");
                    alert.show();

                    tabPane.getSelectionModel().select(tabF);
                }
            } catch (SQLException e) {
                // Gérez les exceptions SQL ici
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur SQL");
                alert.setContentText("Une erreur est survenue : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }


    public void refreshDisplayAfterOffer( Set<Formation> listF) {

        // Clear existing items
        grid.getChildren().clear();

        // Reload data
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
    public void search(javafx.scene.input.KeyEvent keyEvent) {

        String text = chercherF_C.getText();
        // Efface le contenu actuel de la grille
        grid.getChildren().clear();

        if (chercherF_C.getText().isEmpty()) {
            refreshDisplayAfterOffer(getData());

        } else {
            // Si le champ de recherche n'est pas vide, effectuer une recherche dynamique par nom de formation
            // Récupère les formations correspondant au texte saisi
            Set<Formation> formations = null;
            try {
                formations = sf.rechercherFormationsParNom1(text);
                // Affiche les formations trouvées dans la grille
                refreshDisplayAfterOffer(formations);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }



        }
    }



}






