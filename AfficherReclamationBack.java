package edu.esprit.controllers;
import edu.esprit.entites.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherReclamationBack implements Initializable {

    @FXML
    private Button ajouterReclamationButton;
    @FXML
    private GridPane reclamationContainer;
    @FXML
    private TextField searchBar;

    private final ServiceReclamation ReclamationService = new ServiceReclamation();
    private Set<Reclamation> Liste;
    @FXML
    private ScrollPane ScrolR;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterReclamations(newValue);
            }
        });

        updateReclamationList();
    }

    @FXML
    public void Refresh() {
        updateReclamationList();
    }

    @FXML
    public void updateReclamationList() {
        // Clear the existing UI elements
        reclamationContainer.getChildren().clear();

        Liste = new HashSet<>(reclamations());
        int column = 0;
        int row = 1;

        try {
            for (Reclamation reclamation : Liste) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Reclamation.fxml"));

                VBox reclamationBox = fxmlLoader.load();

                ReclamationController reclamationController = fxmlLoader.getController();
                reclamationController.setData(reclamation);

                // Set Reclamation as user data for the VBox
                reclamationBox.setUserData(reclamation);

                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(event -> {
                    handleDelete(reclamation, reclamationBox);
                });

                Button updateButton = new Button("update");
                updateButton.setOnAction(event -> {
                    handleupdate(reclamation);
                });

                reclamationBox.setOnMouseClicked(event -> handleReclamationClick(reclamationBox));

                HBox hbox = new HBox();
                hbox.getChildren().addAll(deleteButton);
                hbox.getChildren().addAll(updateButton);
                reclamationBox.getChildren().add(hbox);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                reclamationContainer.add(reclamationBox, column++, row);
                GridPane.setMargin(reclamationBox, new Insets(10));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void handleExportToExcel() {
        try {
            exportToExcel();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions or display an error message to the user
        }
    }

    private void exportToExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reclamations");

        // Add header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Description");
        headerRow.createCell(2).setCellValue("Date"); // Add more headers based on your Reclamation fields
        headerRow.createCell(3).setCellValue("User");
        headerRow.createCell(4).setCellValue("Formation");
        headerRow.createCell(5).setCellValue("Outil");
        // Add reclamations data
        int rowNum = 1;
        for (Reclamation reclamation : Liste) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(reclamation.getId_reclamation());
            row.createCell(1).setCellValue(reclamation.getDescription());
            row.createCell(2).setCellValue(reclamation.getDate_reclamation().toString());
            row.createCell(3).setCellValue(reclamation.getUser().getNom());
            row.createCell(4).setCellValue(reclamation.getFormation().getNom());
            row.createCell(5).setCellValue(reclamation.getOutil().getNom()); // Adjust this based on your date format
// Adjust this based on your date format
// Adjust this based on your date format
// Adjust this based on your date format
            // Add more data if needed
        }
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Save the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream("Reclamations.xlsx")) {
            workbook.write(fileOut);
        }

        // Close the workbook
        workbook.close();
    }

    private void handleupdate(Reclamation reclamation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamation.fxml"));
            Parent root = loader.load();

            ModifierReclamation modifierReclamationController = loader.getController();
            modifierReclamationController.setReclamationData(reclamation, this);

            Scene scene = new Scene(root);
            Stage modifierReclamationStage = new Stage();
            modifierReclamationStage.setScene(scene);
            modifierReclamationStage.setTitle("Modifier Reclamation");

            modifierReclamationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(Reclamation reclamation, VBox reclamationBox) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this reclamation?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ReclamationService.supprimer(reclamation.getId_reclamation());
                reclamationContainer.getChildren().remove(reclamationBox);
            }
        });
    }

    private Set<Reclamation> reclamations() {
        Set<Reclamation> listR = new HashSet<>();
        try {
            listR.addAll(ReclamationService.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listR;
    }

    @FXML
    private void handleReclamationClick(VBox clickedBox) {
        Reclamation selectedReclamation = getReclamationFromEventSource(clickedBox);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ReplyWindow.fxml"));
            VBox replyWindow = fxmlLoader.load();
            ReplyWindowController replyWindowController = fxmlLoader.getController();

            replyWindowController.setReclamationDetails(selectedReclamation);
            replyWindowController.setAssociatedReclamation(selectedReclamation);

            Stage stage = new Stage();
            stage.setTitle("Reclamation Details and Responses");
            stage.setScene(new Scene(replyWindow));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Reclamation getReclamationFromEventSource(VBox clickedBox) {
        if (clickedBox != null && clickedBox.getUserData() instanceof Reclamation) {
            return (Reclamation) clickedBox.getUserData();
        } else {
            return null;
        }
    }

    @FXML
    public void openAjouterReclamation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage ajouterReclamationStage = new Stage();
            ajouterReclamationStage.setScene(scene);
            ajouterReclamationStage.setTitle("Ajouter Reclamation");

            ajouterReclamationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterReclamations(String searchQuery) {
        reclamationContainer.getChildren().clear();

        ObservableSet<Reclamation> filteredReclamations = FXCollections.observableSet();
        for (Reclamation reclamation : Liste) {
            if (reclamation.getDescription().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredReclamations.add(reclamation);
            }
        }

        int column = 0;
        int row = 1;

        try {
            for (Reclamation reclamation : filteredReclamations) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Reclamation.fxml"));

                VBox reclamationBox = fxmlLoader.load();

                ReclamationController reclamationController = fxmlLoader.getController();
                reclamationController.setData(reclamation);

                reclamationBox.setUserData(reclamation);

                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(event -> {
                    handleDelete(reclamation, reclamationBox);
                });

                Button updateButton = new Button("update");
                updateButton.setOnAction(event -> {
                    handleupdate(reclamation);
                });

                reclamationBox.setOnMouseClicked(event -> handleReclamationClick(reclamationBox));

                HBox hbox = new HBox();
                hbox.getChildren().addAll(deleteButton);
                hbox.getChildren().addAll(updateButton);
                reclamationBox.getChildren().add(hbox);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                reclamationContainer.add(reclamationBox, column++, row);
                GridPane.setMargin(reclamationBox, new Insets(10));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

