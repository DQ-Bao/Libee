<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="text-center mb-4">
    <h2 class="section-title px-5"><span class="px-2">Product Manager</span></h2>
</div>
<div class="row px-xl-5">
    <div class="col-lg-6 mb-5">
        <h3 class="mb-3">Add a Product</h3>
        <form action="${pageContext.request.contextPath}/Product" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add-product">
            <input type="hidden" id="product-categoryName" name="product-categoryName" value="">
            <div class="form-group">
                <input type="text" name="product-name" placeholder="Product Name" class="form-control mb-3" required>
                <input type="number" name="product-price" step="0.01" min="0" placeholder="Price" class="form-control mb-3" required>
                <textarea name="product-description" rows="5" cols="50" placeholder="Description" class="form-control mb-3"></textarea>
                <div class="row mb-3">
                    <label for="product-categoryId" class="col-sm-2 col-form-label">Category</label>
                    <select name="product-categoryId" id="product-categoryId" onchange="showFormCategory(this.options[this.selectedIndex].text)" class="col-sm-10 form-control" required>
                        <option value="-1">None</option>
                        <c:forEach items="${categories}" var="c">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <input type="number" name="product-quantityInStock" min="1" placeholder="Quantity In Stock" class="form-control mb-3" required>
                <div class="row mb-3">
                    <label for="product-image" class="col-sm-2 col-form-label">Image</label>
                    <input type="file" name="product-image" id="product-image" accept="image/jpeg, image/png" placeholder="Product Thumbnail" class="col-sm-10 form-control">
                </div>
            </div>
            <div class="form-group dynamic-form-group" id="form-Book" style="display: none;">
                <input type="text" name="book-isbn10" placeholder="ISBN10" class="form-control mb-3" required>
                <input type="text" name="book-isbn13" placeholder="ISBN13" class="form-control mb-3" required>
                <input type="text" name="book-language" placeholder="Language" class="form-control mb-3" required>
                <div class="row mb-3">
                    <label for="book-publisherId" class="col-sm-2 col-form-label">Publisher</label>
                    <select name="book-publisherId" id="book-publisherId" class="col-sm-10 form-control" required>
                        <c:forEach items="${publishers}" var="pub">
                            <option value="${pub.id}">${pub.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="row mb-3">
                    <label for="book-publicationDate" class="col-sm-3 col-form-label">Publication Date</label>
                    <input type="date" name="book-publicationDate" id="book-publicationDate" class="col-sm-9 form-control" required>
                </div>
                <div class="row mb-3">
                    <div class="col-sm-6 dropdown">
                        <a class="btn dropdown-toggle" data-toggle="dropdown">Authors</a>
                        <div class="dropdown-menu w-100">
                            <c:forEach items="${authors}" var="a" varStatus="loop">
                                <div class="form-check mx-3">
                                    <input type="checkbox" name="book-author" id="book-author${loop.index}" value="${a.id}" class="form-check-input">
                                    <label for="book-author${loop.index}" class="form-check-label">${a.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-sm-6 dropdown">
                        <a class="btn dropdown-toggle" data-toggle="dropdown">Genres</a>
                        <div class="dropdown-menu w-100">
                            <c:forEach items="${genres}" var="g" varStatus="loop">
                                <div class="d-flex justify-content-between">
                                    <div class="form-check-inline ml-3">
                                        <input type="checkbox" name="book-genre" id="book-genre${loop.index}" value="${g.id}" class="form-check-input">
                                        <label for="book-genre${loop.index}" class="form-check-label">${g.name}</label>
                                    </div>
                                    <div class="form-check-inline">
                                        <input type="radio" name="book-genre-primary" value="${g.id}" class="form-check-input">
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-primary py-2 px-4">Add</button>
        </form>
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
                <input type="file" name="author-image" id="author-image" accept="image/jpeg, image/png" class="col-sm-10 form-control">
            </div>
            <button type="submit" class="col-sm-2 btn btn-primary py-2 px-4">Add</button>
        </form>
    </div>
</div>
