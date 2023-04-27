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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**Modify Part Controller. Controller connected to the FXML display rendered when modifying an existing part object.*/
public class ModifyPart implements Initializable {
    public Label switchType;
    public RadioButton outsourcedRadioButton;
    public RadioButton inhouseRadioButton;
    public TextField idField;
    public TextField nameField;
    public TextField inventoryField;
    public TextField costField;
    public TextField maxField;
    public TextField minField;
    public Button saveButton;
    public Button cancelButton;
    public TextField switchTypeField;
/**Target Part variable. Private data member used to pass the selected part info from the All Parts list on the main screen to the Modify Part Screen.*/
    private static Part targetPart = null;
    /**Modify Part Method. Assigns the selected part from the main screen to the Target Part variable.*/
    public static void modifyPart(Part part){
        targetPart = part;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        targetPartData();
    }
    /**Outsourced Radio Button. Selection changes last label in the colum to Company Name, to match the private member variable required for an Outsourced part object.*/
    public void onOutsourced(ActionEvent actionEvent) {
        switchType.setText("Company Name");
    }
    /**Inhouse Radio Button. Selection changes last label in the colum to Machine ID, to match the private member variable required for an Inhouse part object.*/
    public void onInhouse(ActionEvent actionEvent) {
        switchType.setText("Machine ID");
    }
    /**Cancel Button. Returns the user to the main screen without creating a new part object.*/
    public void onCancel(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Inventory.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /**Save Button. If the user has entered info that meets the object requirements(stock between max and min values, all fields filled with correct data types), an existing part object will be modified and updated on the All Parts List.*/
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = targetPart.getId();
            String name = nameField.getText();
            int stock = Integer.parseInt(inventoryField.getText());
            double price = Double.parseDouble(costField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());

            if (max <= min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be higher than Minimum value,");
                alert.showAndWait();
            }
            else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between the Minimum and Maximum values.");
                alert.showAndWait();
            }
            else if (stock <= max && stock >= min && max > min) {

                if (inhouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(switchTypeField.getText());
                    Inhouse modifiedInhousePart = new Inhouse(id, name, price, stock, min, max, machineID);
                    int Index = model.Inventory.getAllParts().indexOf(targetPart);
                    Inventory.updatePart(Index, modifiedInhousePart);
                }
                if (outsourcedRadioButton.isSelected()) {
                    String companyName = switchTypeField.getText();
                    Outsourced modifiedOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    int Index = Inventory.getAllParts().indexOf(targetPart);
                    Inventory.updatePart(Index, modifiedOutsourcedPart);
                }

                Parent root = FXMLLoader.load(getClass().getResource("/view/Inventory.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid value for each textfield.");
            alert.showAndWait();
        }
    }
/**Target Part Data Method. This method utilizes the getter functions to pull in the data for the selected part to modify, and then fills the textfields with the data.*/
        private void targetPartData() {
            if (targetPart instanceof Inhouse) {
                Inhouse part = (Inhouse) targetPart;
                idField.setText(String.valueOf(targetPart.getId()));
                nameField.setText(String.valueOf(targetPart.getName()));
                inventoryField.setText(String.valueOf(targetPart.getStock()));
                costField.setText(String.valueOf(targetPart.getPrice()));
                maxField.setText(String.valueOf(targetPart.getMax()));
                minField.setText(String.valueOf(targetPart.getMin()));
                switchTypeField.setText(String.valueOf(((Inhouse) targetPart).getMachineID()));
                inhouseRadioButton.setSelected(true);
                onInhouse(new ActionEvent());
            }
            if (targetPart instanceof Outsourced) {
                Outsourced part = (Outsourced) targetPart;
                idField.setText(String.valueOf(targetPart.getId()));
                nameField.setText(String.valueOf(targetPart.getName()));
                inventoryField.setText(String.valueOf(targetPart.getStock()));
                costField.setText(String.valueOf(targetPart.getPrice()));
                maxField.setText(String.valueOf(targetPart.getMax()));
                minField.setText(String.valueOf(targetPart.getMin()));
                switchTypeField.setText(String.valueOf(((Outsourced) targetPart).getCompanyName()));
                outsourcedRadioButton.setSelected(true);
                onOutsourced(new ActionEvent());
            }
        }

}
