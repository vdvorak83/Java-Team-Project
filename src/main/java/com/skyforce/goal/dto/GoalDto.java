package com.skyforce.goal.dto;

import com.skyforce.goal.model.Checkpoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto {
    private String goalName;
    private String description;
    private BigDecimal price;
    private Date dateEnd;
    private String checkpointName;
    private String checkpointDescription;
}
