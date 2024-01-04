package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestType = request.getHeader("Request-Type");
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
        } else if (vehicleType.equals("motorcycle")) {
            tempArrayList = evt.getMotorcycles();
        }
        responseString = tempArrayList.toString();
        response.getWriter().write(responseString);
        System.out.println(responseString);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {
        String requestType = request.getHeader("Request-Type");
        if (requestType.equals("Add-Vehicle")) {
            addNewVehicle(request, response);
        } else if (requestType.equals("Return-Vehicle")) {
            returnVehicle(request, response);
        } else if (requestType.equals("Repair-Vehicle")) {
            repairVehicle(request, response);
        } else if (requestType.equals("Refresh-Unavailable")) {
            refreshUnavailable(request, response);
        }
    }

    void refreshUnavailable(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
        EditVehiclesTable vehiclesTable = new EditVehiclesTable();
        try {
            vehiclesTable.refreshUnavailable();
            response.setStatus(200);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
            response.setStatus(409);
            Logger.getLogger(VehicleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void repairVehicle(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
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
        } catch (JsonSyntaxException | ClassNotFoundException | SQLException ex) {
            System.out.println("Error : " + ex);
            response.setStatus(409);
        }

        System.out.println(requestString);
    }

    void returnVehicle(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
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
        } else if (request.getHeader("Vehicle-Type").equals("scooter")) {
            try {
                status = evt.addScooterFromJSON(requestString);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
                Logger.getLogger(VehicleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                status = evt.addMotorcycleFromJSON(requestString);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
