package mainClasses;

public class Rent {

    String name, date, duration;
    double cost;
    int vId, insurance;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public int getvId() {
        return vId;
    }

    public int getInsurance() {
        return insurance;
    }
}
