package com.skyforce.goal.model;

import com.skyforce.goal.security.role.UserRole;
import com.skyforce.goal.security.state.UserState;
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

    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

    @Enumerated(EnumType.ORDINAL)
    private UserState state;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "reg_date")
    private Date regDate;

    @OneToOne
    @JoinColumn(name = "id")
    private Wallet wallet;
}
