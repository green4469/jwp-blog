package techcourse.myblog.service;

import org.springframework.stereotype.Service;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.persistence.CommentRepository;
import techcourse.myblog.presentation.CommentNotFoundException;
import techcourse.myblog.service.dto.CommentRequestDto;
import techcourse.myblog.service.dto.CommentResponseDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment update(long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.update(commentRequestDto.toComment());
        return comment;
    }

    public Comment save(Comment newComment) {
        return commentRepository.save(newComment);
    }

    public List<Comment> findByArticle(Article article) {
        return Collections.unmodifiableList(commentRepository.findByArticleOrderByCreatedAt(article));
    }

    public List<CommentResponseDto> commentsToDtos(List<Comment> comments) {
        return Collections.unmodifiableList(comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList()));
    }

    public List<Comment> findByArticleAfter(Article article, LocalDateTime createdAt) {
        return Collections.unmodifiableList(commentRepository.findByArticleAndCreatedAtAfter(article, createdAt));
    }
}
