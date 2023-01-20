package com.service.user.persistence.model;

import jakarta.persistence.*;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "USERS")
public class User{
    @Id
    @SequenceGenerator(name = "user_id_sequence",
                       sequenceName = "user_id_sequence"
                       )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    public Long id;

    @Column(name = "USER_NAME",unique = true, nullable = false)
    private String name;

    @Column(name = "USER_PASSWORD",nullable = false)
    private String password;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

}
