package techcourse.myblog.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.myblog.domain.Article;

@Getter
@Setter
@NoArgsConstructor
public class ArticleRequestDto {
    private long id;
    private String title;
    private String contents;
    private String coverUrl;

    public Article toArticle() {
        return new Article(title, contents, coverUrl);
    }
}