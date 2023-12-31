/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.EditVehiclesTable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nikos Lasithiotakis
 */
@WebServlet(name = "VehicleServlet", urlPatterns = {"/VehicleServlet"})
public class VehicleServlet extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VehicleServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VehicleServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        PrintStream fileOut = new PrintStream(new File("C:\\Users\\Nikos Lasithiotakis\\Desktop\\CSD\\5ο Εξάμηνο\\ΗΥ360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        System.setOut(fileOut);
        System.out.println(request.getHeader("Vehicle-Type"));
        SQLException status = null;
        EditVehiclesTable evt = new EditVehiclesTable();
        String requestString = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = in.readLine();
        while (line != null) {
            requestString += line;
            line = in.readLine();
        }
        StringBuilder stringBuilder = new StringBuilder(requestString);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(",\"vId\":\"0\"}");
        requestString = stringBuilder.toString();
        System.out.println(requestString);
        if (request.getHeader("Vehicle-Type").equals("car")) {
            System.out.println("Car");

            try {
                status = evt.addCarFromJSON(requestString);
                System.out.println("Car");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
                Logger.getLogger(VehicleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (request.getHeader("Vehicle-Type").equals("bicycle")) {
            try {
                status = evt.addBicycleFromJSON(requestString);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);

                Logger.getLogger(VehicleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Scooter");
            try {
                status = evt.addScooterFromJSON(requestString);

                System.out.println("Scooter");

            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
                Logger.getLogger(VehicleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (status == null) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
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
