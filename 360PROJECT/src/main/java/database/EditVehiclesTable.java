/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import mainClasses.Bicycle;
import mainClasses.Car;
import mainClasses.Rent;
import mainClasses.Motorcycle;
import mainClasses.Scooter;
import mainClasses.Vehicle;

public class EditVehiclesTable {
//    public SQLException addVehicleFromJSON(String json) throws ClassNotFoundException, FileNotFoundException {
////        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
////        System.setOut(fileOut);
////        System.out.println(json);
//        Vehicle vehicle = jsonToVehicle(json);
//        return addNewVehicle(vehicle);
//    }

    public SQLException addCarFromJSON(String json) throws ClassNotFoundException, FileNotFoundException {

        Car vehicle = jsonToCar(json);
        return addNewVehicle(vehicle, "cars");
    }

    public SQLException addMotorcycleFromJSON(String json) throws ClassNotFoundException, FileNotFoundException {

        Motorcycle vehicle = jsonToMotorcycle(json);
        return addNewVehicle(vehicle, "motorcycles");
    }

    public SQLException addScooterFromJSON(String json) throws ClassNotFoundException, FileNotFoundException {
//        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
//        System.setOut(fileOut);
        System.out.println(json);
        Scooter vehicle = jsonToScooter(json);
        System.out.println(vehicle.getBrand());
        return addNewVehicle(vehicle, "scooters");
    }

    public SQLException addBicycleFromJSON(String json) throws ClassNotFoundException, FileNotFoundException {
        Bicycle vehicle = jsonToBicycle(json);
        return addNewVehicle(vehicle, "bicycles");
    }

    public Vehicle jsonToVehicle(String json) {
        Gson gson = new Gson();

        Vehicle vehicle = gson.fromJson(json, Vehicle.class);
        return vehicle;
    }

    public Car jsonToCar(String json) {
        Gson gson = new Gson();

        Car vehicle = gson.fromJson(json, Car.class);
        return vehicle;
    }

    public Motorcycle jsonToMotorcycle(String json) {
        Gson gson = new Gson();

        Motorcycle vehicle = gson.fromJson(json, Motorcycle.class);
        return vehicle;
    }

    public Scooter jsonToScooter(String json) {
        Gson gson = new Gson();
        Scooter vehicle = gson.fromJson(json, Scooter.class);
        return vehicle;
    }

    public Bicycle jsonToBicycle(String json) {
        Gson gson = new Gson();

        Bicycle vehicle = gson.fromJson(json, Bicycle.class);
        return vehicle;
    }

    public String VehicleToJSON(Vehicle user) {
        Gson gson = new Gson();

        String json = gson.toJson(user, Vehicle.class);
        return json;
    }

    public void returnVehicle(String vId) throws SQLException, FileNotFoundException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt;
        Vehicle vehicle;
        ResultSet rs = null;
        boolean vehicleExists;

//        try {
        String query = "SELECT COUNT(*) AS count FROM rents WHERE vId=" + vId;
        PreparedStatement preparedStatement = con.prepareStatement(query);

        rs = preparedStatement.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("count");
            if (count == 0) {
                vehicleExists = false;
            } else {
                vehicleExists = true;
            }

            System.out.println("Count: " + count);
            if (!vehicleExists) {
                throw new NoSuchElementException("Vehicle does not exist!");
            }

            query = "SELECT * FROM rents WHERE vId=" + vId;
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            if (rs.next()) {
                EditRentsTable rentsTable = new EditRentsTable();
                String json = DB_Connection.getResultsToJSON(rs);
                Rent rent = rentsTable.jsonToRent(json);
                System.out.println(rent.getDate());

                LocalDate current = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String formattedDate = current.format(formatter);

                LocalDate rentDate = LocalDate.parse(rent.getDate(), formatter);
                LocalDate currentDate = LocalDate.parse(formattedDate, formatter);

                long daysDifference = ChronoUnit.DAYS.between(currentDate, rentDate);

                if (daysDifference < 0) {
                    int penalty = (int) (-24 * daysDifference); // daysDifference is < 0
                    String customerName = rent.getName();
                    EditCustomersTable customersTable = new EditCustomersTable();
                    customersTable.chargeCustomer(penalty, customerName);
                }
                query = "DELETE FROM rents WHERE vId=" + vId;

                preparedStatement = con.prepareStatement(query);
                preparedStatement.executeUpdate();

                System.out.println("Difference: " + daysDifference);
            }

        }
    }

    public void serviceVehicle(int vId) throws SQLException, ClassNotFoundException {
        LocalDate current = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate plusDays = current.plusDays(1);

        String formattedDate = plusDays.format(formatter);

        System.out.println(formattedDate);

        addToUnavailable(vId, formattedDate, 0, null);
    }

    public void repairVehicle(int vId) throws SQLException, ClassNotFoundException {
        LocalDate current = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate plusDays = current.plusDays(3);

        String formattedDate = plusDays.format(formatter);

        System.out.println(formattedDate);

        updateUnavailable(vId, formattedDate);
    }

    public void updateUnavailable(int vId, String returnDate) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();

        String query = "UPDATE unavailable SET returndate='" + returnDate + "' WHERE vid=" + vId;

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.executeUpdate();

        System.out.println(query);
        System.out.println("# The vehicle was successfully updated to unavailable.");

        preparedStatement.close();
    }

    public void refreshUnavailable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();

        LocalDate current = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = current.format(formatter);

        String query = "DELETE FROM unavailable WHERE returndate<='" + formattedDate + "'";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        int rowsAffected = preparedStatement.executeUpdate();

        System.out.println(query);
        System.out.println("# " + rowsAffected + " vehicles were removed from unavailable.");

        preparedStatement.close();
    }

    public void addToUnavailable(int vId, String returnDate, float repaircost, String entrydate) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String insertQuery = "INSERT INTO "
                + " unavailable (vId, returndate, repaircost, entrydate)"
                + " VALUES ("
                + vId + ","
                + "" + returnDate + ","
                + repaircost
                + ",'" + entrydate + "'"
                + ")";

        System.out.println(insertQuery);
        stmt.executeUpdate(insertQuery);
        System.out.println("# The vehicle was successfully added to unavailable.");

        stmt.close();

    }

    public ArrayList<String> getVehicles() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<String> vehicles = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM vehicles");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                vehicles.add(json);
            }
            return vehicles;
        } catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<String> getCars() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<String> cars = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM cars");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                cars.add(json);
            }
            return cars;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<String> getMotorcycles() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<String> motorcycles = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM motorcycles");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                motorcycles.add(json);
            }
            return motorcycles;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<String> getScooters() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<String> scooters = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM scooters");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                scooters.add(json);
            }
            return scooters;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<String> getBicycles() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<String> vehicles = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM bicycles");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                vehicles.add(json);
            }
            return vehicles;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void createVehiclesTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE vehicles "
                + "(vId INTEGER not null unique,"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,"
                + "    color VARCHAR(10) not null,"
                + "    autonomy INTEGER not null,"
                + "    rentcost FLOAT(10) not null,"
                + "    insurancecost FLOAT(10) not null,"
                + " PRIMARY KEY (vId))";
        stmt.execute(query);

        query = "CREATE TABLE cars "
                + "(licensenumber INTEGER not null references vehicles(vId),"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,"
                + "    color VARCHAR(10) not null,"
                + "    autonomy INTEGER not null,"
                + "    type VARCHAR(15) not null,"
                + "    rentcost FLOAT(10) not null,"
                + "    insurancecost FLOAT(10) not null,"
                + " PRIMARY KEY (licensenumber))";
        stmt.execute(query);

        query = "CREATE TABLE motorcycles "
                + "(licensenumber INTEGER not null references vehicles(vId),"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,"
                + "    color VARCHAR(10) not null,"
                + "    autonomy INTEGER not null,"
                + "    rentcost FLOAT(10) not null,"
                + "    insurancecost FLOAT(10) not null,"
                + " PRIMARY KEY (licensenumber))";
        stmt.execute(query);

        query = "CREATE TABLE scooters "
                + "(vId INTEGER not null references vehicles(vId),"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,"
                + "    color VARCHAR(10) not null,"
                + "    autonomy INTEGER not null,"
                + "    rentcost FLOAT(10) not null,"
                + "    insurancecost FLOAT(10) not null,"
                + " PRIMARY KEY (vId))";
        stmt.execute(query);

        query = "CREATE TABLE bicycles "
                + "(vId INTEGER not null references vehicles(vId),"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,"
                + "    color VARCHAR(10) not null,"
                + "    autonomy INTEGER not null,"
                + "    rentcost FLOAT(10) not null,"
                + "    insurancecost FLOAT(10) not null,"
                + " PRIMARY KEY (vId))";
        stmt.execute(query);

        query = "CREATE TABLE unavailable"
                + "(vId INTEGER not null,"
                + "    returndate DATE,"
                + "    repaircost FLOAT(10),"
                + "    entrydate DATE,"
                + "    FOREIGN KEY (vId) REFERENCES vehicles(vId), "
                + " PRIMARY KEY (vId))";
        stmt.execute(query);
        stmt.close();
    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public SQLException addNewVehicle(Vehicle vehicle, String type) throws ClassNotFoundException, FileNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();
            String insertQuery;
            Statement stmt = con.createStatement();

            insertQuery = "INSERT INTO "
                    + " vehicles (vId, brand, model, color, autonomy, rentcost, insurancecost)"
                    + " VALUES ("
                    + "'" + vehicle.getVehicleId() + "',"
                    + "'" + vehicle.getBrand() + "',"
                    + "'" + vehicle.getModel() + "',"
                    + "'" + vehicle.getColor() + "',"
                    + "'" + vehicle.getAutonomy() + "',"
                    + "'" + vehicle.getRentCost() + "',"
                    + "'" + vehicle.getInsuranceCost() + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The vehicle was successfully added to vehicles.");

            if (type.equals("cars")) {
                Car car = (Car) vehicle;
                insertQuery = "INSERT INTO "
                        + " " + type + " (licensenumber, brand, model, color, autonomy, type, rentcost, insurancecost)"
                        + " VALUES ("
                        + "'" + car.getVehicleId() + "',"
                        + "'" + car.getBrand() + "',"
                        + "'" + car.getModel() + "',"
                        + "'" + car.getColor() + "',"
                        + "'" + car.getAutonomy() + "',"
                        + "'" + car.getType() + "',"
                        + "'" + vehicle.getRentCost() + "',"
                        + "'" + vehicle.getInsuranceCost() + "'"
                        + ")";
            } else if (type.equals("bicycles") || type.equals("scooters")) {
                insertQuery = "INSERT INTO "
                        + " " + type + " (vId, brand, model, color, autonomy, rentcost, insurancecost)"
                        + " VALUES ("
                        + "'" + vehicle.getVehicleId() + "',"
                        + "'" + vehicle.getBrand() + "',"
                        + "'" + vehicle.getModel() + "',"
                        + "'" + vehicle.getColor() + "',"
                        + "'" + vehicle.getAutonomy() + "',"
                        + "'" + vehicle.getRentCost() + "',"
                        + "'" + vehicle.getInsuranceCost() + "'"
                        + ")";
            } else {
                Motorcycle motorcycle = (Motorcycle) vehicle;
                insertQuery = "INSERT INTO "
                        + " " + type + " (licensenumber, brand, model, color, autonomy, rentcost, insurancecost)"
                        + " VALUES ("
                        + "'" + motorcycle.getVehicleId() + "',"
                        + "'" + motorcycle.getBrand() + "',"
                        + "'" + motorcycle.getModel() + "',"
                        + "'" + motorcycle.getColor() + "',"
                        + "'" + motorcycle.getAutonomy() + "',"
                        + "'" + motorcycle.getRentCost() + "',"
                        + "'" + motorcycle.getInsuranceCost() + "'"
                        + ")";
            }
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The vehicle was successfully added to " + type + ".");

            stmt.close();
            return null;
        } catch (SQLException ex) {
//            PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
//            System.setOut(fileOut);
            System.out.println("Error: " + ex);
            return ex;
        }
    }

}
