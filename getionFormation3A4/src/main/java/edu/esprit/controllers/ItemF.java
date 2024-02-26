package edu.esprit.controllers;


import edu.esprit.entities.Formation;
import edu.esprit.tests.MyListenerF;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class ItemF {
    @FXML
    private Label nomLabel;
    @FXML
    private Label prixLabel;

    @FXML
    private ImageView img;
    private Formation formation;
    private MyListenerF myListener;

    public ItemF() {
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListener(this.formation);
    }

    public void setData(Formation formation, MyListenerF myListener) {
        this.formation= formation;
        this.myListener = myListener;
        this.nomLabel.setText(formation.getNom());

        this.prixLabel.setText(String.valueOf( formation.getPrix()));
        /*Image image = new Image(this.getClass().getResourceAsStream(fruit.getImgSrc()));
        this.img.setImage(image);*/
    }
    public void updatePrixDisplay(double nouveauPrix) {
        // Créez un Text pour l'ancien prix et appliquez le style de barré en gris
        Text textAncienPrix = new Text(this.prixLabel.getText());
        textAncienPrix.setStyle("-fx-fill: grey; -fx-strikethrough: true;");

        // Créez un Text pour le nouveau prix sans barré
        Text textNouveauPrix = new Text(String.format(" %.2f €", nouveauPrix));
        textNouveauPrix.setStyle("-fx-fill: red; -fx-font-weight: bold; -fx-font-size: 18;"); // Adaptez le style comme vous le souhaitez

        // Utilisez TextFlow pour mettre les deux Text ensemble
        TextFlow textFlow = new TextFlow(textAncienPrix, textNouveauPrix);

        // Effacez le texte actuel du label et utilisez le TextFlow comme graphique
        this.prixLabel.setGraphic(textFlow);
        this.prixLabel.setText("");
    }


}


