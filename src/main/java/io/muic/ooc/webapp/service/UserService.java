/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.service;

import java.sql.*;

public class UserService {

    public Connection connectSQL(){
        // MySQL Connection
        String myUrl = "jdbc:mysql://localhost:3306/";
        String myDatabase = "account";
        String myDriver = "com.mysql.jdbc.Driver";
        String rootUsername = "root";
        String rootPassword = "dodinhtrung";

        try {
            Class.forName(myDriver).newInstance();
            Connection conn = DriverManager.getConnection(myUrl + myDatabase, rootUsername, rootPassword);

            return conn;
        } catch (Exception e) {
            System.out.println("Something went wrong!!");
        }
        return null;
    }

    public void editUser(Connection conn, String username, String name, String password){

        String query;

        try {
            if (password.equals("")) {
                query = "update account set name = ?, password = ? where id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, username);
                // do update
                preparedStatement.executeUpdate();
            } else {
                query = "update account set name = ? where id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(3, username);
                // do update
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Can't edit user information");
        }


    }

    public void deleteUser(Connection conn, String id){

        String query = "delete from account where id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            // do update
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Can't delete user");
        }
    }

    public void createUser(Connection conn, String id, String password, String name){

        String query = "insert into account (id, password, name)"
                + " values (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            // do update
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Can't delete user");
        }
    }

    public boolean userExist(Connection conn, String id){

        String query = "SELECT * from account where id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            // do update
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.absolute(1)){
                return false;
            } return true;
        } catch (SQLException e) {
            System.out.println("Can't check");
        } return false;
    }
}
