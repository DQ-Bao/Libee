<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <div class="container-fluid pt-5">
        <div class="text-center mb-4">
            <h2 class="section-title px-5"><span class="px-2">User Manager</span></h2>
        </div>
        <h3 class="mb-3">Manage Roles</h3>
        <form action="${pageContext.request.contextPath}/Admin/User" method="post" id="change-role"><input type="hidden" name="form-action" value="change-role"></form>
        <form action="${pageContext.request.contextPath}/Admin/User" method="post" id="add-role"><input type="hidden" name="form-action" value="add-role"></form>
        <table class="table mb-4">
            <tr>
                <td><select form="change-role" name="role-id" class="form-control" required>
                    <c:forEach items="${roles}" var="r">
                        <option value="${r.id}">${r.name}</option>
                    </c:forEach>
                </select></td>
                <td><input type="text" form="change-role" name="update-role-name" placeholder="New Name" class="form-control"></td>
                <td>
                    <button form="change-role" type="submit" name="action" value="update" class="btn btn-primary">Update</button>
                    <button form="change-role" type="submit" name="action" value="delete" class="btn btn-primary">Delete</button>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input form="add-role" type="text" name="add-role-name" placeholder="Add a role" class="form-control" required></td>
                <td><button form="add-role" type="submit" class="btn btn-primary">Add</button></td>
            </tr>
        </table>
        <h3 class="mb-3">Manage User's roles</h3>
        <form action="${pageContext.request.contextPath}/Admin/User" method="post">
            <input type="hidden" name="form-action" value="manage-user-role">
            <button type="submit" class="btn btn-primary mb-3">Update</button>
            <table class="table">
                <tr>
                    <th>User</th>
                    <th>Role</th>
                </tr>
                <c:forEach items="${users}" var="u">
                    <input type="hidden" name="user-id" value="${u.id}">
                    <tr>
                        <td>${u.email}</td>
                        <td>
                            <div class="dropdown">
                                <a class="btn dropdown-toggle" data-toggle="dropdown">Role</a>
                                <div class="dropdown-menu w-100">
                                    <c:forEach items="${roles}" var="r">
                                        <div class="form-check mx-3">
                                            <input type="checkbox" name="user-role${u.id}" id="user-role-${u.id}-${r.id}" value="${r.id}" ${u.roles.contains(r) ? "checked" : ""} class="form-check-input">
                                            <label for="user-role-${u.id}-${r.id}" class="form-check-label">${r.name}</label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </div>
</t:main>