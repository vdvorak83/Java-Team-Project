package com.skyforce.goal.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "goal_followers")
public class GoalFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private Goal goal;*/
}
