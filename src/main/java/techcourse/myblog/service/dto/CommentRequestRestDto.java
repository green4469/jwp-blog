package techcourse.myblog.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestRestDto {
    private String comment;
    private LocalDateTime lastCreatedAt;

    public CommentRequestRestDto(String comment, LocalDateTime createdAt) {
        this.comment = comment;
        this.lastCreatedAt = createdAt;
    }

    public Comment toComment(User commenter, Article article) {
        return Comment.builder()
                .comment(this.comment)
                .article(article)
                .commenter(commenter)
                .build();
    }

    public Comment toComment() {
        return Comment.builder()
                .comment(this.comment)
                .build();
    }
}
