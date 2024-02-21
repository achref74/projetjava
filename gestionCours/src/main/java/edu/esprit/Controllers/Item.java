package edu.esprit.Controllers;

import edu.esprit.entities.Cours;
import edu.esprit.tests.MainFx;
import javafx.fxml.FXML;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import java.awt.*;


public class Item {



    @FXML
    private Label courseDescription;

    @FXML
    private ImageView courseImage;

    @FXML
    private Label courseName;
    private Cours cours ;
    /*public void setData(Cours cours){
this.cours =cours ;
        courseName.setText(cours.getNom());
        courseDescription.setText(MainFx.CURRENCY + cours.getDescrption());
        Image image = new Image(getClass().getResourceAsStream(cours.getImgSource()));
        courseImage.setImage(image);
    }*/

}
