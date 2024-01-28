<%-- 
    Document   : Publisher
    Created on : Jan 16, 2024, 4:28:50 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="Models.Publisher" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Libee</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Add one publisher:</h1>
        <form action="<%=request.getContextPath()%>/Publisher" method="post">
            <input type="hidden" name="action" value="add">
            <label for="pub_name">Name</label>
            <input type="text" id="pub_name" name="add_pub_name" placeholder="Enter publisher's name" required>
            <input type="submit" value="Add">
        </form>
        <hr>
        <h1>List of publishers:</h1>
        <table>
            <tr>
                <th>Name</th>
                <th>Options</th>
            </tr>
            <%
                ArrayList<Publisher> pubs = (ArrayList<Publisher>)request.getAttribute("publishers");
                for (Publisher pub : pubs) {
            %>
            <tr>
                <td><%=pub.getName()%></td>
                <td>
                    <a href="<%=request.getContextPath()%>/Publisher?action=update&id=<%=pub.getId()%>">Update</a>
                    <a href="<%=request.getContextPath()%>/Publisher?action=delete&id=<%=pub.getId()%>">Delete</a>
                </td>
            </tr>
            <%}%>
        </table>
        <hr>
        <% 
            Publisher pub = (Publisher)request.getAttribute("update_pub");
            if (pub != null) {
        %>
        <h1>Update publisher <%=pub.getName()%>:</h1>
        <form action="<%=request.getContextPath()%>/Publisher" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="update_pub_id" value="<%=pub.getId()%>">
            <input type="text" name="update_new_name" value="<%=pub.getName()%>" required>
            <input type="submit" value="Update">
        </form>
        <%}%>
    </body>
</html>
