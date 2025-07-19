package dal;

import jakarta.persistence.TypedQuery;
import model.Customer;
import model.User;

public class CustomerDao extends GenericDao<Customer> {

    public User checkCustomerExistPhone(String phone) {
        TypedQuery<User> query = entityManager.createQuery("select c from Customer c where c.phone = :phone", User.class);
        query.setParameter("phone", phone);
        return query.getSingleResult();
    }
}
