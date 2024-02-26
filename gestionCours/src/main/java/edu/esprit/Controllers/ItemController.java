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
        long minutes = (cours.getDuree()% 3600) / 60;
        long secondes = cours.getDuree() % 60;
        this.priceLable.setText("" + heures +"h "+minutes +"min "+secondes +"sc");
        try {


            String imagePath = "file:///C:/Users/LENOVO/Desktop/gestionCours/src/main/resources/images/" + cours.getImage();
            URL url = new URL(imagePath);
            Image image = new Image(url.openStream());
            this.img.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Cours getCours() {
        return this.cours;
    }
}
