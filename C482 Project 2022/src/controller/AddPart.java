package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inhouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**Add Part Controller. Controller connected to the FXML display rendered when adding a new part object.*/
public class AddPart implements Initializable {

    public RadioButton inhouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public Label switchType;
    public TextField nameField;
    public TextField inventoryField;
    public TextField costField;
    public TextField maxField;
    public TextField minField;

    public Button saveButton;
    public Button cancelButton;
    public TextField switchTypeField;
/**Outsourced Radio Button. Selection changes last label in the colum to Company Name, to match the private member variable required for an Outsourced part object.*/
    public void onOutsourced(ActionEvent actionEvent) {
        switchType.setText("Company Name");
    }
/**Inhouse Radio Button. Selection changes last label in the colum to Machine ID, to match the private member variable required for an Inhouse part object.*/
    public void onInhouse(ActionEvent actionEvent) {
        switchType.setText("Machine ID");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
/**Save Button. If the user has entered info that meets the object requirements(stock between max and min values, all fields filled with correct data types), a new part object will be created and added to the All Parts List.*/
    public void onSaveButton(ActionEvent actionEvent)throws IOException {
        try {
                int id = Inventory.uniqueIDGenPart();
                String name = nameField.getText();
                int stock = Integer.parseInt(inventoryField.getText());
                double price = Double.parseDouble(costField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());

            if (max <= min){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Maximum must be higher than Minimum value.");
                alert.showAndWait();
            }
            else if (stock > max || stock < min){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between the Minimum and Maximum values.");
                alert.showAndWait();
            }
            else if(stock <= max && stock >= min && max > min){

                    if (inhouseRadioButton.isSelected()) {
                        int machineID = Integer.parseInt(switchTypeField.getText());
                        Inhouse newInhousePart = new Inhouse(id, name, price, stock, min, max, machineID);
                        Inventory.addPart(newInhousePart);
                    }
                    if (outsourcedRadioButton.isSelected()) {
                        String companyName = switchTypeField.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        Inventory.addPart(newOutsourcedPart);
                    }

                    Parent root = FXMLLoader.load(getClass().getResource("/view/Inventory.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1000, 400);
                    stage.setTitle("Inventory Management System");
                    stage.setScene(scene);
                    stage.show();
            }
        }
            catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setContentText("Please enter a valid value for each textfield.");
            alert.showAndWait();
            }
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
    
}
