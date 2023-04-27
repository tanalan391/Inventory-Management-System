package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;
/**Inventory Controller Class. Controller associated with the FXML file for the main screen of the Inventory Management System. Methods from the Inventory Model are utilized here and connected with buttons and text fields the User can interact with.*/
public class Inventory implements Initializable {
    public TableView partsTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partCostCol;

    public TableView productsTable;
    public TableColumn productIDCol;
    public TableColumn productNameCol;
    public TableColumn productInvCol;
    public TableColumn productCostCol;

    public Button addPartButton;
    public Button modifyPartButton;
    public Button deletePartButton;

    public Button addProductButton;
    public Button modifyProductButton;
    public Button deleteProductButton;

    public Button exitButton;
    public TextField partSearch;
    public TextField productSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(allParts);
        productsTable.setItems(allProducts);

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));




    }
/**Add Part Button. Directs the user to a new window where a new part object may be created.*/
    public void onAddPartButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600,400);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();

    }
/**Modify Part Button. Upon selection of an existing Part from the displayed list, a User can click the Modify Part button. They will be directed to a new screen containing the existing variables of the selected part, which can be modified and saved with the exception of the Part ID.*/
    public void onModifyPartButton(ActionEvent actionEvent) throws IOException {
       ModifyPart.modifyPart((Part) partsTable.getSelectionModel().getSelectedItem());
    try{
            if (partsTable.getSelectionModel().getSelectedItem() != null){

            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 550);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Part to modify.");
                alert.showAndWait();
            }
        }
    catch (NullPointerException | IOException e){
        Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Part to modify.");
        alert.showAndWait();}
    }
/**Delete Part Button. Allows the user to permanently delete a part object from the list. FUTURE ENHANCEMENT: The Assesments instructs to present an error when the user attempts to delete a product object that has associated parts. However, no instruction is given regarding parts that are associated with a product. A fairly easy addition to the program would be validation to ensure that a part to be deleted is not associated with an existing product.*/
    public void onDeletePartButton(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Selected Part will be permanently deleted. Click OK to proceed.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Part part = (Part) partsTable.getSelectionModel().getSelectedItem();
                deletePart(part);
            }
    }
    /**Add Product Button. Directs the user to a new window where a new product object may be created.*/
    public void onAddProductButton(ActionEvent actionEvent)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,500);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }
    /**Modify Product Button. Upon selection of an existing Product from the displayed list, a User can click the Modify Product button. They will be directed to a new screen containing the existing variables of the selected product, which can be modified and saved with the exception of the Product ID.*/
    public void onModifyProductButton(ActionEvent actionEvent)throws NullPointerException {
        try {
            ModifyProduct.modifyProduct((Product) productsTable.getSelectionModel().getSelectedItem());

            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
            }
        catch (NullPointerException | IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Product to modify.");
            alert.showAndWait();
        }
    }
/**Delete Part Button. Allows the user to permanently delete a part object from the list. An dialog box will pop up if the selected product as any parts associated with it.*/
    public void onDeleteProductButton(ActionEvent actionEvent) {
        Product product = (Product) productsTable.getSelectionModel().getSelectedItem();

        if (product.getAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Selected Product will be permanently deleted. Click OK to proceed.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteProduct(product);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "A Product with associated Parts cannot be deleted.");
            alert.showAndWait();
        }
    }
/**Exit Button. Closes the program.*/
    public void onExitButton(ActionEvent actionEvent) {
        Platform.exit();
    }
/**Part Search. User may input a query in the textfield above the displayed Parts list and press enter. The parts table will display any parts matching the query or a popup box will appear and inform the user that no matching parts were found.*/
    public void onPartSearch(ActionEvent actionEvent){
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
    /**Product Search. User may input a query in the textfield above the displayed Products list and press enter. The products table will display any products matching the query or a popup box will appear and inform the user that no matching products were found.*/
    public void onProductSearch(ActionEvent actionEvent) {
        try {
            String q = productSearch.getText();

            ObservableList<Product> searchedProducts = lookupProduct(q);

            if (searchedProducts.size() == 0) {
                int productID = Integer.parseInt(q);
                Product p = lookupProduct(productID);
                if (p != null)
                    searchedProducts.add(p);
                if (p == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No Products found!");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent()) {
                        productsTable.setItems(allProducts);
                    }
                }
            }
            productsTable.setItems(searchedProducts);
            productSearch.setText("");
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Products found!");
            alert.showAndWait();
        }
    }

}