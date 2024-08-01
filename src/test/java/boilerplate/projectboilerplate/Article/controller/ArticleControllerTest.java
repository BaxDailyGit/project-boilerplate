package boilerplate.projectboilerplate.Article.controller;

import boilerplate.projectboilerplate.Article.dto.AddArticleRequest;
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
        AddArticleRequest request = new AddArticleRequest(title, content);

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
}