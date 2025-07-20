package dal;

import jakarta.persistence.TypedQuery;
import model.User;

public class UserDao extends GenericDao<User> {

    public User login(String username, String password) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username = :username and u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getResultStream().findFirst().orElse(null);
    }

    public User checkUserExistUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst().orElse(null);
    }

    /*public static void signup(String UserName, String Password) {
        String query = "insert into Users\n"
                + "values(?,?,'Customer')";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, UserName);
            ps.setString(2, Password);
            ps.executeUpdate();

        } catch (Exception e) {
        }
    }*/

    /*public static List<User> getAllUser() {
        List<User> list = new ArrayList<>();
        String query = "select * from Users";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        new Customer(
                                rs.getInt("CustomerID"),
                                rs.getString("FullName"),
                                rs.getString("Email"),
                                rs.getString("PhoneNumber"),
                                rs.getString("Address"),
                                rs.getTimestamp("RegistrationDate")
                        )

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static User getUserById(int userID){
        String query = "select * from Users where UserID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        new Customer(
                                rs.getInt("CustomerID"),
                                rs.getString("FullName"),
                                rs.getString("Email"),
                                rs.getString("PhoneNumber"),
                                rs.getString("Address"),
                                rs.getTimestamp("RegistrationDate")
                        )

                );
            }
            return new User();
        } catch (Exception e) {
            e.printStackTrace();
            return new User();
        }
    }

    public static void insertCustomerData(String fullName, String email, String phone, String address, int userID) {
        System.out.println("insert customer run");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDate = sdf.format(new Date(System.currentTimeMillis()));
            String sql = "insert into Customers(FullName, Email, PhoneNumber, Address, RegistrationDate, UserID) values (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setString(5, currentDate);
            ps.setInt(6, userID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*public static int getCustomerId(int userId) {
        try {
            String sql = "select CustomerID from Customers where UserID = ?;";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static User getUser(int userId, List<User> users) {
        for (User user : users) {
            if (user.getUserID() == userId) {
                return user;
            }
        }
        return null;
    }*/

    /*public static Customer getCustomer(int userId) {
        try {
            String sql = "select * from Customers where CustomerID = ?;";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("FullName"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address"),
                        rs.getTimestamp("RegistrationDate")
                );
            }
            return new Customer();
        } catch (Exception e) {
            e.printStackTrace();
            return new Customer();
        }
    }*/

    /*public static boolean updateCustomer(Customer customer) {
        try {
            String query = "update mydb.dbo.Customers set FullName = ?, Email = ?, PhoneNumber = ?, Address = ? where CustomerID = ?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changePassword(String password, int userID) {
        try {
            String sql = "update mydb.dbo.Users set Password = ? where UserID = ?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/
}
