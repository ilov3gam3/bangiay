package dal;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Cart;
import model.Customer;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartDao extends GenericDao<Cart> {
    /*public static void add2Cart(Cart cart) {
        try {
            String sql = "insert into Cart(CustomerID, ProductID, Quantity, DateAdded) VALUES (?, ?, ?, ?);";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cart.getCustomerID());
            ps.setInt(2, cart.getProductID());
            ps.setInt(3, cart.getQuantity());
            ps.setTimestamp(4, cart.getDate());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public List<Cart> getCartOfCustomer(long userId) {
        TypedQuery<Cart> typedQuery = entityManager.createQuery("SELECT c FROM Cart c WHERE c.customer.id = :customerId", Cart.class);
        typedQuery.setParameter("customerId", userId);
        return typedQuery.getResultList();
    }
    public void deleteCartsOfCustomer(Customer customer) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin(); // BẮT BUỘC!
            int deleted = entityManager.createQuery(
                            "DELETE FROM Cart c WHERE c.customer.id = :customerId")
                    .setParameter("customerId", customer.getId())
                    .executeUpdate();
            tx.commit();
            System.out.println("Deleted " + deleted + " cart(s)");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }


    /*public static void updateQuantity(Cart cart) {
        try {
            String sql = "update Cart set Quantity = ? where CartID = ?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cart.getQuantity());
            ps.setInt(2, cart.getCartId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*public static void deleteCart(int cartId) {
        try {
            String sql = "delete from Cart where CartID = ?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*public void deleteCartByCustomerId(int productId, int customerId) {
        TypedQuery<>
    }*/

    /*public static void deleteCarts(List<Cart> carts) {
        try {
            String sql = "delete from Cart where CartID in ();";
            String in = "(";
            for (int i = 0; i < carts.size(); i++) {
                if (i == carts.size() - 1) {
                    in += carts.get(i).getCartId();
                } else {
                    in += carts.get(i).getCartId() + ",";
                }
            }
            in += ")";
            sql = sql.replaceFirst("\\(\\)", in);
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
