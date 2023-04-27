package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;
/**Add Product Controller. Controller connected to the FXML display rendered when adding a new product object.*/
public class AddProduct implements Initializable {
    public TextField nameField;
    public TextField inventoryField;
    public TextField costField;
    public TextField maxField;
    public TextField minField;
    public Button addButton;
    public Button removeAssociatedPart;
    public Button saveButton;
    public Button cancelButton;
    public TextField partSearch;
    public TableView partsTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partCostCol;
    public TableView associatedPartsTable;
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TableColumn asscPartIDCol;
    public TableColumn asscPartNameCol;
    public TableColumn asscPartInvCol;
    public TableColumn asscPartCostCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(allParts);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    /**Cancel Button. Returns the user to the main screen without creating a new part object.*/
    public void onCancelButton(ActionEvent actionEvent)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Inventory.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
/**Add Button. A user may select a part from the All parts table at the top of the window, and click Add. This action will populate the selected part in the lower table for Associated parts, which will be saved as an associated part for the product if saved.*/
    public void onAddButton(ActionEvent actionEvent) {
      Part targetPart = (Part) partsTable.getSelectionModel().getSelectedItem();

      if (partsTable.getSelectionModel().getSelectedItem() != null) {
          associatedParts.add(targetPart);
          associatedPartsTable.setItems(associatedParts);
          asscPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
          asscPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
          asscPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
          asscPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
      }
      else {
          Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Part to add.");
          alert.showAndWait();
      }
    }
/**Remove Associated Part Button. A user may select a part from the Associated parts table at the bottom of the window, and click Remove Associated Part. This action will remove the selected part from the lower table, and will no longer be associated with the product if saved.*/
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Click OK to remove selected Part.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part targetPart = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
            associatedParts.remove(targetPart);
            associatedPartsTable.refresh();
            }
    }
    /**Save Button. If the user has info that meets the object requirements(stock between max and min values, all fields filled with correct data types), a new product object will be created and added to the All Products List.*/
    public void onSaveButton(ActionEvent actionEvent) throws  IOException{
        try {
            int id = Inventory.uniqueIDGenProduct();
            String name = nameField.getText();
            int stock = Integer.parseInt(inventoryField.getText());
            double price = Double.parseDouble(costField.getText()); //Needs to be combined cost of assc parts
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());
            ObservableList<Part> associatedParts = associatedPartsTable.getItems();

            if (max <= min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be higher than Minimum value.");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between the Minimum and Maximum values.");
                alert.showAndWait();
            } else if (stock <= max && stock >= min && max > min) {

                Product newProduct = new Product(id, name, price, stock, min, max, associatedParts);
                Inventory.addProduct(newProduct);

                Parent root = FXMLLoader.load(getClass().getResource("/view/Inventory.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid value for each textfield.");
            alert.showAndWait();
        }
    }
    /**Part Search. User may input a query in the textfield above the displayed Parts list and press enter. The parts table will display any parts matching the query or a popup box will appear and inform the user that no matching parts were found.*/
    public void onPartSearch(ActionEvent actionEvent) {
        try {
            String q = partSearch.getText();

            ObservableList<Part> searchedParts = lookupPart(q);

            if (searchedParts.size() == 0) {
                int partID = Integer.parseInt(q);
                Part p = lookupPart(partID);
                if (p != null)
                    searchedParts.add(p);
                if (p == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No Parts found!");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent()){
                        partsTable.setItems(allParts);
                    }
                }
            }
        partsTable.setItems(searchedParts);
        partSearch.setText("");
    }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Parts found!");
            alert.showAndWait();
        }
    }
}
