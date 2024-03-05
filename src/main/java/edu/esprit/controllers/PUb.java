package edu.esprit.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import edu.esprit.entities.Forum;
import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceForum;
import edu.esprit.services.ServicePublication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class PUb implements Initializable {


    private String pathImage;
    @FXML
    private Pane Photo;
    @FXML
    private TextArea ContenuPublication;
    @FXML
    private TextField idUser;

    @FXML
    private TextField idforum;
    @FXML
    private Text idForumBZi;
    private Timeline timeline;
    @FXML
    private Text nblikMax;
    private final ServiceForum serviceForum=new ServiceForum();

    private final ServicePublication servicePublication =new ServicePublication();


    @FXML
    private TextField nomFormPubR;
    @FXML
    private Text nbPUB;



    @FXML
    void AjouterPubliction(ActionEvent event) {
        try {
            Forum forum=new Forum();
            int idForum = Integer.parseInt(idforum.getText());
            forum.setIdForum(idForum);
            User user=new User();
            int iduser = Integer.parseInt(idUser.getText());
            user.setIdUser(iduser);
            servicePublication.ajouter(new Publication(ContenuPublication.getText(),pathImage,forum,user));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("GG");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void importerImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Obtenez le chemin du répertoire des ressources
        String currentDir = System.getProperty("user.dir");
        String resourceDir = currentDir + "/src/main/resources/PubImages";

        // Définissez le répertoire initial du FileChooser
        File initialDirectory = new File(resourceDir);
        fileChooser.setInitialDirectory(initialDirectory);

        fileChooser.setTitle("Sélectionner une image");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();

            // Extraire la partie après "resources/images"
            String relativePath = imagePath.substring(resourceDir.length() + 1);

            pathImage=imagePath;
            ImageView imageView = creerImageView(relativePath);
            Photo.getChildren().add(imageView);


        }


    }


    public ImageView creerImageView(String cheminImage) {
        System.out.println(cheminImage);
        ImageView imageView = new ImageView();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/PubImages/" + cheminImage);
            if (inputStream != null) {
                Image image = new Image(inputStream);
                imageView.setImage(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(200);
                imageView.setStyle("-fx-border-color: #FF0000FF;  -fx-border-width: 5;");
            } else {
                System.err.println("Le fichier " + cheminImage + " n'a pas pu être chargé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateIdForum));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        List<Publication> publications = null;
        try {
            publications = servicePublication.getAll();
            System.out.println(publications);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int idForumToCount =42;

        long repetitions = countRepetitionsOfIdForum(publications, idForumToCount);
        System.out.println("Le nombre de répétitions de l'idForum " + idForumToCount + " est : " + repetitions);
        String nomForumToCount ="chama9ma9";
        long repetitionsOfNomForum =countRepetitionsOfNomForum(publications, nomForumToCount);
        System.out.println("Le nombre de répétitions de Forum " + nomForumToCount + " est : " + repetitionsOfNomForum);
       long x= countPublications(publications);
        System.out.println(x);
        System.out.println("bbbbbbbbbbb");
       if (x>10){
           String adminPhoneNumber = "+21651784706"; // Replace with the admin's phone number
           String message = "Les publications attendent la limite.";
           sendSmsNotification(adminPhoneNumber, message);
       }





    }
    private void updateIdForum(ActionEvent event) {
        int idForum = refreshUIidForum();
        System.out.println(idForum);

        try {
            Publication publicationWithMostLikes=findPublicationWithMostLikes(servicePublication.getAll());
            nblikMax.setText(publicationWithMostLikes.getForum().getTitre()+publicationWithMostLikes.getContenu()+publicationWithMostLikes.getNbLike());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Forum forum= serviceForum.getOneById(idForum);
            idForumBZi.setText("zibest Forum :"+forum.getTitre());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int findMostRepeatedIdForum(List<Publication> publications) {
        Map<Integer, Long> idForumCounts = publications.stream()
                .collect(Collectors.groupingBy(pub -> pub.getForum().getIdForum(), Collectors.counting()));

        return idForumCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }
    private int refreshUIidForum() {
        try {
            List<Publication> publications = servicePublication.getAll();
            int idForum = findMostRepeatedIdForum(publications);
            return idForum;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Publication findPublicationWithMostLikes(List<Publication> publications) {
        Optional<Publication> publicationWithMostLikes = publications.stream()
                .max(Comparator.comparingInt(Publication::getNbLike));
        System.out.println("+++++");

        return publicationWithMostLikes.orElse(null);
    }
    public static long countRepetitionsOfIdForum(List<Publication> publications, int idForumToCount) {
        return publications.stream()
                .filter(pub -> pub.getForum().getIdForum() == idForumToCount)
                .count();
    }
    public static long countRepetitionsOfNomForum(List<Publication> publications, String nomForumToCount) {
        return publications.stream()
                .filter(pub -> pub.getForum().getTitre().equals(nomForumToCount))
                .count();
    }


    @FXML
    void combienub(ActionEvent event) {
        List<Publication> publications ;
        try {
            publications = servicePublication.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String nomForumToCount =nomFormPubR.getText();
        long repetitionsOfNomForum =countRepetitionsOfNomForum(publications, nomForumToCount);
        nbPUB.setText("contien "+String.valueOf(repetitionsOfNomForum)+" publictions");
    }

    public static long countPublications(List<Publication> publications) {
        return publications.size();
    }
    // Your Twilio credentials
    private static final String ACCOUNT_SID = "AC3adec15e66b733a2e5bd6860ee424a97";
    private static final String AUTH_TOKEN = "327fd0cfd0e9c9bee18845bdb8e5ca89";
    private static final String TWILIO_PHONE_NUMBER = "+19497103983";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSmsNotification(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),  // To phone number
                new PhoneNumber(TWILIO_PHONE_NUMBER),  // From Twilio phone number
                messageBody
        ).create();

        System.out.println("Sent message with ID: " + message.getSid());
    }




    // Construct the SMS message to include the title and type





}


