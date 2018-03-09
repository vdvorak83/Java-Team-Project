package com.skyforce.goal.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "goal_history")
public class GoalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Goal goal;

    @Column(name = "before_state")
    private Long beforeState;

    @Column(name = "after_state")
    private Long afterState;

    @Column(name = "date")
    private Date date;
}
