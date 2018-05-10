package com.skyforce.goal.model;

import com.skyforce.goal.model.enums.GoalState;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.ORDINAL)
    private GoalState state;

    @Column(name = "date_start")
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Column(name = "date_end")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;

    @OneToMany(mappedBy = "goal", fetch = FetchType.EAGER)
    private List<Checkpoint> checkpoints;

    @OneToOne
    @JoinColumn(name = "image")
    private Image image;
}
