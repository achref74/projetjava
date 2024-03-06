package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.entities.Question;
import edu.esprit.services.ServiceCours;
import edu.esprit.services.ServiceEvaluation;
import edu.esprit.tests.MyListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
   @FXML
    private TextField searchField;

    @FXML
    private ScrollPane scroll;
    private String selectedVideoUrl;
    @FXML
    private MediaView video;
    @FXML
    private Button playPauseButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider progressSlider;

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
    @FXML
    private ComboBox<String> tri;

    @FXML
    private Label t1;

    @FXML
    private Label t2;

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

        String videoFile = "C:\\projetPIDEV\\gestionCours\\src\\main\\resources\\videos\\" + cours.getImage();
        currentImageName = cours.getImage();

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

    @FXML
    void selectVideo(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une vidéo");

        // Ensure the initial directory exists
        File initialDirectory = new File("C:\\projetPIDEV\\gestionCours\\src\\main\\resources\\videos\\");
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
    private  Cours cours = new Cours();
    private String selectedImageURL;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServiceCours s =new ServiceCours();
        Cours coursAvecDureeMinimale = s.getCoursAvecDureeMinimale();
        if (coursAvecDureeMinimale != null) {
            // Convertir la durée en heures, minutes et secondes
            int dureeEnSecondes = coursAvecDureeMinimale.getDuree();
            int heures = dureeEnSecondes / 3600; // 3600 secondes dans une heure
            int minutes = (dureeEnSecondes % 3600) / 60; // Le reste après avoir calculé les heures
            int secondes = dureeEnSecondes % 60; // Le reste après avoir calculé les minutes

            // Mettre à jour le contenu du champ t1 avec la durée formatée
            t1.setText(String.format("Terminez en seulement %d h %d min %d s", heures, minutes, secondes));
            t2.setText("le fameux  "+coursAvecDureeMinimale.getNom()+"magique");
        }
        liste.addAll(getData());
        if (!liste.isEmpty()) {
            for (Cours cours : liste) {
                LocalDate dateCours = Instant.ofEpochMilli(cours.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
               /* if (dateCours.equals(LocalDate.now())) {
                     Envoyer l'e-mail avec le nom du cours
                    sendEmail(cours.getNom());

                }*/
            }

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

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Mettre à jour la liste des cours en fonction du texte de recherche
            Set<Cours> filteredList = liste.stream()
                    .filter(cours -> cours.getNom().toLowerCase().contains(newValue.toLowerCase()))
                    .collect(Collectors.toSet());
            afficherCours(filteredList);
        });
        ObservableList<String> options = FXCollections.observableArrayList(
                "Cours les plus courts", "Cours les plus longs","Cours les plus anciens","Cours les plus récents","aucun");
        tri.setItems(options);

        tri.setOnAction(this::handleTriSelection);
    }
    @FXML
    private void handleTriSelection(ActionEvent event) {
        String selectedTri = tri.getValue();
        Set<Cours> sortedCours;
        if (selectedTri.equals("Cours les plus courts")) {
            // Triez les cours par durée (les plus courts d'abord)
            sortedCours = liste.stream()
                    .sorted(Comparator.comparingInt(Cours::getDuree))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else if (selectedTri.equals("Cours les plus longs")) {
            // Triez les cours par durée (les plus longs d'abord)
            sortedCours = liste.stream()
                    .sorted(Comparator.comparingInt(Cours::getDuree).reversed())
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else if (selectedTri.equals("Cours les plus récents")) {
            // Triez les cours par date (les plus récents d'abord)
            sortedCours = liste.stream()
                    .sorted(Comparator.comparing(Cours::getDate).reversed())
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else if (selectedTri.equals("Cours les plus anciens")) {
            // Triez les cours par date (les plus anciens d'abord)
            sortedCours = liste.stream()
                    .sorted(Comparator.comparing(Cours::getDate))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else {
            afficherCours(liste);
            return;
        }
        afficherCours(sortedCours);
    }
    private void afficherCours(Set<Cours> coursList) {
        grid.getChildren().clear(); // Effacer les cours précédents

        int column = 0;
        int row = 1;
        try {
            int i = 0;
            for (Cours cours : coursList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();

                if (itemController != null) {
                    itemController.setData(cours, myListener);
                } else {
                    System.err.println("itemController est null");
                }
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
    private void sendEmail(String coursNom) {
        // Paramètres SMTP
        String host = "smtp.gmail.com";
        int port = 587; // Port pour Gmail
        String username = "loudjeinbouguerra@gmail.com";
        String password = "hhud wtnc tnft oowa";

        try {
            // Création de l'e-mail
            HtmlEmail email = new HtmlEmail();
            email.setHostName(host);
            email.setSmtpPort(port);
            email.setAuthenticator(new DefaultAuthenticator(username, password));
            email.setStartTLSEnabled(true);
            email.setFrom(username);
            email.addTo("yasminebousselmi5t@gmail.com");
            email.setSubject("Nouveau cours disponible : " + coursNom);
            email.setMsg("Le cours " + coursNom + " est maintenant disponible.");

            // Envoi de l'e-mail
            email.send();

            System.out.println("E-mail envoyé avec succès.");

        } catch (EmailException e) {
            e.printStackTrace();
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

