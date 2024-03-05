package edu.esprit.controllers;


import edu.esprit.entites.Formation;
import edu.esprit.entites.Outil;
import edu.esprit.entites.Reclamation;
import edu.esprit.entites.User;
import edu.esprit.services.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class AfficherReclamation {
    @FXML
    private Button bt;
    @FXML
    private TableView<Reclamation> table;

    @FXML
    private TableColumn<Reclamation, User> id_user;
    @FXML
    private TableColumn<Reclamation, Outil> id_outil;
    @FXML
    private TableColumn<Reclamation, Formation> id_formation;
    @FXML
    private TableColumn<Reclamation, String> description;
    @FXML
    private TableColumn<Reclamation, Object> date;

    private final ServiceReclamation ReclamationService = new ServiceReclamation();

    private void loadReclamations() {
        try {
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(ReclamationService.getAll());
            table.setItems(reclamations);
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

    }


  //  @FXML
    /*public void initialize() {
        id_user.setCellValueFactory(new PropertyValueFactory<>("user"));
        id_outil.setCellValueFactory(new PropertyValueFactory<>("outil"));
        id_formation.setCellValueFactory(new PropertyValueFactory<>("formation"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        // tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Initialiser le ComboBox avec les types de réclamations une seule fois
      /*  typeTF.getItems().clear(); // Assurez-vous qu'il est vide avant de le remplir
        typeTF.getItems().addAll("edu.esprit.controllers.Reclamation sur service", "edu.esprit.controllers.Reclamation sur security", "edu.esprit.controllers.Reclamation sur produit","edu.esprit.controllers.Reclamation sur livreur", "edu.esprit.controllers.Reclamation sur colis"); // Ajoutez vos types ici

        loadReclamations(); // Charge les réclamations dans le tableView
*/
      /*  tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                titreTF.setText(newSelection.getTitre());
                descriptionTF.setText(newSelection.getDescription());
                idUserTF.setText(String.valueOf(newSelection.getIduser()));

                // Sélectionnez le type de la réclamation dans le ComboBox, si nécessaire
                // Note: Vous aurez besoin de logique supplémentaire pour gérer cela correctement,
                // en fonction de la façon dont les types sont gérés dans votre application
            }
        });

*/
     //   loadReclamations();
        //table.refresh();
  //  }
  public void initialize() {
      id_user.setCellValueFactory(new PropertyValueFactory<>("user"));
      id_outil.setCellValueFactory(new PropertyValueFactory<>("outil"));
      id_formation.setCellValueFactory(new PropertyValueFactory<>("formation"));
      description.setCellValueFactory(new PropertyValueFactory<>("description"));
      date.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));

      // Create the new column for buttons
      TableColumn<Reclamation, Void> actionColumn = new TableColumn<>("Actions");
      actionColumn.setCellFactory(getButtonCellFactory());
      table.getColumns().add(actionColumn);

      loadReclamations();
      table.refresh();
  }

    private Callback<TableColumn<Reclamation, Void>, TableCell<Reclamation, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Reclamation, Void>, TableCell<Reclamation, Void>>() {
            @Override
            public TableCell<Reclamation, Void> call(final TableColumn<Reclamation, Void> param) {
                final TableCell<Reclamation, Void> cell = new TableCell<Reclamation, Void>() {
                    private final HBox hbox = new HBox();
                    private final Button deleteButton = new Button("Delete");
                    private final Button updateButton = new Button("Update");

                    {
                        deleteButton.setOnAction(event -> {
                            Reclamation data = getTableView().getItems().get(getIndex());
                            // Add your delete action here using the data object
                           ReclamationService.supprimer(data.getId_reclamation());
                            loadReclamations();
                            table.refresh();
                            System.out.println("Deleting: " + data);
                        });

                        updateButton.setOnAction(event -> {
                            Reclamation data = getTableView().getItems().get(getIndex());
                            // Add your update action here using the data object
                            System.out.println("Updating: " + data);
                        });

                        hbox.getChildren().addAll(deleteButton, updateButton);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        };
    }
}
