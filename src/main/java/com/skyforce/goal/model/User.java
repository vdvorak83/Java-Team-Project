package com.skyforce.goal.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    /*
    @Size(min = 2, max = 50)
    @Column(name = "name")
    private String name;

    @Size(min = 2, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "age")
    private Integer age;
    */

    @Column(name = "temp_password")
    private String tempPassword;

    @Column(name = "email")
    private String email;

    @Column(name = "role_id")
    private Integer role;

    @Column(name = "state_id")
    private Integer state;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "reg_date")
    @Temporal(TemporalType.DATE)
    private Date regDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Goal> goals;

    @OneToOne
    private Wallet wallet;

    @OneToOne
    private Image image;
}
