/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainClasses;

/**
 *
 * @author Nikos Lasithiotakis
 */
public class Customer {

    String name, birthdate, address;
    int drivinglicense;
    long creditcard;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setDrivingLicense(int drivinglicense) {
        this.drivinglicense = drivinglicense;
    }

    public int getDrivingLicense() {
        return drivinglicense;
    }

    public void setCreditCard(long creditcard) {
        this.creditcard = creditcard;
    }

    public long getCreditCard() {
        return creditcard;
    }
}
