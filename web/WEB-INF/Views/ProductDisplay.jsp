<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <div class="container-fluid pt-5">
        <div class="text-center mb-4">
            <h2 class="section-title px-5"><span class="px-2">List of products</span></h2>
        </div>
        <div class="row px-xl-5">
            <!-- Sidebar Start -->
            <div class="col-lg-3 col-md-12">
                <t:productFilter/>
            </div>
            <!-- Sidebar End -->
            <div class="col-lg-9 col-md-12">
                <div class="row pb-3">
                    <c:forEach items="${productList}" var="p">
                        <div class="col-lg-4 col-md-6 col-sm-12 pb-1">
                            <t:productCard id="${p.id}" type="${p.category.name}" name="${p.name}" price="${p.price}" imgPath="${p.imagePath}"/>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</t:main>