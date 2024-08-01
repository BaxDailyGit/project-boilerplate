package boilerplate.projectboilerplate.Article.repository;

import boilerplate.projectboilerplate.Article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}