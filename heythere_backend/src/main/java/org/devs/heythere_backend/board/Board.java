package org.devs.heythere_backend.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.devs.heythere_backend.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_owner_id")
    private Long boardOwnerId;

    @NotBlank
    private String title;

    @NotBlank
    private String writer;

    @Column(columnDefinition = "CLOB")
    private String content;

    private int view;
    private int good;
    private int bad;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(Long id, Long boardOwnerId, String title, String writer, String content, int view,
                 int good, int bad, User user,List<Comment> comments) {
        this.id = id;
        this.boardOwnerId = boardOwnerId;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.view = view;
        this.good = good;
        this.bad = bad;
        this.user = user;
        this.comments = comments;
    }


    public Board updateBoard(final String content) {
        this.content = content;
        return this;
    }
}
