package com.politicia.coreservice.repository.like;

import com.politicia.coreservice.domain.like.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
