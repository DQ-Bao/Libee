<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/tag" prefix="t"%>
<t:main>
    <jsp:attribute name="script">
        <script>
            function checkPassword() {
                const newPassword = document.getElementById("new-password");
                const confirmPassword = document.getElementById("confirm-password");
                const submit = document.getElementById("cp-submit");
                if (newPassword.value !== "" && newPassword.value === confirmPassword.value) {
                    submit.disabled = false;
                }
                else {
                    submit.disabled = true;
                }
            }
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid py-5">
            <div class="row px-xl-5">
                <div class="col-lg-3 col-md-12 nav nav-pills flex-column">
                    <a href="#about-pane" class="nav-item nav-link active mb-3" data-toggle="tab">
                        <i class="fas fa-user"></i>
                        <span class="mx-2">About</span>
                    </a>
                    <a href="#settings-pane" class="nav-item nav-link" data-toggle="tab">
                        <i class="fas fa-cog"></i>
                        <span class="mx-2">Settings</span>
                    </a>
                </div>
                <div class="col-lg-9 col-md-12">
                    <div class="text-danger">${error}</div>
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="about-pane">
                            <h3 class="font-weight-semi-bold">${user.firstName} ${user.lastName}</h3>
                            <span>Email: ${user.email}</span>
                        </div>
                        <div class="tab-pane fade" id="settings-pane">
                            <h3 class="font-weight-semi-bold">Change password</h3>
                            <form action="${pageContext.request.contextPath}/User" method="post">
                                <input type="hidden" name="form-action" value="user-change-password">
                                <input type="password" name="old-password" class="form-control mb-3" placeholder="Old Password" required>
                                <input type="password" id="new-password" name="new-password" class="form-control mb-3" placeholder="New Password" onkeyup="checkPassword()" required>
                                <input type="password" id="confirm-password" class="form-control mb-3" placeholder="Confirm Password" onkeyup="checkPassword()" required>
                                <button type="submit" id="cp-submit" class="btn btn-primary px-3" disabled>Submit</button>
                            </form>
                            <hr>
                            <form action="${pageContext.request.contextPath}/User" method="post">
                                <input type="hidden" name="form-action" value="user-delete-account">
                                <button type="submit" class="btn btn-danger">Delete Account</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:main>
