package com.politicia.coreservice.repository.like;

import com.politicia.coreservice.domain.like.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

}