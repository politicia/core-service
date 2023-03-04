package com.politicia.coreservice.domain.like;

import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.Like;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends Like {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
