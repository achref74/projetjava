package edu.esprit.Controllers;


import edu.esprit.entities.Cours;
import edu.esprit.tests.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


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
        this.cours= cours;
        this.myListener = myListener;
        this.nameLabel.setText(cours.getNom());
        this.priceLable.setText("$" + cours.getDuree());
        /*Image image = new Image(this.getClass().getResourceAsStream(fruit.getImgSrc()));
        this.img.setImage(image);*/
    }
}
