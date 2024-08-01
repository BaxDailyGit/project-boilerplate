package boilerplate.projectboilerplate.Article.controller;

import boilerplate.projectboilerplate.Article.dto.ArticleRequest;
import boilerplate.projectboilerplate.Article.dto.ArticleResponse;
import boilerplate.projectboilerplate.Article.entity.Article;
import boilerplate.projectboilerplate.Article.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController        // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class ArticleController {
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // HTTP요청이 'POST /api/articles' 경로일 때 해당 메소드로 매핑
    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody ArticleRequest request) { // RequestBody로 요청 본문 값 매핑
        Article article = articleService.saveArticle(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article.toResponse());
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> list = articleService.findAll()
                .stream().map(Article::toResponse) // Article 객체를 ArticleResponse 객체로 변환
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(list);
    }
}