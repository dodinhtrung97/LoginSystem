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
import org.apache.commons.lang.StringUtils;

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

        System.out.println("SignupServlet");
        boolean userExist = userService.userExist(conn, username);

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(name)) {
            String error = "Please don't leave any blank field!";
            request.setAttribute("error", error);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp");
            rd.include(request, response);
        } else if (!userExist){
            /* For posting user information */
            String info1 = "Your id is: " + LoginServlet.currentUserID;
            String info2 = "Your name is: " + LoginServlet.currentUserName;

            request.setAttribute("info1", info1);
            request.setAttribute("info2", info2);
            /* Ends here */

            userService.createUser(conn, username, password, name);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
            rd.include(request, response);
        } else {
            String error = "This username already exists, please choose another";
            request.setAttribute("error", error);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp");
            rd.include(request, response);
        }
    }
}
