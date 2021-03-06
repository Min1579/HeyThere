package org.devs.heythere_backend.board;

import lombok.Builder;
import lombok.Getter;
@Getter
public class CommentEditResponseDto {
    private final Long id;

    @Builder
    public CommentEditResponseDto(final Comment comment) {
        this.id = comment.getId();
    }
}
