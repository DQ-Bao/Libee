<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <div class="container-fluid pt-5">
        <div class="text-center mb-4">
            <h2 class="section-title px-5"><span class="px-2">Checkout</span></h2>
        </div>
        <div class="row justify-content-center">
            <div class="col-lg-7 mb-5">
                <div class="card border-secondary mb-5">
                    <div class="card-header bg-secondary border-0">
                        <h4 class="font-weight-semi-bold m-0">Order Total</h4>
                    </div>
                    <div class="card-body">
                        <h5 class="font-weight-medium mb-3">Products</h5>
                        <c:forEach items="${sessionScope.cart.items}" var="item">
                            <div class="d-flex justify-content-between">
                                <p>${item.product.name} x ${item.quantity}</p>
                                <p>$${item.purchasePrice * item.quantity}</p>
                            </div>
                        </c:forEach>
                        <hr class="mt-0">
                        <div class="d-flex justify-content-between mb-3 pt-1">
                            <h6 class="font-weight-medium">Subtotal</h6>
                            <h6 class="font-weight-medium">$${sessionScope.cart.total}</h6>
                        </div>
                    </div>
                    <div class="card-footer border-secondary bg-transparent">
                        <div class="d-flex justify-content-between mt-2">
                            <h5 class="font-weight-bold">Total</h5>
                            <h5 class="font-weight-bold">$${sessionScope.cart.total}</h5>
                        </div>
                        <form action="${pageContext.request.contextPath}/Checkout" method="post">
                            <button type="submit" class="btn btn-lg btn-block btn-primary font-weight-bold my-3 py-3">Place Order</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:main>
