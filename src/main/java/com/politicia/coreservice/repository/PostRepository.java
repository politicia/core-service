package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.target.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTarget(Team team);
}
