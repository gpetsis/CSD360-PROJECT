/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
