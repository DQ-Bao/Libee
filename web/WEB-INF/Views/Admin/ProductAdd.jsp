<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<div class="row px-xl-5">
    <div class="col-lg-6 mb-5">
        <h3 class="mb-3">Add a Product</h3>
        <t:productForm action="Add"/>
    </div>
    <div class="col-lg-6 mb-5">
        <h3 class="mb-3">Manage Categories</h3>
        <form action="${pageContext.request.contextPath}/Category" method="post" id="add-category"><input type="hidden" name="form-action" value="add-category"></form>
        <form action="${pageContext.request.contextPath}/Category" method="post" id="change-category"><input type="hidden" name="form-action" value="change-category"></form>
        <table class="table mb-4">
            <tr>
                <td><select form="change-category" name="change-category-id" class="form-control" required>
                    <c:forEach items="${categories}" var="c">
                        <option value="${c.id}">${c.name}</option>
                    </c:forEach>
                </select></td>
                <td><input type="text" form="change-category" name="update-category-name" placeholder="New Name" class="form-control"></td>
                <td>
                    <button form="change-category" type="submit" name="action" value="update" class="btn btn-primary">Update</button>
                    <button form="change-category" type="submit" name="action" value="delete" class="btn btn-primary">Delete</button>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input form="add-category" type="text" name="add-category-name" placeholder="Add a Category" class="form-control" required></td>
                <td><button form="add-category" type="submit" class="btn btn-primary">Add</button></td>
            </tr>
        </table>
        <h3 class="mb-3">Manage SubCategories</h3>
        <form action="${pageContext.request.contextPath}/Category" method="post" id="add-subCategory"><input type="hidden" name="form-action" value="add-subCategory"></form>
        <form action="${pageContext.request.contextPath}/Category" method="post" id="change-subCategory"><input type="hidden" name="form-action" value="change-subCategory"></form>
        <table class="table mb-4">
            <tr>
                <th>Child</th>
                <th>Name</th>
                <th>Parent</th>
                <th>Options</th>
            </tr>
            <tr>
                <td><select form="change-subCategory" id="subCategory-id" name="change-subCategory-id" class="form-control" required>
                    <c:forEach items="${subCategories}" var="sc">
                        <option value="${sc.id}" title="${sc.parentId}">${sc.name}</option>
                    </c:forEach>
                </select></td>
                <td><input type="text" form="change-subCategory" name="update-subCategory-name" placeholder="New Name" class="form-control"></td>
                <td><select form="change-subCategory" name="update-subCategory-parentId" onchange="showChildSubCategories(this[this.selectedIndex])" class="form-control" required>
                    <c:forEach items="${categories}" var="c">
                        <option value="${c.id}">${c.name}</option>
                    </c:forEach>
                </select></td>
                <td>
                    <button form="change-subCategory" type="submit" name="action" value="update" class="btn btn-primary">Update</button>
                    <button form="change-subCategory" type="submit" name="action" value="delete" class="btn btn-primary">Delete</button>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input form="add-subCategory" type="text" name="add-subCategory-name" placeholder="Add a SubCategory" class="form-control" required></td>
                <td><select form="add-subCategory" name="add-subCategory-parentId" class="form-control" required>
                    <c:forEach items="${categories}" var="c">
                        <option value="${c.id}">${c.name}</option>
                    </c:forEach>
                </select></td>
                <td><button form="add-subCategory" type="submit" class="btn btn-primary">Add</button></td>
            </tr>
        </table>
        <hr>
        <h3 class="mb-3">Manage Book Publishers</h3>
        <form action="${pageContext.request.contextPath}/Publisher" method="post" id="add-publisher"><input type="hidden" name="form-action" value="add-publisher"></form>
        <form action="${pageContext.request.contextPath}/Publisher" method="post" id="change-publisher"><input type="hidden" name="form-action" value="change-publisher"></form>
        <table class="table mb-4">
            <tr>
                <td><select form="change-publisher" name="change-publisher-id" class="form-control" required>
                    <c:forEach items="${publishers}" var="pub">
                        <option value="${pub.id}">${pub.name}</option>
                    </c:forEach>
                </select></td>
                <td><input type="text" form="change-publisher" name="update-publisher-name" placeholder="New Name" class="form-control"></td>
                <td>
                    <button form="change-publisher" type="submit" name="action" value="update" class="btn btn-primary">Update</button>
                    <button form="change-publisher" type="submit" name="action" value="delete" class="btn btn-primary">Delete</button>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input form="add-publisher" type="text" name="add-publisher-name" placeholder="Add a Publisher" class="form-control" required></td>
                <td><button form="add-publisher" type="submit" class="btn btn-primary">Add</button></td>
            </tr>
        </table>
        <h3 class="mb-3">Add a Book Author</h3>
        <form action="${pageContext.request.contextPath}/Author" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add-author">
            <input type="text" name="author-name" placeholder="Author Name" class="col-sm-10 form-control mb-3" required>
            <textarea name="author-description" rows="5" cols="50" placeholder="Description" class="form-control mb-3"></textarea>
            <div class="row mb-3">
                <label for="author-image" class="col-sm-2 col-form-label">Image</label>
                <input type="file" name="author-image" id="author-image" accept="image/jpeg" class="col-sm-10 form-control">
            </div>
            <button type="submit" class="col-sm-2 btn btn-primary py-2 px-4">Add</button>
        </form>
    </div>
</div>