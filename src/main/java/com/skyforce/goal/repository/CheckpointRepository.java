package com.skyforce.goal.repository;

import com.skyforce.goal.model.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckpointRepository extends JpaRepository<Checkpoint,Long> {

}
