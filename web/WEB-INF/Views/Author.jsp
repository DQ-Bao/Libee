<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <div class="container-fluid py-5">
        <div class="col">
            <h3 class="text-center font-weight-semi-bold mb-4">${author.name}</h3>
            <div class="row px-xl-5 justify-content-center">
                <div class="col-lg-5 pb-5">
                    <img class="w-100" src="${pageContext.request.contextPath}/files/images/${author.imagePath}" alt="${author.name}">
                </div>
                <div class="col-lg-5 pb-5">
                    <h4 class="font-weight-medium">About</h4>
                    <p>${author.description}</p>
                </div>
            </div>
            <h3 class="text-center font-weight-semi-bold mb-4">Their books</h3>
            <div class="row px-xl-5">
                <c:forEach items="${productList}" var="p">
                    <div class="col-lg-4 col-md-6 col-sm-12 pb-1">
                        <t:productCard id="${p.id}" type="${p.category.name}" name="${p.name}" price="${p.price}" imgPath="${p.imagePath}"/>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</t:main>
