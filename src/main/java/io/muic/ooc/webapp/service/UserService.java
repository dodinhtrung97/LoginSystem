/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.service;

import java.sql.*;

public class UserService {

    private final String DB_HOST = "localhost";
    private final String DB_PORT = "3306";
    private final String DB_NAME = "account";
    private final String MY_DRIVER = "com.mysql.jdbc.Driver";
    private final String MY_USERNAME = "root";
    private final String MY_PASSWORD = "dodinhtrung";

    private Statement s;

    public Connection connectSQL(){
        // MySQL Connection
        try {
            Class.forName(MY_DRIVER); // load driver
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":"
                    + DB_PORT + "/" + DB_NAME + "?" + "user=" + MY_USERNAME + "&"
                    + "password=" + MY_PASSWORD);
            s = conn.createStatement();

            return conn;
        } catch (ClassNotFoundException e) { //
            System.out.println("Driver not found " + e);
        } catch (SQLException e) {
            System.out.println("Connection not possible" + e);
        }

        return null;
    }

    public int hash(String data){
        int hash = 1;
        for (int i = 0; i < data.length(); i++) {
            hash = hash*2 + data.charAt(i);
        }
        return hash;
    }

    public void editUser(Connection conn, String username, String name, String password){

        String query;

        try {
            if (password.equals("")) {
                query = "update account set name = ? where id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, username);
                // do update
                preparedStatement.executeUpdate(query);
            } else if (name.equals("")){
                query = "update account set password = ? where id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, password);
                preparedStatement.setString(2, username);
                // do update
                preparedStatement.executeUpdate(query);
            } else {
                query = "UPDATE account SET password = ?, name = ? WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, username);

                // do update
                preparedStatement.executeUpdate(query);
            }
        } catch (SQLException e) {
            System.out.println("Can't edit user information" + e);
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
            System.out.println("Can't create user");
        }
    }

    public boolean userExist(Connection conn, String id){

        if (id == null) {return false;}

        String query = "SELECT * from account WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);

            // do update
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println("User Exist function");
                if (resultSet.getString("id").equals(id)){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Can't check");
        } return false;
    }
}
