package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.like.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    int countPostLikesByPost(Post post);
}
