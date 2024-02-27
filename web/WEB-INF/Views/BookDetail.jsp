<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <div class="container-fluid py-5">
        <div class="row px-xl-5">
            <div class="col-lg-4 pb-5">
                <img class="w-100" src="${pageContext.request.contextPath}/files/images/${book.imagePath}" alt="${book.name}">
            </div>
            <div class="col-lg-8 pb-5">
                <h3 class="font-weight-semi-bold">${book.name}</h3>
                <p class="mb-4">by 
                    <c:forEach items="${book.authors}" var="author" varStatus="loop">
                        <span><a href="${pageContext.request.contextPath}/Author?id=${author.authorId}">${author.authorName}</a></span><c:if test="${not loop.last}">, </c:if>
                    </c:forEach>
                </p>
                <h3 class="font-weight-semi-bold mb-4">$${book.price}</h3>
                <form action="${pageContext.request.contextPath}/Cart" method="post">
                    <div class="d-flex align-items-center mb-4 pt-2">
                        <input type="hidden" name="form-action" value="add-item">
                        <input type="hidden" name="product-id" value="${book.id}">
                        <input type="hidden" name="purchase-price" value="${book.price}">
                        <input type="number" name="quantity" class="form-control bg-secondary text-center mr-3" style="width: 130px;" value="1" min="1" max="${book.quantityInStock}">
                        <button type="submit" class="btn btn-primary px-3"><i class="fa fa-shopping-cart mr-1"></i> Add To Cart</button>
                    </div>
                </form>
                <h4 class="mb-3">Description</h4>
                <p>${book.description}</p>
            </div>
        </div>
    </div>
</t:main>
