package mainClasses;

public class Car extends Vehicle {

    int licensenumber;
    String type;

    public void setType(String newType) {
        this.type = newType;
    }

    public String getType() {
        return type;
    }

    public int getVehicleId() {
        return this.licensenumber;
    }
}
