package com.politicia.coreservice.repository.target;

import com.politicia.coreservice.domain.target.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
