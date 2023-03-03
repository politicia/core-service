package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
