package edu.esprit.controllers;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;
import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminDisplay implements Initializable{
    @FXML
    private VBox container;
    @FXML
    private RadioButton fxclient;

    @FXML
    private RadioButton fxformateur;
    @FXML
    private TextField fxserarchText;

    @FXML
    private RadioButton fxtout;
    private final ServiceUser su =new ServiceUser();
    private List<User> lu=su.getAll().stream().filter((x)->!(x instanceof Admin)).collect(Collectors.toList());
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        VBox card1 = null;
        VBox card2 = null;
        for (int i = 0; i <lu.size(); i=i+2) {
            try {
                card1 = null;
                card2 = null;
                if (lu.get(i) instanceof Client c) {
                    FXMLLoader fxmlLoader1 = new FXMLLoader();
                    fxmlLoader1.setLocation(getClass().getResource("/clientCard.fxml"));
                    card1 = fxmlLoader1.load();
                    ClientCard uc1 = fxmlLoader1.getController();
                    uc1.setData(c);

                } else if (lu.get(i) instanceof Formateur f) {
                    FXMLLoader fxmlLoader1 = new FXMLLoader();
                    fxmlLoader1.setLocation(getClass().getResource("/formateurCard.fxml"));
                    card1 = fxmlLoader1.load();
                    FormateurCard uc2 = fxmlLoader1.getController();
                    uc2.setData(f);
                }

                if(i+1 != lu.size()){
                    if (lu.get(i+1) instanceof Client c){
                        FXMLLoader fxmlLoader2 = new FXMLLoader();
                        fxmlLoader2.setLocation(getClass().getResource("/clientCard.fxml"));
                        card2 = fxmlLoader2.load();
                        ClientCard uc1= fxmlLoader2.getController();
                        uc1.setData(c);

                    }else if (lu.get(i+1) instanceof Formateur f ){
                        FXMLLoader fxmlLoader2 = new FXMLLoader();
                        fxmlLoader2.setLocation(getClass().getResource("/formateurCard.fxml"));
                        card2 = fxmlLoader2.load();
                        FormateurCard uc2= fxmlLoader2.getController();
                        uc2.setData(f);
                    }
                }


                HBox cardContainer = new HBox();
                cardContainer.setSpacing(20);
                cardContainer.setPadding(new Insets(0, 20, 0, 20));
                cardContainer.getChildren().add(card1);
                if (card2!=null) cardContainer.getChildren().add(card2);

                container.getChildren().add(cardContainer);
                //
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    public void onlyClient(javafx.event.ActionEvent actionEvent) {
        if(!fxclient.isSelected()) fxclient.setSelected(true);

        fxformateur.setSelected(false);
        fxtout.setSelected(false);
        lu = su.getAll().stream().filter(x -> x instanceof Client).collect(Collectors.toList());
        container.getChildren().clear();
        initialize(null, null);
    }

    public void onlyFormateur(ActionEvent actionEvent) {
        if(!fxformateur.isSelected()) fxformateur.setSelected(true);

        fxclient.setSelected(false);
        fxtout.setSelected(false);
        lu = su.getAll().stream().filter(x -> x instanceof Formateur).collect(Collectors.toList());
        container.getChildren().clear();
        initialize(null, null);
    }

    public void affichageTotal(ActionEvent actionEvent) {
        if(!fxtout.isSelected()) fxtout.setSelected(true);

        fxclient.setSelected(false);
        fxformateur.setSelected(false);
        lu = su.getAll().stream().filter((x)->!(x instanceof Admin)).collect(Collectors.toList());
        container.getChildren().clear();
        initialize(null, null);
    }

    public void search(MouseEvent mouseEvent) {
        String role = null;
        if (fxtout.isSelected()) role = "all";
        else if (fxclient.isSelected()) role = "client";
        else role = "formateur";

        String text = fxserarchText.getText();
        List<User> result = su.rechercher(text,role);
        lu = result;
        container.getChildren().clear();
        initialize(null, null);
    }

    public void initContainer(KeyEvent keyEvent) {
        if(fxserarchText.getText().isEmpty()){
            if(fxtout.isSelected()) affichageTotal(null);
            else if(fxclient.isSelected()) onlyClient(null);
            else if(fxformateur.isSelected()) onlyFormateur(null);
        }
    }

    public void navigerAdmin(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profil3.fxml"));
            fxtout.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
            e.printStackTrace();
        }
    }
}
