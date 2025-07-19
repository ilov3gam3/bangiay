package model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "order_details")
public class OrderDetail extends DistributedEntity{
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    private Product product;
    private int quantity;
    private double price;

    public OrderDetail(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
