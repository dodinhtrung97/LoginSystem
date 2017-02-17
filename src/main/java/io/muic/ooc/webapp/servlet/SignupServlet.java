/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = new UserService();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        Connection conn = userService.connectSQL();

        boolean userExist = userService.userExist(conn, username);
        if (!userExist){
            userService.createUser(conn, username, password, name);
        } else {
            System.out.println("This username already exists, please choose another");

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp");
            rd.include(request, response);
        }
    }
}
