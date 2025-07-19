package model;

import jakarta.persistence.*;
import lombok.*;
import model.Constant.Status;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order extends DistributedEntity{
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;

    public Order(Customer customer, Double totalAmount, Status status) {
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.status = status;
    }
}
