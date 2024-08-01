package boilerplate.projectboilerplate.Article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
}