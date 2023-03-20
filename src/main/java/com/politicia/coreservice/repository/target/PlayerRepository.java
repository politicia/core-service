package com.politicia.coreservice.repository.target;

import com.politicia.coreservice.domain.target.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
