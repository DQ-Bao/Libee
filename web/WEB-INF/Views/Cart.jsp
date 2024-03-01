<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <div class="container-fluid pt-5">
        <div class="row px-xl-5">
            <!-- Items list -->
            <div class="col-lg-8 table-responsive mb-5">
                <table class="table table-bordered text-center mb-0">
                    <thead class="bg-secondary text-dark">
                        <tr>
                            <th>Products</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>SubTotal</th>
                            <th>Remove</th>
                        </tr>
                    </thead>
                    <tbody class="align-middle">
                        <c:forEach items="${sessionScope.cart.items}" var="item">
                            <tr>
                                <td class="align-middle"><img src="${pageContext.request.contextPath}/files/images/${item.product.imagePath}" alt="" style="width: 50px;">${item.product.name}</td>
                                <td class="align-middle">$${item.purchasePrice}</td>
                                <td class="align-middle">${item.quantity}</td>
                                <td class="align-middle">$${item.quantity * item.purchasePrice}</td>
                                <td class="align-middle">
                                    <form action="${pageContext.request.contextPath}/Cart" method="post">
                                        <input type="hidden" name="form-action" value="remove-item">
                                        <input type="hidden" name="product-id" value="${item.product.id}">
                                        <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-times"></i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                            <tr>
                                <td class="align-middle" colspan="3">Total</td>
                                <td class="align-middle">$${sessionScope.cart.total}</td>
                            </tr>
                    </tbody>
                </table>
                <button class="btn btn-block btn-primary my-3 py-3">Proceed To Checkout</button>
            </div>
        </div>
    </div>
</t:main>
