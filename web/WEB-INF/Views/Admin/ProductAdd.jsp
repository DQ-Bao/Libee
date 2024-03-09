<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<div class="row px-xl-5">
    <div class="col-lg-6 mb-5">
        <h3 class="mb-3">Add a Product</h3>
        <t:productForm action="Add"/>
    </div>
    <div class="col-lg-6 mb-5">
        <h3 class="mb-3">Add a Category</h3>
        <form action="${pageContext.request.contextPath}/Category" method="post">
            <input type="hidden" name="action" value="add-category">
            <div class="row mb-3">
                <input type="text" name="category-name" placeholder="Category Name" class="col-sm-10 form-control" required>
                <button type="submit" class="col-sm-2 btn btn-primary py-2 px-4">Add</button>
            </div>
        </form>
        <h3 class="mb-3">Add a SubCategory</h3>
        <form action="${pageContext.request.contextPath}/Category" method="post">
            <input type="hidden" name="action" value="add-subCategory">
            <table class="table mb-3">
                <tr>
                    <th>Name</th>
                    <th>Parent</th>
                </tr>
                <tr>
                    <td><input type="text" name="subCategory-name" placeholder="SubCategory Name" class="form-control" required></td>
                    <td><select name="subCategory-parentId" class="form-control" required>
                        <c:forEach items="${categories}" var="c">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select></td>
                </tr>
            </table>
            <button type="submit" class="col-sm-2 btn btn-primary py-2 px-4">Add</button>
        </form>
        <hr>
        <h3 class="mb-3">Add a Book Publisher</h3>
        <form action="${pageContext.request.contextPath}/Publisher" method="post">
            <input type="hidden" name="action" value="add-publisher">
            <div class="row mb-3">
                <input type="text" name="publisher-name" placeholder="Publisher Name" class="col-sm-10 form-control" required>
                <button type="submit" class="col-sm-2 btn btn-primary py-2 px-4">Add</button>
            </div>
        </form>
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