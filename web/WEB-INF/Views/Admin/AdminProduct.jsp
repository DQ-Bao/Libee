<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <jsp:attribute name="script">
        <script>
            function showFormCategory(cname) {
                const forms = document.getElementsByClassName("dynamic-form-group");
                for (let i = 0; i < forms.length; i++) {
                    if (forms[i].id === ("form-" + cname)) {
                        forms[i].style.display = "block";
                    }
                    else {
                        forms[i].style.display = "none";
                    }
                }
                const categoryNameHiddenField = document.getElementById("product-categoryName");
                if (categoryNameHiddenField) {
                    categoryNameHiddenField.setAttribute("value", cname);
                }
            }
            function showChildSubCategories(option) {
                const childs = document.getElementById("subCategory-id").options;
                for (let i = 0; i < childs.length; i++) {
                    if (childs[i].getAttribute('title') === option.value) {
                        childs[i].style.display = "";
                    }
                    else {
                        childs[i].style.display = "none";
                    }
                }
            }
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid pt-5">
            <div class="text-center mb-4">
                <h2 class="section-title px-5"><span class="px-2">Product Manager</span></h2>
            </div>
            <jsp:include page="ProductAdd.jsp"/>
            <hr>
            <jsp:include page="ProductChange.jsp"/>
        </div>
    </jsp:body>
</t:main>
