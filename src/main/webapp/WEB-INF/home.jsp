<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>

<html>
    <body>
    <h2>Users List</h2>
    </body>

    <form method="get">

    <table border="2">
        <tr>
            <td>Username</td>
            <td>Password</td>
            <td>Name</td>
            <td>Action</td>
        </tr>

        <% try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/ooc";
            String username = "root";
            String password = "dodinhtrung";
            String query = "SELECT * FROM account";
            Connection conn = DriverManager.getConnection(url,username,password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
        %>
        <tr>
            <% String a = rs.getString("id"); %>
            <% String b = rs.getString("name"); %>
            <% String c = rs.getString("password"); %>

            <td><%=rs.getString("id") %></td>
            <td><%=rs.getString("password") %></td>
            <td><%=rs.getString("name") %></td>

            <td>
                <form action="/edit" method="get">
                    <input type="hidden" name="userID"  value="<%=a%>">
                    <input type="submit" name="editbtn" value="Edit">
                </form>

                <form action="/" method="get">
                    <input type="hidden" name="userID"  value="<%=a%>">
                    <input type="submit" name="deletebtn" value="Delete">

                </form>
            </td>
        </tr>
        <%}%>
    </table>

    <%
        rs.close();
        stmt.close();
        conn.close();
        } catch(Exception e){
        e.printStackTrace();
        }
    %>
    <br>
    <form action="/signup" method="get">
        <br>
        <input type="submit" name="adduser" value="Add User">
    </form>

    <form action="/" method="post">
        <br>
        <input type="submit" name="logoutbtn" value="Logout">
    </form>

</html>