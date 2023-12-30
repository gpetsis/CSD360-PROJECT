/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Customer;
import mainClasses.Rent;

/**
 *
 * @author Nikos Lasithiotakis
 */
public class EditCustomersTable {

    public SQLException addCustomerFromJSON(String json) throws ClassNotFoundException, FileNotFoundException {
        Customer c = jsonToCustomer(json);
        return createNewCustomer(c);
    }

    public Customer jsonToCustomer(String json) {
        Gson gson = new Gson();

        Customer c = gson.fromJson(json, Customer.class);
        return c;
    }

    public String CustomerToJSON(Rent r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Customer.class);
        return json;
    }

    public ArrayList<Customer> getCustomers() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Customer> customers = new ArrayList<Customer>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM customers");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Customer customer = gson.fromJson(json, Customer.class);
                customers.add(customer);
            }
            return customers;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void createCustomersTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE customers "
                + "(name VARCHAR(20) not null, "
                + "    birthdate DATE not null, "
                + "    address VARCHAR(50) not null, "
                + "    drivinglicense INTEGER not null, "
                + "    creditcard INTEGER not null, "
                + " PRIMARY KEY (name))";
        stmt.execute(query);
        stmt.close();
    }

    public SQLException createNewCustomer(Customer c) throws ClassNotFoundException, FileNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            String insertQuery = "INSERT INTO "
                    + " customers (name,address,birthdate,drivinglicense,creditcard) "
                    + " VALUES ("
                    + "'" + c.getName() + "',"
                    + "'" + c.getAddress() + "',"
                    + "'" + c.getBirthdate() + "',"
                    + "'" + c.getDrivingLicense() + "',"
                    + "'" + c.getCreditCard() + "',"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The customer was successfully added in the database.");
            /* Get the member id from the database and set it to the member */
            stmt.close();
            return null;

        } catch (SQLException ex) {
            Logger.getLogger(EditCustomersTable.class.getName()).log(Level.SEVERE, null, ex);
            return ex;
        }
    }
}
