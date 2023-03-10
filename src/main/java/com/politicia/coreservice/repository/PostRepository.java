package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.domain.target.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTarget(Team team, Pageable pageable);

    Page<Post> findByCreatedAtAfterAndCreatedAtBefore(LocalDateTime createdAtAfter, LocalDateTime createdAtBefore, Pageable pageable);

    Page<Post> findByUser(User user, Pageable pageable);


//    Page<Post> findPosts(Page);
}
