package dal;

import jakarta.persistence.TypedQuery;
import model.Customer;
import model.User;

public class CustomerDao extends GenericDao<Customer> {

    public Customer checkCustomerExistEmail(String email) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c where c.email = :email", Customer.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst().orElse(null);
    }
    public Customer checkCustomerExistEmailExcept(String email, long id) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c where c.email = :email and c.id != :id", Customer.class);
        query.setParameter("email", email);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }
    public Customer checkCustomerExistPhone(String phone) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c where c.phone = :phone", Customer.class);
        query.setParameter("phone", phone);
        return query.getResultStream().findFirst().orElse(null);
    }
    public Customer checkCustomerExistPhoneExcept(String phone, long id) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c where c.phone = :phone and c.id != :id", Customer.class);
        query.setParameter("phone", phone);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }
}
