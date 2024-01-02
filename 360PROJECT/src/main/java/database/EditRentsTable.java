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
import mainClasses.Rent;

/**
 *
 * @author Nikos Lasithiotakis
 */
public class EditRentsTable {

    public SQLException addRentFromJSON(String json) throws ClassNotFoundException, FileNotFoundException {
        Rent r = jsonToRent(json);
        return createNewRent(r);
    }

    public Rent jsonToRent(String json) {
        Gson gson = new Gson();

        Rent r = gson.fromJson(json, Rent.class);
        return r;
    }

    public String RentToJSON(Rent r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Rent.class);
        return json;
    }

    public ArrayList<Rent> getRents() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Rent> rents = new ArrayList<Rent>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM rents");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Rent rent = gson.fromJson(json, Rent.class);
                rents.add(rent);
            }
            return rents;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void createRentsTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE rents ("
                + "    rent_id INTEGER NOT NULL AUTO_INCREMENT, "
                + "    vId INT, "
                + "    name VARCHAR(255), "
                + "    date DATE NOT NULL, "
                + "    duration VARCHAR(50) NOT NULL, "
                + "    cost FLOAT(10) NOT NULL, "
                + "    insurance BOOLEAN NOT NULL, "
                + "    PRIMARY KEY (rent_id), "
                + "    FOREIGN KEY (vId) REFERENCES vehicles(vId), "
                + "    FOREIGN KEY (name) REFERENCES customers(name)"
                + ")";
        stmt.execute(query);
        stmt.close();
    }

    public SQLException createNewRent(Rent r) throws ClassNotFoundException, FileNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            String insertQuery = "INSERT INTO "
                    + " rents (vId,name,date,duration,cost,insurance) "
                    + " VALUES ("
                    + "'" + r.getvId() + "',"
                    + "'" + r.getName() + "',"
                    + "'" + r.getDate() + "',"
                    + "'" + r.getDuration() + "',"
                    + "'" + r.getCost() + "',"
                    + "'" + r.getInsurance() + "'"
                    + ")";
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The rent was successfully added in the database.");
            stmt.close();
            return null;

        } catch (SQLException ex) {
//            PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
//            System.setOut(fileOut);
            System.out.println("Error: " + ex);
            Logger.getLogger(EditCustomersTable.class.getName()).log(Level.SEVERE, null, ex);
            return ex;
        }
    }

}
