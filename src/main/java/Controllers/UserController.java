package Controllers;

import dal.CartDao;
import dal.CustomerDao;
import dal.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Constant.Role;
import model.Customer;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    @WebServlet("/login")
    public static class LoginServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.getRequestDispatcher("/views/public/Login.jsp").forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            UserDao userDao = new UserDao();
            String UserName = request.getParameter("UserName");
            String Password = request.getParameter("Password");
            User a = userDao.login(UserName, Password);
            if(a== null){
                request.getSession().setAttribute("flash_error", "Tên đăng nhập hoặc mật khẩu sai.");
                response.sendRedirect(request.getContextPath()+ "/login");
            }
            else{
                HttpSession session = request.getSession();
                session.setAttribute("acc", a);
                session.setMaxInactiveInterval(10000);
                CustomerDao customerDao = new CustomerDao();
                CartDao cartDao = new CartDao();
                Customer customer = customerDao.getById(a.getId());
                if (request.getSession().getAttribute("cart") != null){
                    List<Cart> cart = (ArrayList<Cart>) request.getSession().getAttribute("cart");
                    for (Cart c : cart) {
                        c.setCustomer(customer);
                    }
                    cartDao.saveAll(cart);
                }
                request.getSession().removeAttribute("cart");
                request.getSession().setAttribute("flash_success", "Đăng nhập thành công.");
                response.sendRedirect(request.getContextPath() +"/home");
            }
        }
    }

    @WebServlet("/logout")
    public static class LogoutServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @WebServlet("/customer/profile")
    public static class ProfileServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            User user = (User) req.getSession().getAttribute("acc");
            CustomerDao customerDao = new CustomerDao();
            Customer customer = customerDao.getById(user.getId());
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/views/user/Profile.jsp").forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            User user = (User) req.getSession().getAttribute("acc");
            CustomerDao customerDao = new CustomerDao();
            Customer customer = customerDao.getById(user.getId());
            String FullName = req.getParameter("FullName");
            String Email = req.getParameter("Email");
            String PhoneNumber = req.getParameter("PhoneNumber");
            String Address = req.getParameter("Address");
            customer.setFullname(FullName);
            customer.setEmail(Email);
            customer.setPhone(PhoneNumber);
            customer.setAddress(Address);
            customerDao.update(customer);
            req.getSession().setAttribute("flash_success", "Cập nhật thông tin thành công.");
            resp.sendRedirect(req.getContextPath() + "/customer/profile");
        }
    }

    @WebServlet("/customer/change-password")
    public static class ChangePasswordServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            UserDao userDao = new UserDao();
            String password = req.getParameter("password");
            String newPassword = req.getParameter("newPassword");
            String confirmPassword = req.getParameter("confirmPassword");
            User user = (User) req.getSession().getAttribute("acc");
            User check = userDao.login(user.getUsername(), password);
            if (check == null){
                req.getSession().setAttribute("flash_error", "Mật khẩu cũ không đúng.");
            } else {
                if (newPassword.equals(confirmPassword)){
                    user.setPassword(newPassword);
                    userDao.save(user);
                    req.getSession().setAttribute("flash_success", "Đổi mật khẩu thành công.");
                } else {
                    req.getSession().setAttribute("flash_error", "Mật khẩu cũ không đúng.");
                }
            }
            resp.sendRedirect(req.getContextPath() + "/customer/profile");
        }
    }

    @WebServlet("/register")
    public static class RegisterServlet extends HttpServlet{
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            UserDao userDao = new UserDao();
            String UserName = request.getParameter("user");
            String Password = request.getParameter("pass");
            String RepeatPassword = request.getParameter("repass");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            if(!Password.equals(RepeatPassword)){
                request.getSession().setAttribute("flash_error", "Mật khẩu không khớp.");
                response.sendRedirect(request.getContextPath() + "/login");
            }
            else{
                User a = userDao.checkUserExistUsername(UserName);
                if (a == null) {
                    CustomerDao customerDao = new CustomerDao();
                    a = customerDao.checkCustomerExistPhone(phone);
                    if (a == null) {
                        User user = new User(UserName, Password, Role.CUSTOMER);
                        userDao.save(user);
                        Customer customer = new Customer(user, fullname, email, phone, address);
                        customerDao.save(customer);
                        request.getSession().setAttribute("flash_success", "Đăng kí thành công.");
                        response.sendRedirect(request.getContextPath() + "/home");
                    } else {
                        request.getSession().setAttribute("flash_error", "Số điện thoại đã tồn tại.");
                        response.sendRedirect(request.getContextPath() + "/login");
                    }
                }
                else{
                    request.getSession().setAttribute("flash_error", "Username đã tồn tại.");
                    response.sendRedirect(request.getContextPath() + "/login");
                }

            }
        }
    }

    @WebServlet("/admin/users")
    public static class AdminCustomerServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<User> users = new UserDao().getAll();
            req.setAttribute("users", users);
            List<Customer> customers = new CustomerDao().getAll();
            req.setAttribute("customers", customers);
            req.getRequestDispatcher("/views/admin/ManagerAccount.jsp").forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            UserDao userDao = new UserDao();
            String UserName = request.getParameter("user");
            String Password = request.getParameter("pass");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            Role role = Role.valueOf(request.getParameter("role"));
            User user = new User(UserName, Password, role);
            userDao.save(user);
            if (role == Role.CUSTOMER) {
                CustomerDao customerDao = new CustomerDao();
                Customer customer = new Customer(user, fullname, email, phone, address);
                customerDao.save(customer);
            }
            request.getSession().setAttribute("flash_success", "Thêm mới thành công.");
            response.sendRedirect(request.getContextPath() + "/admin/users");
        }
    }
    @WebServlet("/admin/edit-user")
    public static class AdminEditServlet extends HttpServlet{
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
            UserDao userDao = new UserDao();
            long id = Long.parseLong(request.getParameter("id"));
            String UserName = request.getParameter("user");
            String Password = request.getParameter("pass");
            User user = userDao.getById(id);
            user.setUsername(UserName);
            if (!Password.isEmpty()){
                user.setPassword(Password);
            }
            userDao.update(user);
            if (user.getRole() == Role.CUSTOMER) {
                CustomerDao customerDao = new CustomerDao();
                Customer customer = customerDao.getById(user.getId());
                String fullname = request.getParameter("fullname");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                customer.setFullname(fullname);
                customer.setEmail(email);
                customer.setPhone(phone);
                customer.setAddress(address);
                customerDao.update(customer);
            }
            request.getSession().setAttribute("flash_success", "Cập nhật thành công.");
            resp.sendRedirect(request.getContextPath() + "/admin/users");
        }
    }
}
