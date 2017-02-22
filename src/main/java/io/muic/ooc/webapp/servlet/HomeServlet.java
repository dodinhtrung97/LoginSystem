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
            // do MVC in here
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
            rd.include(request, response);

            if (request.getParameter("deletebtn") != null){
                String id = request.getParameter("userID");

                if (!id.equals(LoginServlet.currentUser)) {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete this user?", "Confirmation", dialogButton);
                    if(dialogResult == 0) {
                        userService.deleteUser(conn, id);
                    } else {}

                } else {
                    String error = "You can't remove your own account";
                    request.setAttribute("error", error);
                    rd.include(request, response);
                }
            }

            if (request.getParameter("editbtn") != null){
                System.out.println("hello");
                System.out.println(request.getParameter("selectedID"));
                request.setAttribute("thisID", request.getParameter("selectedID"));
                rd = request.getRequestDispatcher("WEB-INF/edit.jsp");
                rd.include(request, response);
            }

            if (request.getParameter("info") != null){
                rd = request.getRequestDispatcher("WEB-INF/edit.jsp");
                rd.include(request, response);
            }
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

    }
}
