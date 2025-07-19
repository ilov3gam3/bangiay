package model;

import jakarta.persistence.*;
import lombok.*;
import model.Constant.Role;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User extends DistributedEntity{
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role Role;
}
