package com.politicia.coreservice.repository.like;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.domain.like.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostAndUser(Post post, User user);
}
