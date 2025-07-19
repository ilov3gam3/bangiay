package model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private User user;
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String fullname;
    private String email;
    @Column(unique = true, nullable = false)
    private String phone;
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String address;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Customer(User user, String fullname, String email, String phone, String address) {
        this.user = user;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

}
