package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**Product Class. Each Product is made up of variables identical to that of Part, with the addition of an Associated Parts Array List. Each Product can have zero to many Parts associated with it.*/
public class Product {
    /**Private Member Variables. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private  String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**Constructor. */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> associatedParts){
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**Getter. Product ID.  */
    public int getId() {
        return id;
    }
    /**Setter. Product ID*/
    public void setId(int id) {
        this.id = id;
    }
    /**Getter. Product name*/
    public String getName() {
        return name;
    }
    /**Setter. Product Name*/
    public void setName(String name) {
        this.name = name;
    }
    /**Getter. Product Price*/
    public double getPrice() {
        return price;
    }
    /**Setter. Product Price*/
    public void setPrice(double price) {
        this.price = price;
    }
    /**Getter. Product Stock, aka Inventory.*/
    public int getStock() {
        return stock;
    }
    /**Setter. Product Stock, aka Inventory.*/
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**Getter. Minimum amount of Product allowed.*/
    public int getMin() {
        return min;
    }
    /**Setter. Minimum amount of Product allowed.*/
    public void setMin(int min) {
        this.min = min;
    }
    /**Getter. Maximum amount of Product allowed.*/
    public int getMax() {
        return max;
    }
    /**Setter. Maximum amount of Product allowed.*/
    public void setMax(int max) {
        this.max = max;
    }

    /**Add Associated Part. Product method to add a selected part to the associated Parts list of a product object.*/
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**Delete Associated Part. Product method to remove a selected part from the associated Parts list of a product object.*/
    public void deleteAssociatedPart(Part part){
        associatedParts.remove(part);
    }
    /**Get Associated Parts Method. Used to obtain the list of associated parts for a selected product object.*/
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }


}
