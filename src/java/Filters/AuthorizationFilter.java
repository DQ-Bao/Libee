package Filters;

import Annotations.Authorize;
import Models.User;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.lang.reflect.Method;

public class AuthorizationFilter implements Filter {
    public AuthorizationFilter() {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        boolean authorize = false;
        User user = null;
        HttpSession session = req.getSession();
        if (session != null) {
            user = (User)session.getAttribute("user");
        }
        
        String servletName = req.getHttpServletMapping().getServletName();
        try {
            String servletClassName = request.getServletContext().getServletRegistration(servletName).getClassName();
            Class<?> servletClass = Class.forName(servletClassName);
            Authorize classAnno = servletClass.getAnnotation(Authorize.class);
            authorize = hasAuthorizedRole(user, classAnno);
            if (authorize) {
                String method = "do" + req.getMethod().substring(0, 1).toUpperCase() + req.getMethod().substring(1).toLowerCase();
                Method[] methods = servletClass.getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].getName().equals(method)) {
                        Authorize methodAnno = methods[i].getAnnotation(Authorize.class);
                        authorize = hasAuthorizedRole(user, methodAnno);
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        if (authorize) {
            chain.doFilter(request, response);
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/Login");
        }
    }
    
    private boolean hasAuthorizedRole(User user, Authorize anno) {
        if (anno == null) return true;
        String[] requiredRoles = anno.value();
        if (requiredRoles == null || requiredRoles.length == 0) return true;
        else if (user == null || user.getRoles() == null || user.getRoles().isEmpty()) return false;
        for (int i = 0; i < user.getRoles().size(); i++) {
            for (int j = 0; j < requiredRoles.length; j++) {
                if (user.getRoles().get(i).getName().equals(requiredRoles[j])) {
                    return true;
                }
            }
        }
        return false;
    }
}
