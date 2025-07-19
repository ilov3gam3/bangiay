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
@Table(name = "categories")
public class Category extends DistributedEntity{
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }
}
