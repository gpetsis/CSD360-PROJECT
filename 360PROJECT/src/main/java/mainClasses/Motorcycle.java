/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainClasses;

public class Motorcycle extends Vehicle {

    int licensenumber;

    public void setVehicleId(int licensenumber) {
        this.licensenumber = licensenumber;
    }

    public int getVehicleId() {
        return this.licensenumber;
    }
}
