package Controllers;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import model.Constant.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    @WebServlet("/customer/order")
    public static class OrderServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            CustomerDao customerDao = new CustomerDao();
            User user = (User) req.getSession().getAttribute("acc");
            Customer customer = customerDao.getById(user.getId());
            List<Order> orders = customer.getOrders();
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/views/user/Order.jsp").forward(req, resp);
        }
    }

    @WebServlet("/admin/order")
    public static class AdminOrderServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<Order> orders = new OrderDao().getAll();
            req.setAttribute("orders", orders.reversed());
            req.getRequestDispatcher("/views/admin/AdminOrder.jsp").forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Status status = Status.valueOf(req.getParameter("status"));
            long orderId = Long.parseLong(req.getParameter("orderId"));
            OrderDao orderDao = new OrderDao();
            Order order = orderDao.getById(orderId);
            order.setStatus(status);
            orderDao.save(order);
            resp.sendRedirect(req.getContextPath() + "/admin/order");
        }
    }
    @WebServlet("/customer/place-order")
    public static class PlaceOrder extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            CustomerDao customerDao = new CustomerDao();
            CartDao cartDao = new CartDao();
            OrderDao orderDao = new OrderDao();
            User user = (User) req.getSession().getAttribute("acc");
            Customer customer = customerDao.getById(user.getId());
            List<Cart> carts = customer.getCarts();
            double totalPrice = 0;
            boolean checkStock = true;
            for (Cart cart : carts) {
                totalPrice += cart.getQuantity() * cart.getProduct().getPrice();
                if (cart.getQuantity() > cart.getProduct().getStock()){
                    checkStock = false;
                }
            }
            if (!checkStock) {
                req.getSession().setAttribute("flash_error", "Số lượng bạn muốn mua vượt quá giới hạn trong kho.");
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            totalPrice *= 1.1;
            Order order = new Order(customer, totalPrice, Status.PENDING);
            orderDao.save(order);
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (Cart cart : carts) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(cart.getQuantity());
                orderDetail.setProduct(cart.getProduct());
                orderDetail.setPrice(cart.getProduct().getPrice());
                orderDetail.setOrder(order);
                orderDetails.add(orderDetail);
            }
            new OrderDetailDao().saveAll(orderDetails);
            cartDao.deleteAll(carts);
            req.getSession().setAttribute("flash_success", "Đặt hàng thành công.");
            resp.sendRedirect(req.getContextPath() + "/customer/order");
        }
    }
}
