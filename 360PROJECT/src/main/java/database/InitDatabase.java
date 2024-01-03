/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import static database.DB_Connection.getInitialConnection;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {
    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("DROP DATABASE PROJECT360");
        stmt.execute("CREATE DATABASE PROJECT360");
        stmt.close();
        conn.close();
    }

    public void initTables() throws SQLException, ClassNotFoundException, FileNotFoundException {
        EditCustomersTable editcustomers = new EditCustomersTable();
        EditVehiclesTable editvehicles = new EditVehiclesTable();
        EditRentsTable editrents = new EditRentsTable();

        editcustomers.createCustomersTable();
        editvehicles.createVehiclesTable();
        editrents.createRentsTable();
    }

    public void addToDatabaseExamples() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {

        EditVehiclesTable vehiclesTable = new EditVehiclesTable();
        vehiclesTable.addBicycleFromJSON(examples.bike1JSON);
        vehiclesTable.addBicycleFromJSON(examples.bike2JSON);
        vehiclesTable.addScooterFromJSON(examples.scooter1JSON);
        vehiclesTable.addScooterFromJSON(examples.scooter2JSON);
        vehiclesTable.addCarFromJSON(examples.car1JSON);
        vehiclesTable.addCarFromJSON(examples.car2JSON);
        vehiclesTable.addMotorcycleFromJSON(examples.motorbike1JSON);
        vehiclesTable.addMotorcycleFromJSON(examples.motorbike2JSON);

        EditCustomersTable customersTable = new EditCustomersTable();
        customersTable.addCustomerFromJSON(examples.Customer1JSON);
        customersTable.addCustomerFromJSON(examples.Customer2JSON);
        customersTable.addCustomerFromJSON(examples.Customer3JSON);
        customersTable.addCustomerFromJSON(examples.Customer4JSON);
        customersTable.addCustomerFromJSON(examples.Customer5JSON);

    }
}
