package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
