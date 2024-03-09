<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<div class="row px-xl-5" id="product-change">
    <div class="col-lg-12 mb-5">
        <h3 class="mb-3">Update/Delete a Product</h3>
        <form action="${pageContext.request.contextPath}/Admin/Product" method="post" class="mb-3">
            <div class="input-group">
                <input type="hidden" name="form-action" value="search-product">
                <input type="text" name="search-text" class="form-control" placeholder="Search for products">
                <div class="input-group-append">
                    <span class="input-group-text bg-transparent text-primary">
                        <i class="fa fa-search"></i>
                    </span>
                </div>
            </div>
        </form>
        <div class="table-responsive mb-5">
            <table class="table table-bordered text-center mb-0">
                <thead class="bg-secondary text-dark">
                    <tr>
                        <th>Products</th>
                        <th>Options</th>
                    </tr>
                </thead>
                <tbody class="align-middle">
                    <c:forEach items="${searchedProductList}" var="p">
                        <tr>
                            <td class="align-middle"><img src="${pageContext.request.contextPath}/files/images/${p.imagePath}" alt="${p.name}" style="width: 100px;">${p.name}</td>
                            <td class="align-middle">
                                <a href="${pageContext.request.contextPath}/Admin/Product?search=${param["search"]}&update-id=${p.id}&update-type=${p.category.name}#product-change" class="btn bg-primary text-white">Update</a>
                                <button form="product${p.id}-delete-form" type="submit" class="btn bg-primary text-white">Delete</button>
                                <form id="product${p.id}-delete-form" action="${pageContext.request.contextPath}/Admin/Product/Delete" method="post">
                                    <input type="hidden" name="product-id" value="${p.id}">
                                    <input type="hidden" name="product-imagePath" value="${p.imagePath}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${not empty updateItem}">
            <t:productForm action="Update" item="${updateItem}"/>
        </c:if>
    </div>
</div>
