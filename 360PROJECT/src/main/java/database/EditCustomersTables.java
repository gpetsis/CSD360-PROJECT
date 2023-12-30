/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikos Lasithiotakis
 */
public class EditCustomersTables {

    public SQLException createNewPet(Customer c) throws ClassNotFoundException, FileNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            String insertQuery = "INSERT INTO "
                    + " pets (pet_id,owner_id,name,type,breed,gender,birthyear,weight,description,photo) "
                    + " VALUES ("
                    + "'" + c.getPet_id() + "',"
                    + "'" + c.getOwner_id() + "',"
                    + "'" + c.getName() + "',"
                    + "'" + c.getType() + "',"
                    + "'" + c.getBreed() + "',"
                    + "'" + c.getGender() + "',"
                    + "'" + c.getBirthyear() + "',"
                    + "'" + c.getWeight() + "',"
                    + "'" + c.getDescription() + "',"
                    + "'" + c.getPhoto() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The customer was successfully added in the database.");
            /* Get the member id from the database and set it to the member */
            stmt.close();
            return null;

        } catch (SQLException ex) {
            Logger.getLogger(EditPetsTable.class.getName()).log(Level.SEVERE, null, ex);
            return ex;
        }
    }

}
