package com.skyforce.goal.repository;

import com.skyforce.goal.model.MoneyHistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyHistoryEntryRepository extends JpaRepository<MoneyHistoryEntry, Long> {
}
