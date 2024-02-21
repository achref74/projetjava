package edu.esprit.controllers;

import edu.esprit.entities.Formation;
import edu.esprit.entities.Forum;
import edu.esprit.services.ServiceForum;
import edu.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AjouterForum {

    @FXML
    private Button AnnulerForum;

    @FXML
    private ComboBox<String> ComboboxForum;

    @FXML
    private TextArea DescriptionForum;

    @FXML
    private TextField TitreForum;

    @FXML
    private Button ajouterFormation;

    @FXML
    private Button btnCours;

    @FXML
    private Button btnFormation;

    @FXML
    private Button btnForum;

    @FXML
    private Button btnOutils;

    @FXML
    private Button btnReclamation;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnUser;

    @FXML
    private Pane pnlOverview;
    private final ServiceForum serviceForum=new ServiceForum();
    private DataSource MyConnection;
    private static int getSubstringBeforeDash(String input) {
        int dashIndex = input.indexOf("--");
        if (dashIndex != -1) {
            return Integer.valueOf(input.substring(0, dashIndex));
        } else {
            // Si les tirets ne sont pas présents, retourner la chaîne d'origine
            return Integer.valueOf(input);
        }
    }


    public void InterfaceForumAction(ActionEvent actionEvent) {
    }


    public void AjouterForum(ActionEvent actionEvent) {
        try {
            Formation formation=new Formation();


            String type = (String) ComboboxForum.getValue();
            int idFormation=getSubstringBeforeDash(type);
            formation.setIdFormation(idFormation);

            serviceForum.ajouter(new Forum(TitreForum.getText(),DescriptionForum.getText(),formation));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("GG");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void AnnulerForum(ActionEvent actionEvent) {
        TitreForum.clear();
        DescriptionForum.clear();
    }

    @FXML
    void initialize() {
        // Fetch the values from the database
        List<String> types = new ArrayList<>();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery("SELECT idFormation, nom FROM formation");
            while (rs.next()) {
                int idFormation=rs.getInt("idFormation");
                String id = String.valueOf(idFormation);
                String nomFormation= rs.getString("nom");
                String delimiteur="--";
                String idNomFormation=id+delimiteur+nomFormation;
                types.add(idNomFormation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Populate the TypeChoiceBox with the fetched values
        ObservableList<String> observableTypes = FXCollections.observableArrayList(types);
        ComboboxForum.setItems(observableTypes);
    }






}
