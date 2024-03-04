package edu.esprit.Controllers;

import edu.esprit.entities.Evaluation;
import edu.esprit.services.ServiceEvaluation;
import edu.esprit.tests.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

public class Back2Controller implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private Set<Evaluation> liste = new HashSet<>();

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    private MyListener myListener;
    private String selectedId ;


    private Set<Evaluation> getData() {
        Set<Evaluation> liste = new HashSet<>();
        ServiceEvaluation serviceCours =new ServiceEvaluation();
        liste=serviceCours.getAll();
        return liste;
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        liste.addAll(getData());
        if (!liste.isEmpty()) {
            Iterator<Evaluation> iterator = liste.iterator();
            Evaluation firstCours = iterator.next();




        }
        int column = 0;
        int row = 1;
        try {


            int i = 0;
            for (Evaluation evaluation : liste) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Evaluation.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                EvaluationController evaluationController = fxmlLoader.getController();


                if  (evaluationController != null) {

                    evaluationController.setData(evaluation, myListener);
                } else  System.err.println("Evaluation Controller  est null");
                i++;
                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    private Button back;
    public void back(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Back3.fxml"));
            back.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

////








}

