package boilerplate.projectboilerplate.Article.service;
import boilerplate.projectboilerplate.Article.dto.AddArticleRequest;
import boilerplate.projectboilerplate.Article.entity.Article;
import boilerplate.projectboilerplate.Article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article saveArticle(AddArticleRequest request) {
        return articleRepository.save(request.toEntity());
    }
}