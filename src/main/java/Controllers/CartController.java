package Controllers;

import dal.CartDao;
import dal.CustomerDao;
import dal.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cart;
import model.Customer;
import model.Product;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartController {
    @WebServlet("/cart")
    public static class CartServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            ProductDao productDao = new ProductDao();
            CartDao cartDao = new CartDao();
            CustomerDao customerDao = new CustomerDao();
            long ProductID = Long.parseLong(req.getParameter("ProductID"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            Product product = productDao.getById(ProductID);
            if (req.getSession().getAttribute("acc") == null) {
                if (req.getSession().getAttribute("cart") == null){
                    ArrayList<Cart> cart = new ArrayList<>();
                    cart.add(new Cart(product, quantity));
                    req.getSession().setAttribute("cart", cart);
                } else {
                    List<Cart> cart = (ArrayList<Cart>) req.getSession().getAttribute("cart");
                    boolean mark = true;
                    for (int i = 0; i < cart.size(); i++) {
                        if (cart.get(i).getProduct().getId() == ProductID) {
                            cart.get(i).setQuantity(cart.get(i).getQuantity() + quantity);
                            mark = false;
                            break;
                        }
                    }
                    if (mark) {
                        cart.add(new Cart(product, quantity));
                    }
                    req.getSession().setAttribute("cart", cart);
                }
            } else {
                User user = (User) req.getSession().getAttribute("acc");
                Customer customer = customerDao.getById(user.getId());
                List<Cart> customerCart = customer.getCarts();
                boolean mark = true;
                for (int i = 0; i < customerCart.size(); i++) {
                    if (customerCart.get(i).getProduct().getId() == ProductID) {
                        customerCart.get(i).setQuantity(customerCart.get(i).getQuantity() + quantity);
                        mark = false;
                        break;
                    }
                }
                if (mark) {
                    customerCart.add(new Cart(customer, product, quantity));
                }
                cartDao.updateAll(customerCart);
            }
            req.getSession().setAttribute("flash_success", "Đã thêm vào giỏ hàng.");
            resp.sendRedirect(req.getHeader("referer"));
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            CartDao cartDao = new CartDao();
            CustomerDao customerDao = new CustomerDao();
            String action = req.getParameter("action");
            User user = (User) req.getSession().getAttribute("acc");
            if (action == null){
                req.getRequestDispatcher("/views/public/Cart.jsp").forward(req, resp);
            } else {
                if (action.equals("plus") || action.equals("minus")) {
                    long productId = Long.parseLong(req.getParameter("productId"));

                    List<Cart> carts;
                    if(user == null){
                        carts = (ArrayList<Cart>) req.getSession().getAttribute("cart");
                    } else {
                        Customer customer = customerDao.getById(user.getId());
                        carts = cartDao.getCartOfCustomer(customer.getId());
                    }
                    for (Cart cart : carts) {
                        if (cart.getProduct().getId() == productId) {
                            if (action.equals("plus")) {
                                cart.setQuantity(cart.getQuantity() + 1);
                            } else {
                                if (cart.getQuantity() == 1){
                                    if(user != null){
                                        cartDao.delete(cart);
                                    } else {
                                        carts.remove(cart);
                                    }
                                }
                                cart.setQuantity(cart.getQuantity() - 1);
                            }
                            if(user != null){
                                cartDao.save(cart);
                            }
                            break;
                        }
                    }
                }
                else {
                    if (user != null){
                        long cartId = Long.parseLong(req.getParameter("cartId"));
                        Cart cart = cartDao.getById(cartId);
                        cartDao.delete(cart);
                    } else {
                        long productId = Long.parseLong(req.getParameter("productId"));
                        List<Cart> carts = (ArrayList<Cart>) req.getSession().getAttribute("cart");
                        for (int i = 0; i < carts.size(); i++) {
                            if(carts.get(i).getProduct().getId() == productId){
                                carts.remove(i);
                                break;
                            }
                        }
                    }
                }
                resp.sendRedirect(req.getContextPath() + "/cart");
            }
        }
    }

}
