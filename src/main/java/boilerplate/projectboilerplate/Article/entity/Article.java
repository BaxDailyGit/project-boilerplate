package boilerplate.projectboilerplate.Article.entity;

import boilerplate.projectboilerplate.Article.dto.AddArticleResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 생성자를 사용해 객체 생성
    // Article 객체를 AddArticleResponse 객체로 변환
    public AddArticleResponse toResponse() {
        return new AddArticleResponse(id, title, content);
    }
}