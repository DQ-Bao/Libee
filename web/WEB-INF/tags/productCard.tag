<%@tag description="The card that display a product" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="imgPath"%>
<%@attribute name="name"%>
<%@attribute name="price"%>
<%@attribute name="id" required="true"%>
<%@attribute name="type" required="true"%>
<div class="card product-item border-0 mb-4">
    <div class="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
        <a href="${pageContext.request.contextPath}/Product/${type}?id=${id}">
            <img class="img-fluid w-100" src="${pageContext.request.contextPath}/files/images/${imgPath}" alt="${name}">
        </a>
    </div>
    <div class="card-body border-left border-right text-center p-0 pt-4 pb-3">
        <a href="${pageContext.request.contextPath}/Product/${type}?id=${id}">
            <h6 class="text-truncate mb-3">${name}</h6>
        </a>
        <div class="d-flex justify-content-center">
            <h6>$${price}</h6>
        </div>
    </div>
</div>