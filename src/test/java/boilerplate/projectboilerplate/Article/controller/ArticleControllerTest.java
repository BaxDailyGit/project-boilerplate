package boilerplate.projectboilerplate.Article.controller;

import boilerplate.projectboilerplate.Article.dto.ArticleRequest;
import boilerplate.projectboilerplate.Article.entity.Article;
import boilerplate.projectboilerplate.Article.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        articleRepository.deleteAll();
    }

    @DisplayName("글 추가 성공")
    @Test
    public void addAriticle() throws Exception {
        // given: API 요청 준비
        String url = "/api/articles";
        String title = "title";
        String content = "contents";
        ArticleRequest request = new ArticleRequest(title, content);

        // 객체를 JSON으로 직렬화
        String requestBody = objectMapper.writeValueAsString(request);

        // when: API 요청
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then: API 응답 상태 검증
        result.andExpect(status().isCreated());

        List<Article> articleList = articleRepository.findAll();

        assertThat(articleList.size()).isEqualTo(1);
        assertThat(articleList.get(0).getTitle()).isEqualTo(title);
        assertThat(articleList.get(0).getContent()).isEqualTo(content);
    }


    @DisplayName("블로그 글 전체 조회 성공")
    @Test
    public void testFindAll() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "제목";
        final String content = "내용";
        Article article = articleRepository.save(new Article(title, content));

        // when
        ResultActions result = mockMvc.perform(get(url));

        // then : 정상적으로 요청이 되었는지 검증
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(article.getTitle()))
                .andExpect(jsonPath("$[0].content").value(article.getContent()));
    }
}