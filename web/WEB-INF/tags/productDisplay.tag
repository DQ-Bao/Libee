<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<%@tag description="Display list of product" pageEncoding="UTF-8"%>
<%@attribute name="items" required="true"%>
<div class="col-lg-9 col-md-12">
    <div class="row pb-3">
        <div class="col-12 pb-1">
            <div class="d-flex align-items-center justify-content-between mb-4">
                <form action="${pageContext.request.contextPath}/Product" method="post">
                    <div class="input-group">
                        <input type="text" name="search_name" class="form-control" placeholder="Search by name">
                        <div class="input-group-append">
                            <span class="input-group-text bg-transparent text-primary">
                                <i class="fa fa-search"></i>
                            </span>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <c:forEach items="${product_list}" var="p">
            <t:productCard id="${p.id}" name="${p.name}" price="${p.price}" imgPath="${p.imagePath}"/>
        </c:forEach>
    </div>
</div>