package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "comments")
public class Comment extends DistributedEntity{
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Product product;
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String text;
    private int rating;
}