package com.parking.parkinglot.sevlets.users;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_USERS"}))
@WebServlet(name = "EditUser",value = "/EditUser")
public class EditUser extends HttpServlet {
    @Inject
    UserBean userBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        Long userId=Long.parseLong(request.getParameter("id"));
        UserDto user=userBean.findById(userId);
        request.setAttribute("user",user);
        request.setAttribute("userGroups",new String[]{"READ_CARS","WRITE_CARS","READ_USERS","WRITE_USERS","INVOICING"});
        request.getRequestDispatcher("/WEB-INF/pages/users/editUser.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String newPassword = request.getParameter("password");
        String[] userGroups = request.getParameterValues("user_groups");
        Long userId = Long.parseLong(request.getParameter("user_id"));

        if (newPassword != null && !newPassword.isEmpty()) {
            userBean.updateUser(userId, username, email, newPassword, Arrays.asList(userGroups));
        } else {
            userBean.updateUserWithoutPasswordChange(userId, username, email, Arrays.asList(userGroups));
        }
        response.sendRedirect(request.getContextPath() + "/Users");
    }

}