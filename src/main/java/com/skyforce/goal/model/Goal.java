package com.skyforce.goal.model;

import com.skyforce.goal.security.state.GoalState;
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
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.ORDINAL)
    private GoalState state;

    @Column(name = "date_end")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;
}
