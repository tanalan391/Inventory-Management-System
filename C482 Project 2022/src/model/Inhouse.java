package model;
/**Inhouse Part Class. Subclass of the provided Part class.*/
public class Inhouse extends Part{
/**Private Inhouse variable. */
    private int machineID;
    /**Inhouse Constructor. Super constructor based off the provided Part class, adding in the Machine ID variable.*/
    public Inhouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /**Inhouse Getter. */
    public int getMachineID() {
        return machineID;
    }
    /**Inhouse Setter. */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
