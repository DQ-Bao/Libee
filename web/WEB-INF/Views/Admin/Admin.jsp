<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <jsp:attribute name="script">
        <script>
            function showFormCategory(cname) {
                if (cname === "None") {
                    const forms = document.getElementsByClassName("dynamic-form-group");
                    for (let i = 0; i < forms.length; i++) {
                        forms[i].style.display = "none";
                    }
                    return;
                }
                const selectedForm = document.getElementById("form-" + cname);
                if (selectedForm) {
                    selectedForm.style.display = "block";
                    const categoryNameHiddenField = document.getElementById("product-categoryName");
                    if (categoryNameHiddenField) {
                        categoryNameHiddenField.setAttribute("value", cname);
                    }
                }
            }
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid pt-5">
            <div class="row px-xl-5">
                <div class="col">
                    <div class="nav nav-tabs justify-content-start border-primary mb-4">
                        <a class="nav-item nav-link active" data-toggle="tab" href="#product-pane">Product</a>
                        <a class="nav-item nav-link" data-toggle="tab" href="#tab-pane-2">Information</a>
                        <a class="nav-item nav-link" data-toggle="tab" href="#tab-pane-3">Reviews (0)</a>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="product-pane">
                            <jsp:include page="ProductManager.jsp"/>
                        </div>
                        <div class="tab-pane fade" id="tab-pane-2">

                        </div>
                        <div class="tab-pane fade" id="tab-pane-3">
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:main>
