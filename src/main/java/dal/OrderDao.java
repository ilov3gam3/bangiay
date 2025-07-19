package dal;

import model.*;
import model.Constant.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends GenericDao<Order> {
    /*public static void order(int userId) {
        try {
            List<Cart> carts = CartDao.getCartOfCustomer(userId);
            double totalPrice = 0;
            for (Cart cart : carts) {
                totalPrice += cart.getQuantity() * cart.getProducts().getPrice();
            }
            totalPrice *= 1.1;
            String sql = "insert into Orders(CustomerID, OrderDate, TotalAmount, Status) VALUES ((select CustomerID from Customers where UserID = ?), ?, ?, ?)";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setDouble(3, totalPrice);
            ps.setString(4, "Pending");
            ps.executeUpdate();
            ps.getGeneratedKeys().next();
            int orderId = ps.getGeneratedKeys().getInt(1);
            sql = "";
            for (int i = 0; i < carts.size(); i++) {
                sql += "insert into OrderDetails(OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?);";
            }
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < carts.size(); i++) {
                ps.setInt(i * 4 + 1, orderId);
                ps.setInt(i * 4 + 2, carts.get(i).getProductID());
                ps.setDouble(i * 4 + 3, carts.get(i).getQuantity());
                ps.setDouble(i * 4 + 4, carts.get(i).getProducts().getPrice());
            }
            ps.executeUpdate();
            CartDao.deleteCarts(carts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*public static List<Order> getOrdersWithUser(User user) {
        try {
            int CustomerId = UserDao.getCustomerId(user.getUserID());
            List<OrderDetail> orderDetails = new ArrayList<>();
            List<Product> products = ProductDao.getAllProducts();
            String sql = "select OrderDetails.* from OrderDetails inner join dbo.Orders O on O.OrderID = OrderDetails.OrderID where CustomerID = ? order by OrderID desc;";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, CustomerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                orderDetails.add(new OrderDetail(
                        rs.getInt(1),
                        rs.getInt(2),
                        ProductDao.getProduct(rs.getInt(3), products),
                        rs.getInt(4),
                        rs.getInt(5)
                ));
            }

            sql = "select * from Orders where CustomerID = ?";
            List<Order> orders = new ArrayList<>();
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, CustomerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt(1),
                        user,
                        rs.getTimestamp(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        new ArrayList<OrderDetail>()
                ));
            }
            for (Order order : orders) {
                for (OrderDetail orderDetail : orderDetails) {
                    if (order.getOrderId() == orderDetail.getOrderId()) {
                        order.getOrderDetails().add(orderDetail);
                    }
                }
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    /*public static List<Order> getAllOrders() {
        try {
            List<User> users = UserDao.getAllUser();
            List<OrderDetail> orderDetails = new ArrayList<>();
            List<Product> products = ProductDao.getAllProducts();
            String sql = "select * from OrderDetails";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                orderDetails.add(new OrderDetail(
                        rs.getInt(1),
                        rs.getInt(2),
                        ProductDao.getProduct(rs.getInt(3), products),
                        rs.getInt(4),
                        rs.getInt(5)
                ));
            }

            sql = "select * from Orders order by OrderID desc;";
            List<Order> orders = new ArrayList<>();
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt(1),
                        UserDao.getUser(rs.getInt(2), users),
                        rs.getTimestamp(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        new ArrayList<>()
                ));
            }
            for (Order order : orders) {
                for (OrderDetail orderDetail : orderDetails) {
                    if (order.getOrderId() == orderDetail.getOrderId()) {
                        order.getOrderDetails().add(orderDetail);
                    }
                }
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public int getDeliveredOrdersCount(long customerId, long productId) {
        String jpql = "SELECT COUNT(o) FROM Order o " +
                "JOIN o.orderDetails od " +
                "WHERE o.customer.id = :userId " +
                "AND od.product.id = :productId " +
                "AND o.status = :status";

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("userId", customerId)
                .setParameter("productId", productId)
                .setParameter("status", Status.DELIVERED)
                .getSingleResult();

        return count != null ? count.intValue() : 0;
    }

}

