package techcourse.myblog.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.domain.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private long id;
    private String comment;

    public Comment toComment(User commenter, Article article) {
        return Comment.builder()
                .comment(this.comment)
                .article(article)
                .commenter(commenter)
                .build();
    }
}
