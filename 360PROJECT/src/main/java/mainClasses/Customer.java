package mainClasses;

public class Customer {

    String name, birthdate, address;
    int drivinglicense;
    long creditcard;
    float balance;

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

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }
}
