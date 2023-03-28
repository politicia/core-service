package com.politicia.coreservice.repository.like;

import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.domain.like.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);

}
