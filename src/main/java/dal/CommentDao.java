package dal;

import jakarta.persistence.TypedQuery;
import model.Comment;

import java.sql.SQLException;

public class CommentDao extends GenericDao<Comment> {

    public double getAverageRatingByProductId(long productId) {
        TypedQuery<Double> query = entityManager.createQuery(
                "select avg(c.rating) from Comment c where c.product.id = :productId", Double.class);
        query.setParameter("productId", productId);
        Double result = query.getSingleResult();
        return result != null ? result : 0.0;
    }

    /*public static int getTotalReviews(long productId) {

        try {
            String sql = "SELECT COUNT(*) FROM Comments WHERE productId = ?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }*/

    public boolean canUserCommentOnProduct(int userId, String productId) {
        String jpql = "SELECT COUNT(o) FROM Order o " +
                "JOIN o.orderDetails od " +
                "WHERE o.customer.user.id = :userId AND od.product.id = :productId AND o.status = 'Delivered'";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .getSingleResult();
        return count != null && count > 0;
    }

    public boolean hasUserCommentedOnProduct(int customerId, String productId) {
        String jpql = "SELECT COUNT(c) FROM Comment c WHERE c.customer.id = :customerId AND c.product.id = :productId";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("customerId", customerId)
                .setParameter("productId", productId)
                .getSingleResult();
        return count != null && count > 0;
    }

    public int getUserCommentsCount(long customerId, long productId) {
        String jpql = "SELECT COUNT(c) FROM Comment c WHERE c.customer.id = :customerId AND c.product.id = :productId";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("customerId", customerId)
                .setParameter("productId", productId)
                .getSingleResult();
        return count != null ? count.intValue() : 0;
    }

}
