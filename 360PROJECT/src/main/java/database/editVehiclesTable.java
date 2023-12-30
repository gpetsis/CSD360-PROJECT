/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giann
 */
public class editVehiclesTable {

    public SQLException addVehicleFromJSON(String json) throws ClassNotFoundException {
        PetOwner user = jsonToPetOwner(json);
        return addNewPetOwner(user);
    }

    public PetOwner jsonToPetOwner(String json) {
        Gson gson = new Gson();

        PetOwner user = gson.fromJson(json, PetOwner.class);
        return user;
    }

    public String petOwnerToJSON(PetOwner user) {
        Gson gson = new Gson();

        String json = gson.toJson(user, PetOwner.class);
        return json;
    }

    public void updatePetOwner(String username, String personalpage) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE petowners SET personalpage='" + personalpage + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
    }

    public ArrayList<PetOwner> getOwners() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<PetOwner> owners = new ArrayList<PetOwner>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                PetOwner owner = gson.fromJson(json, PetOwner.class);
                owners.add(owner);
            }
            return owners;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public PetOwner databaseToPetOwners(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetOwner user = gson.fromJson(json, PetOwner.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String databasePetOwnerToJSON(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            return json;
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
                + "(vId INTEGER not NULL,"
                + "    brand VARCHAR(15) not null,"
                + "    model VARCHAR(15) not null,	"
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
    public SQLException addNewPetOwner(PetOwner user) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " petowners (username,email,password,firstname,lastname,birthdate,gender,country,city,address,personalpage,"
                    + "job,telephone,lat,lon)"
                    + " VALUES ("
                    + "'" + user.getUsername() + "',"
                    + "'" + user.getEmail() + "',"
                    + "'" + user.getPassword() + "',"
                    + "'" + user.getFirstname() + "',"
                    + "'" + user.getLastname() + "',"
                    + "'" + user.getBirthdate() + "',"
                    + "'" + user.getGender() + "',"
                    + "'" + user.getCountry() + "',"
                    + "'" + user.getCity() + "',"
                    + "'" + user.getAddress() + "',"
                    + "'" + user.getPersonalpage() + "',"
                    + "'" + user.getJob() + "',"
                    + "'" + user.getTelephone() + "',"
                    + "'" + user.getLat() + "',"
                    + "'" + user.getLon() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The pet owner was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();
            return null;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
            Logger.getLogger(EditPetOwnersTable.class.getName()).log(Level.SEVERE, null, ex);
            return ex;
        }
    }

}
