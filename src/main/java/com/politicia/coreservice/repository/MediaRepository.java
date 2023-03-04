package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.Media;
import com.politicia.coreservice.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {

    Page<Media> findByPost(Post post);
}
