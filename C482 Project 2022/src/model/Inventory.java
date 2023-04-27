package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Inventory Class. This class contains the Observable Lists and Methods used to display and operate the Main Screen of the Inventory Management System.*/
public class Inventory {
/**Parts List. Observable List containing all part objects initialized within the source code and through User operation.*/
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**Products List. Observble List containing all product objects initialized within the source code and through user operation.*/
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    //METHODS
    /**Add Part Method. Method used to add a part to the Parts List.*/
    public static void addPart(Part part){
        allParts.add(part);
    }
    /**Add Product Method. Method used to add a product to the Products List.*/
    public static void addProduct(Product product) {
        allProducts.add(product);
    }
    /**Update Part Method. Method used to update an existing part from the list.*/
    public static void updatePart(int Index, Part part){
        allParts.set(Index,part);
    }
    /**Update Product Method. Method used to update an existing part from the list.*/
    public static void updateProduct(int Index, Product product){allProducts.set(Index,product);}
    /**Lookup Part Method. Utilized within the search field found above the parts list, will return a list of parts with a partial or complete match, or inform the user that no parts match the input.*/
    public static ObservableList<Part> lookupPart(String partialName){
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for(Part i : allParts){
            if (i.getName().toLowerCase().contains(partialName.toLowerCase())) {
                searchedParts.add(i);
            }
        }
        return searchedParts;
    }
    /**Lookup Part Method. Overloaded method identical to the method above, but returns parts with matching Int input.*/
    public static Part lookupPart(int partID){
        ObservableList<Part> allParts = getAllParts();

        for (int i = 0; i < allParts.size(); i++){
            Part p = allParts.get(i);
            if (p.getId() == partID){
                return p;
            }
        }
        return null;
    }
    /**Lookup Product Method. Utilized within the search field found above the products list, will return a list of products with a partial or complete match, or inform the user that no products match the input.*/
    public static ObservableList<Product> lookupProduct(String partialName){
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();

        for (Product i : allProducts){
            if (i.getName().toLowerCase().contains(partialName.toLowerCase())){
                searchedProducts.add(i);
            }
        }
        return searchedProducts;
    }
    /**Lookup Product Method. Overloaded method identical to the method above, but returns products with matching Int input.*/
    public static Product lookupProduct(int productID){
        ObservableList<Product> allProducts = getAllProducts();

        for (int i = 0; i < allProducts.size(); i++){
            Product p = allProducts.get(i);
            if (p.getId() == productID){
                return p;
            }
        }
        return null;
    }
/**Delete Part Method. Used to permanently delete a part object from the list*/
    public static boolean deletePart(Part part){
        allParts.remove(part);
        return true;
    }
    /**Delete Product Method. Used to permanently delete a product object from the list*/
    public static boolean deleteProduct(Product product){
        allProducts.remove(product);
        return true;
    }
/**Get Product List method. Returns the list of all products, used to display the list of products on the main screen and to search through the list of products as part of other functions.*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    /**Get Part List method. Returns the list of all parts, used to display the list of parts on the main screen and to search through the list of parts as part of other functions.*/
    public static ObservableList<Part> getAllParts(){return allParts;}
/**Part ID Generator. Method created to create a unique Part ID for each new part object. Method ensures that any new part added will have it's own identifier, and is not editable by the user.*/
    public static int uniqueIDGenPart(){

        int newID = 1;
        for (int x = 0; x < allParts.size(); x++){
            Part p = allParts.get(x);
            if (p.getId() == newID){
                newID++;
            }
        }
        return newID;
    }
    /**Product ID Generator. Method created to create a unique Product ID for each new part object. Method ensures that any new product added will have it's own identifier, and is not editable by the user.*/
    public static int uniqueIDGenProduct(){

        int newID = 100;
        for (int x = 0; x < allProducts.size(); x++){
            Product p = allProducts.get(x);
            if (p.getId() == newID){
                newID++;
            }
        }
        return newID;
    }



}
