package Controllers.Admin;

import Annotations.Authorize;
import DataAccesses.Internal.DBProps;
import DataAccesses.RoleDataAccess;
import DataAccesses.UserDataAccess;
import Models.Role;
import java.util.Arrays;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Authorize({"Admin"})
public class AdminUserController extends HttpServlet {
    private RoleDataAccess roleDAO;
    private UserDataAccess userDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        DBProps props = new DBProps(driverName, connectionString);
        this.roleDAO = RoleDataAccess.getInstance(props);
        this.userDAO = UserDataAccess.getInstance(props);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setAttribute("roles", roleDAO.getAll());
        req.setAttribute("users", userDAO.getAll());
        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getParameter("form-action");
        if (action == null) {
            resp.sendError(400);
            return;
        }
        if (action.equals("add-role")) {
            String roleName = req.getParameter("add-role-name");
            if (roleName != null && !roleName.isBlank()) {
                roleDAO.addOne(roleName);
            }
        }
        if (action.equals("change-role")) {
            String act = req.getParameter("action");
            int rid = Integer.parseInt(req.getParameter("role-id"));
            if (act != null) {
                if (act.equals("update")) {
                    String rname = req.getParameter("update-role-name");
                    if (rname != null && !rname.isBlank()) {
                        roleDAO.updateOne(new Role(rid, rname));
                    }
                }
                if (act.equals("delete")) {
                    roleDAO.deleteOne(rid);
                }
            }
        }
        if (action.equals("manage-user-role")) {
            String[] userIds = req.getParameterValues("user-id");
            for (String id : userIds) {
                String[] roleIds = req.getParameterValues("user-role" + id);
                int uid = Integer.parseInt(id);
                userDAO.updateRoles(uid, Arrays.asList(roleIds));
            }
        }
        resp.sendRedirect(req.getContextPath() + "/Admin/User");
    }
}
