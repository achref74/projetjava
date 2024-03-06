package edu.esprit.Controllers;


import edu.esprit.entities.Cours;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.net.URL;


public class ItemController {

    @FXML
    private MediaView img;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLable;

    private Cours cours;
    private MyListener myListener;

    public ItemController() {
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListener(this.cours);
    }

    public void setData(Cours cours, MyListener myListener) {
        this.cours = cours;
        this.myListener = myListener;
        this.nameLabel.setText(cours.getNom());

        long heures = cours.getDuree() / 3600;
        long minutes = (cours.getDuree() % 3600) / 60;
        long secondes = cours.getDuree() % 60;
        this.priceLable.setText(heures + "h " + minutes + "min " + secondes + "sc");

        try {
            String imagePath = "/videos/" + cours.getImage();

            URL url = getClass().getResource(imagePath);
            if (url != null) {

                Media media = new Media(url.toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                this.img.setMediaPlayer(mediaPlayer);
            } else {
                System.err.println("Impossible de charger la video  : " + imagePath);
                // Afficher une image par défaut ou un message d'erreur
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la video  : " + e.getMessage());
            e.printStackTrace();
        }
    }
    public Cours getCours() {
        return this.cours;
    }
}
