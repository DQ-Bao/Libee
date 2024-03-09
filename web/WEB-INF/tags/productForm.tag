<%@tag description="Form to add/update product" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="action" required="true"%>
<%@attribute name="item" type="Models.Product"%>
<form action="${pageContext.request.contextPath}/Admin/Product/${action}" method="post" enctype="multipart/form-data">
    <input type="hidden" name="product-id" value="${item.id}">
    <input type="hidden" id="product-categoryName" name="product-categoryName" value="">
    <input type="hidden" name="product-old-imagePath" value="${item.imagePath}">
    <div class="form-group">
        <input type="text" name="product-name" placeholder="Product Name" class="form-control mb-3" value="${item.name}" required>
        <input type="number" name="product-price" step="0.01" min="0" placeholder="Price" class="form-control mb-3" value="${item.price}" required>
        <textarea name="product-description" rows="5" cols="50" placeholder="Description" class="form-control mb-3">${item.description}</textarea>
        <div class="row mb-3">
            <label for="product-categoryId" class="col-sm-2 col-form-label">Category</label>
            <select name="product-categoryId" id="product-categoryId" onchange="showFormCategory(this.options[this.selectedIndex].text)" class="col-sm-10 form-control" required>
                <option value="-1">None</option>
                <c:forEach items="${categories}" var="c">
                    <option value="${c.id}" ${item.category.id eq c.id ? "selected" : ""}>${c.name}</option>
                </c:forEach>
            </select>
        </div>
        <input type="number" name="product-quantityInStock" min="1" placeholder="Quantity In Stock" class="form-control mb-3" value="${item.quantityInStock}" required>
        <div class="row mb-3">
            <label for="product-image" class="col-sm-2 col-form-label">Image</label>
            <input type="file" name="product-image" id="product-image" accept="image/jpeg" placeholder="Product Thumbnail" class="col-sm-10 form-control">
        </div>
    </div>
    <div class="form-group dynamic-form-group" id="form-Book" style="display: none;">
        <input type="text" name="book-isbn10" placeholder="ISBN10" class="form-control mb-3" value="${item.isbn10}" required>
        <input type="text" name="book-isbn13" placeholder="ISBN13" class="form-control mb-3" value="${item.isbn13}" required>
        <input type="text" name="book-language" placeholder="Language" class="form-control mb-3" value="${item.language}" required>
        <div class="row mb-3">
            <label for="book-publisherId" class="col-sm-2 col-form-label">Publisher</label>
            <select name="book-publisherId" id="book-publisherId" class="col-sm-10 form-control" required>
                <c:forEach items="${publishers}" var="pub">
                    <option value="${pub.id}" ${item.publisher.id eq pub.id ? "selected" : ""}>${pub.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="row mb-3">
            <label for="book-publicationDate" class="col-sm-3 col-form-label">Publication Date</label>
            <input type="date" name="book-publicationDate" id="book-publicationDate" class="col-sm-9 form-control" value="${item.publicationDate.toLocalDate()}" required>
        </div>
        <div class="row mb-3">
            <div class="col-sm-6 dropdown">
                <a class="btn dropdown-toggle" data-toggle="dropdown">Authors</a>
                <div class="dropdown-menu w-100">
                    <c:forEach items="${authors}" var="a" varStatus="loop">
                        <c:set var="hasAuthor" value="false"/>
                        <c:forEach items="${item.authors}" var="ia">
                            <c:if test="${ia.authorId eq a.id}">
                                <c:set var="hasAuthor" value="true"/>
                            </c:if>
                        </c:forEach>
                        <div class="form-check mx-3">
                            <input type="checkbox" name="book-author" id="book-author${loop.index}" value="${a.id}" ${hasAuthor eq true ? "checked" : ""} class="form-check-input">
                            <label for="book-author${loop.index}" class="form-check-label">${a.name}</label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="col-sm-6 dropdown">
                <a class="btn dropdown-toggle" data-toggle="dropdown">Genres</a>
                <div class="dropdown-menu w-100">
                    <c:forEach items="${genres}" var="g" varStatus="loop">
                        <c:set var="hasGenre" value="false"/>
                        <c:set var="primary" value="false"/>
                        <c:forEach items="${item.genres}" var="ig">
                            <c:if test="${ig.subCategoryId eq g.id}">
                                <c:set var="hasGenre" value="true"/>
                                <c:set var="primary" value="${ig.primary}"/>
                            </c:if>
                        </c:forEach>
                        <div class="d-flex justify-content-between">
                            <div class="form-check-inline ml-3">
                                <input type="checkbox" name="book-genre" id="book-genre${loop.index}" value="${g.id}" ${hasGenre eq true ? "checked" : ""} class="form-check-input">
                                <label for="book-genre${loop.index}" class="form-check-label">${g.name}</label>
                            </div>
                            <div class="form-check-inline">
                                <input type="radio" name="book-genre-primary" value="${g.id}" ${primary eq true ? "checked" : ""} class="form-check-input">
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-primary py-2 px-4">Submit</button>
</form>
