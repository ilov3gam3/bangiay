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
@Table(name = "carts")
public class Cart extends DistributedEntity{
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    private Product product;
    private int quantity;

    public Cart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
