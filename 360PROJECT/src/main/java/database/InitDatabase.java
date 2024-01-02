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
    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {
//        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
//        System.setOut(fileOut);
//        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
//        System.setOut(fileOut);
        InitDatabase init = new InitDatabase();
//        init.initDatabase();
//        init.initTables();
    }

    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("DROP DATABASE PROJECT360");
        stmt.execute("CREATE DATABASE PROJECT360");
        stmt.close();
        conn.close();
    }

    public void initTables() throws SQLException, ClassNotFoundException {
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

        EditCustomersTable customersTable = new EditCustomersTable();
        customersTable.addCustomerFromJSON(examples.Customer1JSON);
        customersTable.addCustomerFromJSON(examples.Customer2JSON);
        customersTable.addCustomerFromJSON(examples.Customer3JSON);
        customersTable.addCustomerFromJSON(examples.Customer4JSON);
        customersTable.addCustomerFromJSON(examples.Customer5JSON);

    }
}
