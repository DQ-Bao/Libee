<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <div class="container-fluid pt-5">
        <div class="text-center mb-4">
            <h2 class="section-title px-5"><span class="px-2">Login</span></h2>
        </div>
        <div class="row justify-content-center">
            <div class="col-lg-7 mb-5">
                <div class="text-danger">${message}</div>
                <form action="${pageContext.request.contextPath}/Login" method="post" id="loginForm">
                    <input type="email" class="form-control mb-3" name="email" placeholder="Your Email" required/>
                    <input type="password" class="form-control mb-3" name="password" placeholder="Your Password" required/>
                    <button class="btn btn-primary py-2 px-4" type="submit" id="loginButton">Login</button>
                </form>
            </div>
        </div>
    </div>
</t:main>