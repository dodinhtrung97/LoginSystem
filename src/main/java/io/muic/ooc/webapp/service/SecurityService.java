/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.service;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class SecurityService {

    private UserService userService = new UserService();
    private Connection userCredentials = userService.connectSQL();

    public boolean isAuthorized(HttpServletRequest request) {
        String id = (String) request.getSession().getAttribute("username");

        // do checking
        System.out.println("Security Service is authorized");
        return userService.userExist(userCredentials, id);
    }

    /* For testing */
    public void printDatabase(){
        String query = "select * from account";

        try {
            Statement st = userCredentials.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /* Ends here */
    
    public boolean authenticate(String id, String password, HttpServletRequest request) {

        String query = "select * from account where id  = ?";
        String passwordInDB;

        try {
            PreparedStatement preparedStatement = userCredentials.prepareStatement(query);
            preparedStatement.setString(1, id);
            // do update
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                passwordInDB = rs.getString("password");
            } else {
                passwordInDB = null;
            }

            boolean isMatched = StringUtils.equals(userService.hash(password), passwordInDB);

            if (isMatched) {
                request.getSession().setAttribute("username", id);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Can't authenticate user");
        }

        return false;
    }

    public String userName(String id){

        String query = "SELECT * from account WHERE id = ?";

        try {
            PreparedStatement preparedStatement = userCredentials.prepareStatement(query);
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println(id);

            while (resultSet.next()){
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Can't check");
        }

        return "";
    }
    
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().invalidate();
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
        rd.include(request, response);
    }
    
}
