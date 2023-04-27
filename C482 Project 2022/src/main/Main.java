package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inhouse;
import model.Outsourced;
import model.Product;

import static model.Inventory.allParts;
import static model.Inventory.allProducts;

//The javadoc folder is in the Main folder.

/**Main Class. Used to start the program and initialize test data. Inventory Management System.*/
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Inventory.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1000, 400));
        stage.show();
    }
/**Passing in test data and passing in the arguments. */
    public static void main(String[] args){

        allParts.add(new Inhouse(1, "Long Handle", 5.99, 50, 10, 100, 101));
        allParts.add(new Inhouse(2,"Short Handle",3.99,50, 10, 100, 102));
        allParts.add(new Inhouse(3,"Mediume Handle", 4.99, 50, 10, 100, 103));
        allParts.add(new Inhouse(4, "Shovel head", 7.99, 50, 10, 100, 104));
        allParts.add(new Outsourced(5, "Axe Head", 12.99, 50, 10, 100, "Forge Inc"));
        allParts.add(new Outsourced(6,"Rake Head", 8.99, 50, 10, 100, "Metal Inc"));
        allParts.add(new Outsourced(7, "Hoe Head", 4.99, 50, 10, 100, "Forge Inc"));
        allParts.add(new Outsourced(8, "Pickaxe Head", 13.99,50, 10, 100, "Metal Inc"));


        launch(args);
    }
}
