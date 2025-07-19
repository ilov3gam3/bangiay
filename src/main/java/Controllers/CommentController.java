package Controllers;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Comment;
import model.Customer;
import model.Product;
import model.User;

import java.io.IOException;

public class CommentController {
    @WebServlet("/customer/comment")
    public static class CommentServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            CustomerDao customerDao = new CustomerDao();
            OrderDao orderDao = new OrderDao();
            CommentDao commentDao = new CommentDao();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("acc");
            long productId = Long.parseLong(request.getParameter("productId"));
            Product product = new ProductDao().getById(productId);
            String commentText = request.getParameter("commentText");
            String ratingStr = request.getParameter("rating");

            try {
                int rating = Integer.parseInt(ratingStr);
                Customer customer = new CustomerDao().getById(user.getId());
                long customerId = customer.getId();

                if (customerId == 0) {
                    request.getSession().setAttribute("flash_error", "Không tìm thấy dữ liệu người dùng trong hệ thống.");
                    response.sendRedirect(request.getContextPath() + "/detail?ProductID=" + productId);
                }

                int deliveredOrders = orderDao.getDeliveredOrdersCount(customerId, productId);
                int existingComments = commentDao.getUserCommentsCount(customerId, productId);

                if (existingComments >= deliveredOrders) {
                    request.setAttribute("flash_error", "Bạn đã review hết số lần bạn mua sản phẩm này.");
                    request.getRequestDispatcher(request.getContextPath() + "/detail?ProductID=" + productId).forward(request, response);
                    return;
                }

                Comment comment = new Comment(
                        customer,
                        product,
                        commentText,
                        rating
                );

                commentDao.save(comment);
                request.getSession().setAttribute("flash_success", "Đã thêm đánh giá.");
                response.sendRedirect(request.getContextPath() + "/detail?ProductID=" + productId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.getSession().setAttribute("flash_error", "Lỗi dữ liệu đầu vào.");
                request.getRequestDispatcher("detail?ProductID=" + productId).forward(request, response);
            }
        }

    }
}
