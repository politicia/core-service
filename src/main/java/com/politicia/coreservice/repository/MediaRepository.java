package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
