package com.skyforce.goal.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "temp_password")
    private String tempPassword;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "id")
    private State state;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "reg_date")
    private Date regDate;

    @OneToOne
    @JoinColumn(name = "id")
    private Wallet wallet;
}
