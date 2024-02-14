<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<%@page import="java.util.ArrayList" %>
<%@page import="Models.Publisher" %>
<t:main>
    <h1>Add one publisher:</h1>
    <form action="${pageContext.request.getContextPath()}/Publisher" method="post">
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
        <c:forEach items="${publishers}" var="pub">
            <tr>
                <td>${pub.getName()}</td>
                <td>
                    <a href="${pageContext.request.getContextPath()}/Publisher?action=update&id=${pub.getId()}">Update</a>
                    <a href="${pageContext.request.getContextPath()}/Publisher?action=delete&id=${pub.getId()}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <hr>
    <c:if test="${update_pub != null}">
        <h1>Update publisher ${update_pub.getName()}:</h1>
        <form action="${pageContext.request.getContextPath()}/Publisher" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="update_pub_id" value="${update_pub.getId()}">
            <input type="text" name="update_new_name" value="${update_pub.getName()}" required>
            <input type="submit" value="Update">
        </form>
    </c:if>
</t:main>