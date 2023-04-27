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

import static model.Inventory.allParts;
import static model.Inventory.lookupPart;
/**Modify Product Controller. Controller connected to the FXML display rendered when modifying an existing product object.*/
public class ModifyProduct implements Initializable {
    public TextField idField;
    public TextField nameField;
    public TextField inventoryField;
    public TextField costField;
    public TextField maxField;
    public TextField minField;
    public Button addButton;
    public Button removeAssociatedPart;
    public Button saveButton;
    public Button cancelButton;
    public TableColumn partIDCol;
    public TableView partsTable;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partCostCol;
    public TableView associatedPartsTable;
    public TableColumn asscPartIDCol;
    public TableColumn asscPartNameCol;
    public TableColumn asscPartInvCol;
    public TableColumn asscPartCostCol;

    /**Target Product variable. Private data member used to pass the selected product info from the All Products list on the main screen to the Modify Product Screen.*/
    private static Product targetProduct = null;
    public TextField partSearch;
/**Modify Product Method. Assigns the selected product from the main screen to the Target Product variable.*/
    public static void modifyProduct(Product product){targetProduct = product;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(allParts);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        targetProductData();

    }
    /**Add Button. A user may select a part from the All parts table at the top of the window, and click Add. This action will populate the selected part in the lower table for Associated parts, which will be saved as an associated part for the product if saved.*/
    public void onAddButton(ActionEvent actionEvent) throws NullPointerException{
        Part targetPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            targetProduct.getAssociatedParts().add(targetPart);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Part to add.");
            alert.showAndWait();
        }
    }
    /**Remove Associated Part Button. A user may select a part from the Associated parts table at the bottom of the window, and click Remove Associated Part. This action will remove the selected part from the lower table, and will no longer be associated with the product if saved.
     * RUNTIME ERROR: An error with this button caused significant confusion during development. Using the targetProductData method, the variables for the selected product were successfully passed to the Modify Product screen and displayed in textfields and tables. However, any attempt to remove a part from the Associated Parts list was unsuccessful. Furthermore, upon clicking Add, the Associated Parts table would be cleared. Through trial and error, it was identified that while the method successfully passed the data to the screen, the button was not referencing the selected products Associated Parts list, but instead was pointing to the associated parts list withing the controller. Upon changing the code to read targetProduct.getAssociatedParts().remove(targetPart); the error was resolved.*/
    public void onRemoveAssociatedPart(ActionEvent actionEvent) throws NullPointerException{
        Part targetPart = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();

        if(associatedPartsTable.getSelectionModel().getSelectedItem() != null){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Click OK to remove selected Part.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            //associatedParts.remove(targetPart);
            targetProduct.getAssociatedParts().remove(targetPart);
        }
        else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please select a Part to remove.");
            alert2.showAndWait();
        }

            }
    }
    /**Save Button. If the user has info that meets the object requirements(stock between max and min values, all fields filled with correct data types), an existing product object will be modified and updated on the All Products List.*/
    public void onSaveButton(ActionEvent actionEvent)throws IOException {
        try {
            int id = targetProduct.getId();
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

                Product modifiedProduct = new Product(id, name, price, stock, min, max, associatedParts);
                int Index = Inventory.getAllProducts().indexOf(targetProduct);
                Inventory.updateProduct(Index, modifiedProduct);

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
    /**Cancel Button. Returns the user to the main screen without creating a new part object.*/
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Inventory.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /**Target Product Data Method. This method utilizes the getter functions to pull in the data for the selected product to modify, and then fills the textfields and Associated Parts table with the data.*/
    private void targetProductData(){
        idField.setText(String.valueOf(targetProduct.getId()));
        nameField.setText(String.valueOf(targetProduct.getName()));
        inventoryField.setText(String.valueOf(targetProduct.getStock()));
        costField.setText(String.valueOf(targetProduct.getPrice()));
        maxField.setText(String.valueOf(targetProduct.getMax()));
        minField.setText(String.valueOf(targetProduct.getMin()));
        associatedPartsTable.setItems(targetProduct.getAssociatedParts());
        asscPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asscPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asscPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asscPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
