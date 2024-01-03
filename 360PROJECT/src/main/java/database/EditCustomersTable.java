/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mainClasses.Customer;
import mainClasses.Rent;

public class EditCustomersTable {

    public SQLException addCustomerFromJSON(String json) throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {

        System.out.println(json);
        Customer c = jsonToCustomer(json);
        System.out.println("Correct JSON");
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
                + "    creditcard BIGINT not null, "
                + "    balance FLOAT not null, "
                + " PRIMARY KEY (name))";
        stmt.execute(query);
        stmt.close();
    }

    public SQLException createNewCustomer(Customer c) throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
//        try {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String insertQuery = "INSERT INTO "
                + " customers (name,birthdate,address,drivinglicense,creditcard,balance) "
                + " VALUES ("
                + "'" + c.getName() + "',"
                + "'" + c.getBirthdate() + "',"
                + "'" + c.getAddress() + "',"
                + "'" + c.getDrivingLicense() + "',"
                + "'" + c.getCreditCard() + "',"
                + "'" + c.getBalance() + "'"
                + ")";

        System.out.println(insertQuery);
        stmt.executeUpdate(insertQuery);
        System.out.println("# The customer was successfully added in the database.");

        stmt.close();
        return null;
    }

    public void chargeCustomer(double cost, String customerName) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        PreparedStatement preparedStatement;
        String query = "UPDATE customers SET balance=balance - " + cost + " WHERE name='" + customerName + "'";

        preparedStatement = con.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
