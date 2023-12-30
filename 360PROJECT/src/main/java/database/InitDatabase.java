/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import static database.DB_Connection.getInitialConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InitDatabase init = new InitDatabase();
//        init.initDatabase();
//        init.initTables();
    }

    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE PROJECT360");
        stmt.close();
        conn.close();
    }

    public void initTables() throws SQLException, ClassNotFoundException {
        EditCustomersTable editcustomers = new EditCustomersTable();
        EditVehiclesTable editvehicles = new EditVehiclesTable();

        editcustomers.createCustomersTable();
        editvehicles.createVehiclesTable();

        // init rents
    }

    public void addToDatabaseExamples() throws ClassNotFoundException, SQLException {

        EditVehiclesTable vehiclesTable = new EditVehiclesTable();
        vehiclesTable.addVehicleFromJSON(examples.bike1JSON);
        vehiclesTable.addVehicleFromJSON(examples.bike2JSON);
        vehiclesTable.addVehicleFromJSON(examples.scooter1JSON);
        vehiclesTable.addVehicleFromJSON(examples.scooter1JSON);
        vehiclesTable.addVehicleFromJSON(examples.car1JSON);
        vehiclesTable.addVehicleFromJSON(examples.car2JSON);

//        EditPetKeepersTable editKeepers = new EditPetKeepersTable();
//        editKeepers.addPetKeeperFromJSON(Resources.petKeeper1);
//        editKeepers.addPetKeeperFromJSON(Resources.petKeeper2);
//        editKeepers.addPetKeeperFromJSON(Resources.petKeeper3);
//        editKeepers.addPetKeeperFromJSON(Resources.petKeeper4);
//        editKeepers.addPetKeeperFromJSON(Resources.petKeeper5);
//        editKeepers.addPetKeeperFromJSON(Resources.petKeeper6);
//
//        EditPetsTable ebt = new EditPetsTable();
//        ebt.addPetFromJSON(Resources.pet1);
//        ebt.addPetFromJSON(Resources.pet2);
//        ebt.addPetFromJSON(Resources.pet3);
//        ebt.addPetFromJSON(Resources.pet4);
//
//        EditBookingsTable editbookings = new EditBookingsTable();
//        editbookings.addBookingFromJSON(Resources.booking1);
//        editbookings.addBookingFromJSON(Resources.booking2);
//        editbookings.addBookingFromJSON(Resources.booking3);
//
//        EditMessagesTable editmessages = new EditMessagesTable();
//        editmessages.addMessageFromJSON(Resources.message1);
//        editmessages.addMessageFromJSON(Resources.message2);
//
//        EditReviewsTable editRevs = new EditReviewsTable();
//        editRevs.addReviewFromJSON(Resources.review1);
    }
}
