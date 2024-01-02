/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.ws.rs.NotFoundException;
import mainClasses.Bicycle;
import mainClasses.Car;
import mainClasses.Rent;
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
        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        System.setOut(fileOut);
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
                throw new NotFoundException("Vehicle does not exist!");
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
                    query = "UPDATE customers SET balance=balance - " + penalty + " WHERE name='" + customerName + "'";

                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.executeUpdate();

                }
                query = "DELETE FROM rents WHERE vId=" + vId;

                preparedStatement = con.prepareStatement(query);
                preparedStatement.executeUpdate();

                System.out.println("Difference: " + daysDifference);
            }

        }
//        } catch (Exception e) {
//            System.out.println("Got an exception! ");
//            System.out.println(e.getMessage());
//        }
    }

//    public void updateVehicle(String username, String personalpage) throws SQLException, ClassNotFoundException {
//        Connection con = DB_Connection.getConnection();
//        Statement stmt = con.createStatement();
//        String update = "UPDATE petowners SET personalpage='" + personalpage + "' WHERE username = '" + username + "'";
//        stmt.executeUpdate(update);
//    }

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

//    public PetOwner databaseToPetOwners(String username, String password) throws SQLException, ClassNotFoundException {
//        Connection con = DB_Connection.getConnection();
//        Statement stmt = con.createStatement();
//
//        ResultSet rs;
//        try {
//            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "' AND password='" + password + "'");
//            rs.next();
//            String json = DB_Connection.getResultsToJSON(rs);
//            Gson gson = new Gson();
//            PetOwner user = gson.fromJson(json, PetOwner.class);
//            return user;
//        } catch (Exception e) {
//            System.err.println("Got an exception! ");
//            System.err.println(e.getMessage());
//        }
//        return null;
//    }

//    public String databasePetOwnerToJSON(String username, String password) throws SQLException, ClassNotFoundException {
//        Connection con = DB_Connection.getConnection();
//        Statement stmt = con.createStatement();
//
//        ResultSet rs;
//        try {
//            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "' AND password='" + password + "'");
//            rs.next();
//            String json = DB_Connection.getResultsToJSON(rs);
//            return json;
//        } catch (Exception e) {
//            System.err.println("Got an exception! ");
//            System.err.println(e.getMessage());
//        }
//        return null;
//    }

    public void createVehiclesTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE vehicles "
                + "(vId INTEGER not null unique,"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,"
                + "    color VARCHAR(10) not null,"
                + "    autonomy INTEGER not null,"
                + " PRIMARY KEY (vId))";
        stmt.execute(query);

        query = "CREATE TABLE cars "
                + "(licensenumber INTEGER not null references vehicles(vId),"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,"
                + "    color VARCHAR(10) not null,"
                + "    autonomy INTEGER not null,"
                + "    type VARCHAR(15) not null,"
                + " PRIMARY KEY (licensenumber))";
        stmt.execute(query);

        query = "CREATE TABLE scooters "
                + "(vId INTEGER not null references vehicles(vId),"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,"
                + "    color VARCHAR(10) not null,"
                + "    autonomy INTEGER not null,"
                + " PRIMARY KEY (vId))";
        stmt.execute(query);

        query = "CREATE TABLE bicycles "
                + "(vId INTEGER not null references vehicles(vId),"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,"
                + "    color VARCHAR(10) not null,"
                + "    autonomy INTEGER not null,"
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
                    + " vehicles (vId, brand, model, color, autonomy)"
                    + " VALUES ("
                    + "'" + vehicle.getVehicleId() + "',"
                    + "'" + vehicle.getBrand() + "',"
                    + "'" + vehicle.getModel() + "',"
                    + "'" + vehicle.getColor() + "',"
                    + "'" + vehicle.getAutonomy() + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The vehicle was successfully added to vehicles.");

            if (type.equals("cars")) {
                Car car = (Car) vehicle;
                insertQuery = "INSERT INTO "
                        + " " + type + " (licensenumber, brand, model, color, autonomy, type)"
                        + " VALUES ("
                        + "'" + car.getVehicleId() + "',"
                        + "'" + car.getBrand() + "',"
                        + "'" + car.getModel() + "',"
                        + "'" + car.getColor() + "',"
                        + "'" + car.getAutonomy() + "',"
                        + "'" + car.getType() + "'"
                        + ")";
            } else {
                insertQuery = "INSERT INTO "
                        + " " + type + " (vId, brand, model, color, autonomy)"
                        + " VALUES ("
                        + "'" + vehicle.getVehicleId() + "',"
                        + "'" + vehicle.getBrand() + "',"
                        + "'" + vehicle.getModel() + "',"
                        + "'" + vehicle.getColor() + "',"
                        + "'" + vehicle.getAutonomy() + "'"
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
