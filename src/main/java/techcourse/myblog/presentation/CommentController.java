package techcourse.myblog.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.persistence.CommentRepository;
import techcourse.myblog.service.CommentService;
import techcourse.myblog.service.dto.CommentRequestDto;
import techcourse.myblog.service.dto.CommentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    public CommentController(CommentRepository commentRepository, CommentService commentService) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    @DeleteMapping("/{commentId}")
    public String processDeleteComment(@PathVariable long commentId) {
        Comment comment = commentService.findById(commentId);
        commentService.deleteById(commentId);
        return "redirect:/articles/" + comment.getArticle().getId();
    }

    @DeleteMapping("/{commentId}/rest")
    @ResponseBody
    public ResponseEntity<List<CommentResponseDto>> processDeleteCommentRest(@PathVariable long commentId) {
        Comment comment = commentService.findById(commentId);
        commentService.deleteById(commentId);
        return new ResponseEntity<>(commentService.findByArticle(comment.getArticle()).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList()), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{commentId}")
    public String processUpdateComment(@PathVariable long commentId, CommentRequestDto commentRequestDto) {
        return "redirect:/articles/" + commentService.update(commentId, commentRequestDto).getArticle().getId();
    }

    @PutMapping("/{commentId}/rest")
    @ResponseBody
    public ResponseEntity<List<CommentResponseDto>> processUpdateCommentRest(@PathVariable long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        Comment comment = commentService.findById(commentId);
        commentService.update(commentId, commentRequestDto);
        return new ResponseEntity<>(commentService.findByArticle(comment.getArticle()).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList()), HttpStatus.ACCEPTED);
    }
}
