package edu.esprit.controllers;


import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.tests.MyListenerF;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class ItemO {
    @FXML
    private Label descripLabel;
    @FXML
    private Label prixOLabel;

    @FXML
    private ImageView img;
    private Offre offre;
    private MyListenerF myListenerO;

    public ItemO() {
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListenerO.onClickListener1(this.offre);
    }

    public void setData1(Offre offre, MyListenerF myListenerO) {
        this.offre = offre;
        this.myListenerO = myListenerO;
        this.descripLabel.setText(offre.getDescription());

        this.prixOLabel.setText(String.valueOf(offre.getPrixOffre()));
        /*Image image = new Image(this.getClass().getResourceAsStream(fruit.getImgSrc()));
        this.img.setImage(image);*/
    }
}
