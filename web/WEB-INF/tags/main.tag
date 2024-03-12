<%@tag description="Page layout" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="head" fragment="true"%>
<%@attribute name="nav" fragment="true"%>
<%@attribute name="script" fragment="true"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Libee</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/files/images/logo.png">
        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <!-- Customized Bootstrap Stylesheet -->
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row align-items-center py-3 px-xl-5">
                <div class="col-lg-3 d-none d-lg-block">
                    <a href="${pageContext.request.contextPath}" class="text-decoration-none">
                        <h1 class="m-0 display-5 font-weight-semi-bold"><span class="text-primary font-weight-bold border px-3 mr-1"><img src="${pageContext.request.contextPath}/files/images/logo.png" style="width: 34px; height: 34px;"></span>Libee</h1>
                    </a>
                </div>
                <div class="col-lg-6 col-6 text-left">
                    <form action="${pageContext.request.contextPath}/Product" method="post">
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
                </div>
                <c:if test="${not empty sessionScope.cart}">
                    <div class="col-lg-3 col-6 text-right">
                        <a href="${pageContext.request.contextPath}/Cart" class="btn border">
                            <i class="fas fa-shopping-cart text-primary"></i>
                            <span class="badge">${sessionScope.cart.items.size()}</span>
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="container-fluid mb-5">
            <div class="row border-top px-xl-5">
                <div class="col-lg-3 d-none d-lg-block">
                    <a class="btn shadow-none d-flex align-items-center justify-content-between bg-primary text-white w-100" data-toggle="collapse" href="#navbar-vertical" style="height: 65px; margin-top: -1px; padding: 0 30px;">
                        <h6 class="m-0">Categories</h6>
                        <i class="fa fa-angle-down text-dark"></i>
                    </a>
                    <nav class="collapse position-absolute navbar navbar-vertical navbar-light align-items-start p-0 border border-top-0 border-bottom-0 bg-light" id="navbar-vertical" style="width: calc(100% - 30px); z-index: 100;">
                        <div class="navbar-nav w-100">
                            <c:forEach items="${navCatMap}" var="entry">
                                <c:choose>
                                    <c:when test="${entry.value.size() le 0}">
                                        <a href="${pageContext.request.contextPath}/Product/${entry.key}" class="nav-item nav-link">${entry.key}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="nav-item dropdown">
                                            <a href="#" class="nav-link" data-toggle="dropdown">${entry.key} <i class="fa fa-angle-down float-right mt-1"></i></a>
                                            <div class="dropdown-menu bg-secondary border-0 rounded-0 w-100 m-0">
                                                <c:forEach items="${entry.value}" var="sc">
                                                    <a href="${pageContext.request.contextPath}/Product/${entry.key}/${sc.name}" class="dropdown-item">${sc.name}</a>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </nav>
                </div>
                <div class="col-lg-9">
                    <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 py-lg-0 px-0">
                        <a href="${pageContext.request.contextPath}" class="text-decoration-none d-block d-lg-none">
                            <h1 class="m-0 display-5 font-weight-semi-bold"><span class="text-primary font-weight-bold border px-3 mr-1"><img src="${pageContext.request.contextPath}/files/images/logo.png" style="width: 34px; height: 34px;"></span>Libee</h1>
                        </a>
                        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                            <div class="navbar-nav mr-auto py-0">
                                <a href="${pageContext.request.contextPath}" class="nav-item nav-link">Home</a>
                                <a href="${pageContext.request.contextPath}/Product" class="nav-item nav-link">Product</a>
                                <c:if test="${sessionScope.isAdmin}">
                                    <div class="nav-item dropdown">
                                        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Admin</a>
                                        <div class="dropdown-menu rounded-0 m-0">
                                            <a href="${pageContext.request.contextPath}/Admin/Product" class="dropdown-item">Product</a>
                                            <a href="${pageContext.request.contextPath}/Admin/User" class="dropdown-item">User</a>
                                        </div>
                                    </div>
                                </c:if>
                                <jsp:invoke fragment="nav"/>
                            </div>
                            <div class="navbar-nav ml-auto py-0">
                                <c:choose>
                                    <c:when test="${empty sessionScope.user}">
                                        <a href="${pageContext.request.contextPath}/Login" class="nav-item nav-link">Login</a>
                                        <a href="${pageContext.request.contextPath}/Register" class="nav-item nav-link">Register</a>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="nav-item dropdown">
                                            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">${sessionScope.user.firstName} ${sessionScope.user.lastName}</a>
                                            <div class="dropdown-menu rounded-0 m-0">
                                                <a href="${pageContext.request.contextPath}/User" class="dropdown-item">Account</a>
                                                <a href="${pageContext.request.contextPath}/Logout" class="dropdown-item">Logout</a>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
        <jsp:doBody/>
        <div class="container-fluid bg-secondary text-dark mt-5 pt-5">
            <div class="row px-xl-5 pt-5">
                <div class="col-lg-4 col-md-12 mb-5 pr-3 pr-xl-5">
                    <a href="${pageContext.request.contextPath}" class="text-decoration-none">
                        <h1 class="mb-4 display-5 font-weight-semi-bold"><span class="text-primary font-weight-bold border border-white px-3 mr-1"><img src="${pageContext.request.contextPath}/files/images/logo.png" style="width: 34px; height: 34px;"></span>Libee</h1>
                    </a>
                    <p>Dolore erat dolor sit lorem vero amet. Sed sit lorem magna, ipsum no sit erat lorem et magna ipsum dolore amet erat.</p>
                    <p class="mb-2"><i class="fa fa-map-marker-alt text-primary mr-3"></i>FPT University, Hoa Lac, Thach That, Ha Noi, Viet Nam</p>
                    <p class="mb-2"><i class="fa fa-envelope text-primary mr-3"></i>baodqhe180053@fpt.edu.vn</p>
                    <p class="mb-0"><i class="fa fa-phone-alt text-primary mr-3"></i>+012 345 67890</p>
                </div>
                <div class="col-lg-8 col-md-12">
                    <div class="row">
                        <div class="col-md-4 mb-5">
                            <h5 class="font-weight-bold text-dark mb-4">Quick Links</h5>
                            <div class="d-flex flex-column justify-content-start">
                                <a class="text-dark mb-2" href="${pageContext.request.contextPath}"><i class="fa fa-angle-right mr-2"></i>Home</a>
                                <a class="text-dark mb-2" href="${pageContext.request.contextPath}/Product"><i class="fa fa-angle-right mr-2"></i>Product</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row border-top border-light mx-xl-5 py-4">
                <div class="col-md-6 px-xl-0">
                    <p class="mb-md-0 text-center text-md-left text-dark">
                        &copy; <a class="text-dark font-weight-semi-bold" href="#">Libee</a>. All Rights Reserved. Designed
                        by
                        <a class="text-dark font-weight-semi-bold" href="https://htmlcodex.com">HTML Codex</a><br>
                        Distributed By <a href="https://themewagon.com" target="_blank">ThemeWagon</a>
                    </p>
                </div>
            </div>
        </div>
        <a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>
        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/lib/easing/easing.min.js"></script>

        <!-- Template Javascript -->
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
        <jsp:invoke fragment="script"/>
    </body>
</html>