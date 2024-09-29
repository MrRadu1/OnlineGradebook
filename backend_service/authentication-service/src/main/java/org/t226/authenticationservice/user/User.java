package org.t226.authenticationservice.user;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "user_table")
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "user_role")
    private Role role;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}