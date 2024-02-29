package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class Login {
    @FXML
    public Label errormsg;
    @FXML
    private TextField f1;

    @FXML
    private TextField f2;


    private void sendEmailWithOTP(String toEmail, String otp) {
        final String username = "connectartisan6@gmail.com"; // Your email
        final String password = "gmbm pdin iwvu mkgy"; // Your email password
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("connectartisan6@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Login OTP");
            message.setText("Your OTP for login is: " + otp);
            Transport.send(message);
            System.out.println("OTP email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateOTP() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return sb.toString();
    }

    @FXML
    private void navigateafficher(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        String mail = f1.getText();
        String pass = f2.getText();
        ServiceUser s = new ServiceUser();
        errormsg.setText("");

        if (pass.isEmpty() || mail.isEmpty()) {
            // Error handling for empty fields
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR !!");
            alert.setContentText("PLEASE FILL ALL DATA");
            alert.showAndWait();
        } else {
            String accountType = s.login(mail, pass);
            if(accountType.equals("Admin")){
                loadScene("/menuAdmin.fxml",event);
            }
           else if (accountType.equals("client") || accountType.equals("Formateur") ) {
                String otp = generateOTP();
                sendEmailWithOTP(mail, otp);
                errormsg.setVisible(false);

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("OTP Verification");
                dialog.setHeaderText(null);
                dialog.setContentText("Enter your OTP:");
                Optional<String> result = dialog.showAndWait();

                if (result.isPresent() && result.get().equals(otp)) {
                    // Proceed with navigation after successful OTP verification
                    String fxmlPath = accountType.equals("Admin") ? "/menuAdmin.fxml" : "/menuApplication.fxml";
                    Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                    f1.getScene().setRoot(root);
                    storeSession(s.getIdByEmail(mail));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Incorrect OTP. Please try again.");
                    alert.showAndWait();
                }
            } else {
                errormsg.setText("User name or password is incorrect");
            }
        }
    }


    public void navigatesinscrire(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/creeCompte.fxml"));
            f1.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
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
    private void loadScene(String fxmlPath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
