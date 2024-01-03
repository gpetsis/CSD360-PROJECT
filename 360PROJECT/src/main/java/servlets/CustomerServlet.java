/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.EditCustomersTable;
import database.EditRentsTable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.io.PrintStream;
>>>>>>> 6881db9df95a1e100e2f21a49c8b0180885e8211
import java.sql.Connection;
import java.sql.ResultSet;
=======
>>>>>>> parent of 9ab0c80 ([Giannis] Update service vehicle)
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/CustomerServlet"})
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.io.FileNotFoundException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {
        String requestType = request.getHeader("Request-Type");
//        PrintStream fileOut = new PrintStream(new File("C:\\Users\\Nikos Lasithiotakis\\Desktop\\CSD\\5ο Εξάμηνο\\ΗΥ360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
//        System.setOut(fileOut);
        if (requestType.equals("Add-Customer")) {
            addCustomer(request, response);
        } else if (requestType.equals("Rent")) {
            try {
                rent(request, response);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CustomerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

<<<<<<< HEAD
    void rent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException, ClassNotFoundException, SQLException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        int count = 0;
        boolean temp = false;
<<<<<<< HEAD
=======
        PrintStream fileOut = new PrintStream(new File("C:\\Users\\Nikos Lasithiotakis\\Desktop\\CSD\\5ο Εξάμηνο\\ΗΥ360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        System.setOut(fileOut);
>>>>>>> 6881db9df95a1e100e2f21a49c8b0180885e8211
=======
    void rent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException, ClassNotFoundException {
>>>>>>> parent of 9ab0c80 ([Giannis] Update service vehicle)
        String requestString = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = in.readLine();
        SQLException status;
        EditRentsTable ert = new EditRentsTable();
        while (line != null) {
            requestString += line;
            line = in.readLine();
        }
        System.out.println(requestString);
<<<<<<< HEAD
        String query = "SELECT COUNT(*) AS count FROM rents WHERE vId=" + request.getHeader("vId");
        stmt = con.createStatement();

        rs = stmt.executeQuery(query);
        if (rs.next()) {
            count = rs.getInt("count");
            System.out.println("Count: " + count);
        }
        if (count == 0) {
            status = ert.addRentFromJSON(requestString);
<<<<<<< HEAD
            Rent rent = ert.jsonToRent(requestString);
            double cost = rent.getCost();
            EditCustomersTable customersTable = new EditCustomersTable();
            customersTable.chargeCustomer(cost, rent.getName());
=======
>>>>>>> 6881db9df95a1e100e2f21a49c8b0180885e8211
        } else {
            temp = true;
        }
        if (status == null && temp == false) {
=======
        String query = "SELECT COUNT(*) AS count FROM rents WHERE vId=";
        status = ert.addRentFromJSON(requestString);
        if (status == null) {
>>>>>>> parent of 9ab0c80 ([Giannis] Update service vehicle)
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
    }

    void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, FileNotFoundException {
        String requestString = "";

        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = in.readLine();
        while (line != null) {
            requestString += line;
            line = in.readLine();
        }
        EditCustomersTable customersTable = new EditCustomersTable();
        try {
            customersTable.addCustomerFromJSON(requestString);
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            response.setStatus(409);
            Logger.getLogger(CustomerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setStatus(200);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
