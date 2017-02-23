/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.SecurityService;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.muic.ooc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

public class LoginServlet extends HttpServlet {

    private SecurityService securityService;
    public static String currentUserID;
    public static String currentUserName;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // do login post logic
        // extract username and password from request
        String id = request.getParameter("username");
        String password = request.getParameter("password");

        if (!StringUtils.isBlank(id) && !StringUtils.isBlank(password)) {
            if (securityService.authenticate(id, password, request)) {
                /* For posting user information */
                String name = securityService.userName(id);
                String info1 = "Your id is: " + id;
                String info2 = "Your name is: " + name;

                request.setAttribute("info1", info1);
                request.setAttribute("info2", info2);
                /* Ends here */

                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
                rd.include(request, response);

                currentUserID = id;
                currentUserName = name;

            } else {
                String error = "Wrong username or password.";
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                rd.include(request, response);
            }
        } else {
            String error = "Username or password is missing.";
            request.setAttribute("error", error);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
            rd.include(request, response);
        }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
