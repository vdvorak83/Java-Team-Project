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

   /* @OneToOne
    @JoinColumn(name = "id")
    private Wallet wallet;*/
}
