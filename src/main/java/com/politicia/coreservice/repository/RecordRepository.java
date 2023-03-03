package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
