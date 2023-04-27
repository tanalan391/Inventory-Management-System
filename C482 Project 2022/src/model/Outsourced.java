package model;
/**Outsourced Part Class. Subclass of the provided Part Class.*/
public class Outsourced extends Part{
    /**Private Outsourced variable. */
    private String companyName;
    /**Outsourced Constructor. Super Constructor based off of the provided Part Class, adding in the Company Name variable.*/
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**Outsourced Getter. */
    public String getCompanyName(){
        return companyName;
    }
    /**Outsourced Setter. */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

}
