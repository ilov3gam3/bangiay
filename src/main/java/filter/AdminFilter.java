package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Constant.Role;
import model.User;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) request.getSession().getAttribute("acc");
        if (user == null) {
            request.getSession().setAttribute("flash_error", "Vui lòng đăng nhập.");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            if (user.getRole() != Role.ADMIN) {
                request.getSession().setAttribute("flash_error", "Bạn phải là quản trị viên mới có thể thực hiện hành động này.");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
