package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AjoutClient implements Initializable {
    @FXML
    private ComboBox<String> fxgenre;

    @FXML
    private ComboBox<String> fxniveauscolaire;

    @FXML
    private Text fxns;

    @FXML
    private PasswordField fxpwd;

    @FXML
    private Text fxpwd2;
ServiceUser sp =new ServiceUser();
    private String imagePath;

    @FXML
    void ajouterphpto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une photo");
        File selectedFile = fileChooser.showOpenDialog(null);
        String imagepath=null;
        if (selectedFile != null)
            imagepath = selectedFile.getAbsolutePath();

         imagePath=imagepath;

    }

    @FXML
    void createAccount(ActionEvent event) throws NoSuchAlgorithmException {
       String[] data = getData();


       String a=fxniveauscolaire.getSelectionModel().getSelectedItem().toString();
       String g=fxgenre.getSelectionModel().getSelectedItem().toString();

       Client c = new Client(data[0],data[1],data[2],Date.valueOf(data[3]),data[4],Integer.parseInt(data[5]),hashof(fxpwd.getText()),imagePath,g,a);
       sp.ajouter(c);
        if (ServiceUser.codeAjout == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR !!");
            alert.setContentText("Email ou mot de passe est utilisé déja !");
            alert.showAndWait();
            ServiceUser.codeAjout = 0;
        }else{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/menuApplication.fxml"));
                fxgenre.getScene().setRoot(root);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sorry");
                alert.setTitle("Error");
                alert.show();
            }
            storeSession(sp.getIdByEmail(data[2]));
        }

    }

    private String[] getData() {
        String[] data=null;
        try {
            File file = new File("src/main/resources/fichiers/cache.txt");
            Scanner myReader = new Scanner(file);
            if (myReader.hasNextLine()) {
                String filecontent = myReader.nextLine();
                data =filecontent.split("&&");
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list= FXCollections.observableArrayList("ecole","college","lycee");
        fxniveauscolaire.setItems(list);
        ObservableList<String> list2= FXCollections.observableArrayList("Homme","Femme","Autre");
        fxgenre.setItems(list2);

    }

    public String hashof(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte[] hash = md.digest();
        return new String(hash);
    }

    public void storeSession(int id){
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/fichiers/session.txt");
            myWriter.write(String.valueOf(id));
            myWriter.close();
            System.out.println("Successfully wrote to the session file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
