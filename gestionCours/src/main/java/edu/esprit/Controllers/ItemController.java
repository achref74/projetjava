package edu.esprit.Controllers;


import edu.esprit.entities.Cours;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;


public class ItemController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLable;
    @FXML
    private ImageView img;
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
            String imagePath = "/images/" + cours.getImage();// Utilisation d'un chemin relatif
            URL url = getClass().getResource(imagePath);
            if (url != null) {
                Image image = new Image(url.toExternalForm());
                this.img.setImage(image);
            } else {
                System.err.println("Impossible de charger l'image : " + imagePath);
                // Afficher une image par défaut ou un message d'erreur
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            e.printStackTrace();
        }
    }
    public Cours getCours() {
        return this.cours;
    }
}
