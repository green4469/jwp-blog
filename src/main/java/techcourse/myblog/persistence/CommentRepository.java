package techcourse.myblog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleOrderByCreatedAt(Article article);
    List<Comment> findByArticleAndCreatedAtAfter(Article article, LocalDateTime createdAt);
    void deleteByArticle(Article article);
}