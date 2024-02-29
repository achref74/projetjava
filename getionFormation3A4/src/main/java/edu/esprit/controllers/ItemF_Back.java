package edu.esprit.controllers;

import edu.esprit.entities.Certificat;
import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.tests.MyListenerF;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemF_Back {
    @FXML
    private Label descripLabel;
    @FXML
    private Label prixLabel;

    @FXML
    private Label nomLabel;
    @FXML
    private Label dateDLabel;
    @FXML
    private Label dateFLabel;
    @FXML
    private Label nbrCLabel;
    private Formation formation;
    private Offre offre;
    private Certificat certificat;
    private MyListenerF myListener1;

    public ItemF_Back() {
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener1.onClickListener2(this.formation);
    }

    public void setData2(Formation formation, MyListenerF myListener1) {
        this.formation = formation;
        this.myListener1= myListener1;
        this.descripLabel.setText(formation.getDescription());
        this.nomLabel.setText(formation.getNom());
        this.dateDLabel.setText(String.valueOf(formation.getDateDebut()));
        this.dateFLabel.setText(String.valueOf(formation.getDateFin()));
        this.nbrCLabel.setText(String.valueOf(formation.getNbrCours()));
        this.prixLabel.setText(String.valueOf(formation.getPrix()));
        /*Image image = new Image(this.getClass().getResourceAsStream(fruit.getImgSrc()));
        this.img.setImage(image);*/
    }
    public void setData3(Offre offre, MyListenerF myListener1) {
        this.offre = offre;
        this.myListener1= myListener1;
        this.nomLabel.setText(String.valueOf(offre.getPrixOffre()));

        this.descripLabel.setText(offre.getDescription());
        this.dateDLabel.setText(String.valueOf(offre.getDateD()));
        this.dateFLabel.setText(String.valueOf(offre.getDateF()));

        /*Image image = new Image(this.getClass().getResourceAsStream(fruit.getImgSrc()));
        this.img.setImage(image);*/
    }
    public void setData4(Certificat certificat, MyListenerF myListener1) {
        this.certificat = certificat;
        this.myListener1= myListener1;
        this.nomLabel.setText(certificat.getTitre());

        this.descripLabel.setText(certificat.getDescription());
        this.dateDLabel.setText(String.valueOf(certificat.getDateObtention()));
        this.dateFLabel.setText(String.valueOf(certificat.getNbrCours()));

        /*Image image = new Image(this.getClass().getResourceAsStream(fruit.getImgSrc()));
        this.img.setImage(image);*/
    }
}
