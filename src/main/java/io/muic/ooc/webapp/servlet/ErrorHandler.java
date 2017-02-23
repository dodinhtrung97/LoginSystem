/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.SecurityService;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorHandler extends HttpServlet {

    private SecurityService securityService = new SecurityService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);

        if (authorized) {

            /* For posting user information */
            String info1 = "Your id is: " + LoginServlet.currentUserID;
            String info2 = "Your name is: " + LoginServlet.currentUserName;

            request.setAttribute("info1", info1);
            request.setAttribute("info2", info2);
            /* Ends here */

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
            rd.forward(request, response);
        }
    }
}
