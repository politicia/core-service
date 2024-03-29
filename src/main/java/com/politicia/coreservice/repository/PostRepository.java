package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.Target;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.domain.target.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTarget(Target target, Pageable pageable);

    @EntityGraph
    Page<Post> findByCreatedAtAfterAndCreatedAtBefore(LocalDateTime createdAtAfter, LocalDateTime createdAtBefore, Pageable pageable);

    @EntityGraph
    Page<Post> findByUser(User user, Pageable pageable);


//    Page<Post> findPosts(Page);
}
