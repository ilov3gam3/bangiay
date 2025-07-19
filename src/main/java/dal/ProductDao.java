package dal;

import jakarta.persistence.TypedQuery;
import model.Product;

import java.util.Collections;
import java.util.List;

public class ProductDao extends GenericDao<Product> {


    public Product getLast() {
        try {
            TypedQuery<Product> query = entityManager.createQuery(
                    "SELECT p FROM Product p ORDER BY p.id DESC", Product.class);
            query.setMaxResults(1);
            List<Product> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Product> getByCategoryId(Long categoryId) {
        return entityManager.createQuery(
                        "SELECT p FROM Product p WHERE p.category.id = :categoryId", Product.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }


    /*public static List<Product> getAllProductByCID(String CategoryID) {
        List<Product> list = new ArrayList<>();
        String query = "select *from Products\n"
                + "where CategoryID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, CategoryID);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6)));
            }
        } catch (Exception e) {
        }
        return list;
    }*/

    /*public static Product getProductByCID(String ProductID) {

        String query = "select *from Products\n"
                + "where ProductID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, ProductID);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6));
            }
        } catch (Exception e) {
        }
        return null;
    }*/

    public List<Product> searchByName(String txtSearch) {
        try {
            String jpql = "SELECT p FROM Product p WHERE p.name LIKE :txtSearch";
            TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
            query.setParameter("txtSearch", "%" + txtSearch + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    /*public static void deleteProduct(String ProductID) {
        String query = "delete from Products\n"
                + "where ProductID =?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, ProductID);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public static void insertProduct(String ProductName, String ImageURL, String Price, String Brand, String Description, String CategoryID) {
        String query = "insert [dbo].[Products]([ProductName], [ImageURL], [Price], [Brand], [Description], [CategoryID])  \n"
                + "values(?,?,?,?,?,?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, ProductName);
            ps.setString(2, ImageURL);
            ps.setString(3, Price);
            ps.setString(4, Brand);
            ps.setString(5, Description);
            ps.setString(6, CategoryID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editProduct(String ProductName, String ImageURL, String Price, String Brand, String Description, String CategoryID, String ProductID) {
        String query = "update Products\n"
                + "set [ProductName] = ?,\n"
                + "ImageURL = ?,\n"
                + "Price =?,\n"
                + "Brand = ?,\n"
                + "[Description] =?,\n"
                + "CategoryID = ?\n"
                + "where ProductID =?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, ProductName);
            ps.setString(2, ImageURL);
            ps.setString(3, Price);
            ps.setString(4, Brand);
            ps.setString(5, Description);
            ps.setString(6, CategoryID);
            ps.setString(7, ProductID);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }*/
}
