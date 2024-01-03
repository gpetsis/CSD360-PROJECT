/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.EditVehiclesTable;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String requestType = request.getHeader("Request-Type");
//        PrintStream fileOut = new PrintStream(new File("C:\\Users\\Nikos Lasithiotakis\\Desktop\\CSD\\5ο Εξάμηνο\\ΗΥ360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        System.setOut(fileOut);
        if (requestType.equals("Search")) {
            try {
                searchVehicles(request, response);
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                System.out.println("Error: " + ex);
                Logger.getLogger(VehicleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void searchVehicles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        String vehicleType = request.getHeader("Vehicle-Type");
        EditVehiclesTable evt = new EditVehiclesTable();
        Gson gson = new Gson();
        ArrayList<String> tempArrayList = new ArrayList<String>();
        String responseString;
        if (vehicleType.equals("vehicle")) {
            tempArrayList = evt.getVehicles();
        } else if (vehicleType.equals("car")) {
            tempArrayList = evt.getCars();
        } else if (vehicleType.equals("scooter")) {
            tempArrayList = evt.getScooters();
        } else if (vehicleType.equals("bicycle")) {
            tempArrayList = evt.getBicycles();
        }
        responseString = tempArrayList.toString();
        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        System.setOut(fileOut);

        response.getWriter().write(responseString);
        System.out.println(responseString);
        return;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        String requestType = request.getHeader("Request-Type");
        if (requestType.equals("Add-Vehicle")) {
            addNewVehicle(request, response);
        } else if (requestType.equals("Return-Vehicle")) {
//            PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
//            System.setOut(fileOut);

            returnVehicle(request, response);
        }
    }

<<<<<<< HEAD
<<<<<<< HEAD
    void repairVehicle(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        System.setOut(fileOut);

        String requestString = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = in.readLine();
        while (line != null) {
            requestString += line;
            line = in.readLine();
        }

        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(requestString).getAsJsonObject();

            int vId = jsonObject.get("vId").getAsInt();
            String repairType = jsonObject.get("repairType").getAsString();

            EditVehiclesTable vehiclesTable = new EditVehiclesTable();
            if (repairType.equals("service")) {
                vehiclesTable.serviceVehicle(vId);
            } else if (repairType.equals("repair")) {
                vehiclesTable.repairVehicle(vId);
            }

            System.out.println(vId + repairType);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(requestString);
    }

=======
>>>>>>> 6881db9df95a1e100e2f21a49c8b0180885e8211
=======
>>>>>>> parent of 9ab0c80 ([Giannis] Update service vehicle)
    void returnVehicle(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        System.setOut(fileOut);

        String vId = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = in.readLine();
        while (line != null) {
            vId += line;
            line = in.readLine();
        }

        System.out.println(vId);

        try {
            EditVehiclesTable vehiclesTable = new EditVehiclesTable();
            vehiclesTable.returnVehicle(vId);
            response.setStatus(200);
        } catch (FileNotFoundException | ClassNotFoundException | SQLException | NoSuchElementException ex) {
            System.out.println("Error: " + ex);
            if (ex instanceof NoSuchElementException) {
                response.setStatus(404);
            } else {
                response.setStatus(409);
            }
            Logger.getLogger(VehicleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void addNewVehicle(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
        //        PrintStream fileOut = new PrintStream(new File("C:\\Users\\Nikos Lasithiotakis\\Desktop\\CSD\\5ο Εξάμηνο\\ΗΥ360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
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
//        StringBuilder stringBuilder = new StringBuilder(requestString);
//        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//        stringBuilder.append(",\"vId\":\"0\"}");
//        requestString = stringBuilder.toString();
        System.out.println(requestString);
        if (request.getHeader("Vehicle-Type").equals("car")) {
            try {
                status = evt.addCarFromJSON(requestString);
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
            try {
                status = evt.addScooterFromJSON(requestString);
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
