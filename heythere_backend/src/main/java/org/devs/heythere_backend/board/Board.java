package org.devs.heythere_backend.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.devs.heythere_backend.comment.Comment;
import org.devs.heythere_backend.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
@Entity
public class Board{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Column(name = "board_owner")
    private User boardOwner;

    @NotBlank
    private String title;

    @NotBlank
    private String writer;

    @Column(columnDefinition = "CLOB")
    private String content;

    private int view;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(Long id, User boardOwner, String title, String writer,
                 String content, int view, User user,
                 List<Comment> comments) {
        this.id = id;
        this.boardOwner = boardOwner;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.view = view;
        this.user = user;
        this.comments = comments;
    }

    public Board addViewCount() {
        ++view;
        return this;
    }

    public Board updateBoard(final String content) {
        this.content = content;
        return this;
    }
}