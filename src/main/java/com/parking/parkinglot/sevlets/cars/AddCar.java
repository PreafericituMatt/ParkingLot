package com.parking.parkinglot.sevlets.cars;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.CarsBean;
import com.parking.parkinglot.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}))
@WebServlet(name="AddCar",value = "/AddCar")
public class AddCar extends HttpServlet{
    @Inject
    UserBean userBean;
    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{

        List<UserDto> users=userBean.findAllUsers();
        request.setAttribute("users",users);
        request.getRequestDispatcher("/WEB-INF/pages/cars/addCar.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String licensePlate=request.getParameter("license_plate");
        String parkingSpot=request.getParameter("parking_spot");
        Long userId=Long.parseLong(request.getParameter("owner_id"));

        carsBean.createCar(licensePlate,parkingSpot,userId);

        response.sendRedirect(request.getContextPath()+"/Cars");
    }
}