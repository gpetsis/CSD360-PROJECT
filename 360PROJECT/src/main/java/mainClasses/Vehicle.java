package mainClasses;

public class Vehicle {

    String brand, model, color;
    int autonomy, vId;
    double rentcost, insurancecost;

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getAutonomy() {
        return autonomy;
    }

    public int getVehicleId() {
        return vId;
    }

    public void setBrand(String newBrand) {
        this.brand = newBrand;
    }

    public void setModel(String newModel) {
        this.model = newModel;
    }

    public void setColor(String newColor) {
        this.color = newColor;
    }

    public void setAutonomy(int newAutonomy) {
        this.autonomy = newAutonomy;
    }

    public void setVehicleId(int newvId) {
        this.vId = newvId;
    }

    public void setRentCost(double rentCost) {
        this.rentcost = rentCost;
    }

    public double getRentCost() {
        return rentcost;
    }

    public void setInsuranceCost(double insuranceCost) {
        this.insurancecost = insuranceCost;
    }

    public double getInsuranceCost() {
        return insurancecost;
    }
}
