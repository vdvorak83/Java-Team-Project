package com.skyforce.goal.model;

import com.skyforce.goal.model.enums.GoalState;
import com.skyforce.goal.security.role.UserRole;
import com.skyforce.goal.security.state.UserState;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString(exclude = "goals")

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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_id")
    private UserRole role;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state_id")
    private UserState state;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "reg_date")
    @Temporal(TemporalType.DATE)
    private Date regDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Goal> goals;

    public List<Goal> getGoalsByState(GoalState goalState){
        return goals.stream().filter(g->g.getState().equals(goalState)).collect(Collectors.toList());
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet")
    private Wallet wallet;

    @OneToOne
    private Image image;

    @ManyToMany
    @JoinTable(name = "users_followings", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id"))
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "users_followings", joinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> follows;
}
