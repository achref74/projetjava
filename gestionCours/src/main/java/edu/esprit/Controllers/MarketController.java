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
import javafx.stage.FileChooser;



import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MarketController implements Initializable {
    @FXML
    private Label msg ;
    @FXML
    private Label t1 ;
    @FXML
    private Label t2 ;

    @FXML
    private ComboBox<String> tri;
    @FXML
    private TextField searchField;


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
    @FXML
    private Button ajouterC;

    private String selectedVideoUrl;

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
    private Button ajouter;
    @FXML
    private Button evaluation;
    private MyListener myListener;
    private String selectedId ;
    @FXML
    private MediaView video;
    @FXML
    private Button playPauseButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider progressSlider;

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
   private  Cours cours = new Cours();

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
       msg.setVisible(false);
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
        setupSearchField();
        refreshGrid(liste);

        modifier.setOnAction(event -> modifierCours());
        supprimer.setOnAction(event -> supprimerCours());
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
            refreshGrid(liste);
            return;
        }
        refreshGrid(sortedCours);
    }
    private void refreshGrid(Set<Cours> courses) {
        grid.getChildren().clear();
        int column = 0;
        int row = 1;
        for (Cours cours : courses) {
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
                    grid.add(anchorPane, column++, row);
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                Set<Cours> filteredCourses = new HashSet<>();
                for (Cours cours : liste) {
                    if (cours.getNom().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredCourses.add(cours);
                    }
                }
                refreshGrid(filteredCourses);
            } else {
                refreshGrid(liste);
            }
        });
    }
    @FXML
    private void supprimerCours() {

        if (selectedId != null && !selectedId.isEmpty()) {
            // Convertissez l'ID en entier si nécessaire
            int id = Integer.parseInt(selectedId);

            ServiceCours serviceCours = new ServiceCours();
            serviceCours.supprimer(id);

            liste.clear();
            liste.addAll(getData());
            grid.getChildren().clear();
            refreshGrid();

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
        fruitNameLable.setText(""); // Optionnel : vous pouvez également le supprimer si vous avez déjà effacé le champ fruitNameLabel ci-dessus.
    }

////

    private String currentImageName;
    private void modifierCours() {
        if (selectedId != null && !selectedId.isEmpty()) {
            int id = Integer.parseInt(selectedId);
            String newName = fruitNameLable.getText();
            String newDate = date.getText();
            String newDescription = description.getText();
            String newDuree = duree.getText();
            String newPrerequis = prerequis.getText();
            String newRessource = ressource.getText();


            try {
                Cours cours = new Cours();
                cours.setId_cours(id);
                cours.setNom(newName);
                cours.setDate(java.sql.Date.valueOf(newDate));
                cours.setDescrption(newDescription);

                String[] parts = newDuree.split("\\s+");
                int heures = Integer.parseInt(parts[0].replace("h", ""));
                int minutes = Integer.parseInt(parts[1].replace("min", ""));
                int secondes = Integer.parseInt(parts[2].replace("sc", ""));

                if (heures < 0 || minutes < 0 || secondes < 0) {

                    msg.setText("valeur<0");
                    msg.setVisible(true);
                    return;
                }

                String regex = "\\d+h\\s*\\d+min\\s*\\d+sc";
                if (!newDuree.matches(regex)) {
                    System.err.println("La durée doit être au format 'xh ymin zsc'.");
                    msg.setText("La durée doit être au format 'xh ymin zsc'.");
                    msg.setVisible(true);
                    return;
                }

                int totalSeconds = heures * 3600 + minutes * 60 + secondes;
                cours.setDuree(totalSeconds);

                cours.setPrerequis(newPrerequis);
                cours.setRessource(newRessource);
                if (selectedImageURL != null && !selectedImageURL.isEmpty()) {
                    String imageName = selectedImageURL.substring(selectedImageURL.lastIndexOf("/") + 1);
                    cours.setImage(imageName);
                } else {
                    cours.setImage(currentImageName);
                }

                ServiceCours serviceCours = new ServiceCours();
                serviceCours.modifier(cours);
                 clearChosenCours();
                 msg.setVisible(false);
                liste.clear();
                liste.addAll(getData());
                grid.getChildren().clear();
                refreshGrid();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshGrid() {
        grid.getChildren().clear();
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
                    grid.add(anchorPane, column++, row);
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







    public void navigatetoEvaluationEnTantQueFormateur(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvaluationFormateur.fxml"));
            Parent root = loader.load();

            AfficherEFormateur controller = loader.getController();

            controller.setCoursId(selectedId);

            evaluationf.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void redirectetoCours(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCours.fxml"));
            Parent root = loader.load();

            // Obtenez la scène à partir de la source de l'événement
            Scene scene = ajouterC.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Désolé, une erreur est survenue lors de la redirection.");
            alert.setTitle("Erreur");
            alert.show();
        }
    }

    @FXML
    void AjouterEvaluation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvaluation.fxml"));
            Parent root = loader.load();

            AjouterEController controller = loader.getController();

            controller.setCoursId(selectedId);

            // Changer la scène vers le contrôleur EvaluationController
            ajouter.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs ici
        }
    }



}

