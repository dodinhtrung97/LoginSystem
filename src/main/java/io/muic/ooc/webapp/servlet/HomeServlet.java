/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.SecurityService;
import io.muic.ooc.webapp.service.UserService;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

public class HomeServlet extends HttpServlet {

    private SecurityService securityService;
    private UserService userService = new UserService();
    private Connection conn = userService.connectSQL();

    public void setSecurityManager(SecurityService securityService) {
        this.securityService = securityService;
    }

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
            rd.include(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("logoutbtn") != null) {
            securityService.logout(request, response);
        }

        if (request.getParameter("adduser") != null){
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp");
            rd.include(request, response);
        }

        if (request.getParameter("editbtn") != null){
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edit.jsp");
            rd.include(request, response);
        }

        if (request.getParameter("deletebtn") != null){
            String id = request.getParameter("userID");

            if (!id.equals(LoginServlet.currentUserID)) {

                userService.deleteUser(conn, id);

                /* For posting user information */
                String info1 = "Your id is: " + LoginServlet.currentUserID;
                String info2 = "Your name is: " + LoginServlet.currentUserName;

                request.setAttribute("info1", info1);
                request.setAttribute("info2", info2);
                /* Ends here */

                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
                rd.include(request, response);

            } else {

                /* For posting user information */
                String info1 = "Your id is: " + LoginServlet.currentUserID;
                String info2 = "Your name is: " + LoginServlet.currentUserName;

                request.setAttribute("info1", info1);
                request.setAttribute("info2", info2);
                /* Ends here */

                String error = "You can't remove your own account";
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
                rd.include(request, response);
            }
        }

    }
}
