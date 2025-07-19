package model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product extends DistributedEntity{
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String name;
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String brand;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    private double price;
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String description;
    private String image;
    private int stock;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Cart> carts;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<OrderDetail> orderDetails;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments;

    public Product(String name, String brand, Category category, double price, String description, String image, int stock) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.description = description;
        this.image = image;
        this.stock = stock;
    }
}

   

    

