package techcourse.myblog.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.apache.commons.lang3.StringEscapeUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static techcourse.myblog.web.UserControllerTest.*;

@Slf4j
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerTests {
    private String cookie;
    private WebTestClient webTestClient;
    private static String testTitle = "testTitle";
    private static String testCoverUrl = "testCoverUrl";
    private static String testContents = "testContents";
    private static String testUniContents = StringEscapeUtils.escapeJava(testContents);

    @Autowired
    public ArticleControllerTests(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @BeforeEach
    void setUp() {
        this.cookie = webTestClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("email", testEmail)
                        .with("password", testPassword))
                .exchange()
                .expectStatus()
                .isFound()  // 로그인에 성공하면 리다이렉트
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

    @Test
    void showArticleWritingPageTest() {
        webTestClient.get()
                .uri("/writing")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void addNewArticleTest() {
        testSaveNewArticle("testArticle", "testCoverUrl", "testContents");
    }

    private void testSaveNewArticle(String title, String coverUrl, String contents) {
        webTestClient.post()
                .uri("/articles")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", title)
                        .with("coverUrl", coverUrl)
                        .with("contents", contents))
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody()
                .consumeWith(response -> {
                    String redirectUrl = response.getResponseHeaders().getLocation().toString();
                    webTestClient.get().uri(redirectUrl)
                            .exchange()
                            .expectStatus()
                            .isOk()
                            .expectBody()
                            .consumeWith(innerResponse -> {
                                String body = new String(innerResponse.getResponseBody());
                                assertThat(body.contains(title)).isTrue();
                                assertThat(body.contains(coverUrl)).isTrue();
                                assertThat(body.contains(StringEscapeUtils.escapeJava(testContents))).isTrue();
                            });
                });
    }

    @Test
    void showArticlesPageTest() {
        webTestClient.get().uri("/")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(innerResponse -> {
                    String body = new String(innerResponse.getResponseBody());
                    assertThat(body.contains(testTitle)).isTrue();
                    assertThat(body.contains(testCoverUrl)).isTrue();
                    //assertThat(body.contains(testContents)).isTrue();
                });
    }

    @Test
    void showArticleByIdPageTest() {
        webTestClient.get().uri("/articles/1")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains(testTitle)).isTrue();
                    assertThat(body.contains(testCoverUrl)).isTrue();
                    assertThat(body.contains(testUniContents)).isTrue();
                });
    }

    @Test
    void updateArticleByIdPageTest() {
        String updatedTitle = "포비의 글";
        String updatedCoverUrl = "http://www.kinews.net/news/photo/200907/bjs.jpg";
        String updatedContents = "나는 우아한형제들에서 짱이다.";
        String updatedUniContents = StringEscapeUtils.escapeJava(updatedContents);

        webTestClient.put()
                .uri("/articles")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", updatedTitle)
                        .with("coverUrl", updatedCoverUrl)
                        .with("contents", updatedContents))
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody()
                .consumeWith(response -> {
                    String redirectUrl = response.getResponseHeaders().getLocation().toString();
                    webTestClient.get().uri(redirectUrl)
                            .exchange()
                            .expectStatus()
                            .isOk()
                            .expectBody()
                            .consumeWith(innerResponse -> {
                                String body = new String(innerResponse.getResponseBody());
                                assertThat(body.contains(updatedTitle)).isTrue();
                                assertThat(body.contains(updatedCoverUrl)).isTrue();
                                assertThat(body.contains(updatedUniContents)).isTrue();
                            });
                });

        webTestClient.put()
                .uri("/articles")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", testTitle)
                        .with("coverUrl", testCoverUrl)
                        .with("contents", testContents))
                .exchange()
                .expectStatus()
                .is3xxRedirection();
    }

    @Test
    void deleteArticleByIdTest() {
        webTestClient.post()
                .uri("/articles")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", testTitle)
                        .with("coverUrl", testCoverUrl)
                        .with("contents", testContents))
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody()
                .consumeWith(response -> {
                    String redirectUrl = response.getResponseHeaders().getLocation().toString();
                    webTestClient.delete().uri(redirectUrl)
                            .exchange()
                            .expectStatus()
                            .is3xxRedirection();
                });
    }

    @Test
    void showArticleEditingPage() {
        webTestClient.get()
                .uri("/articles/1/edit")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains(testTitle)).isTrue();
                    assertThat(body.contains(testCoverUrl)).isTrue();
                    assertThat(body.contains(testUniContents)).isTrue();
                });
    }

    private void saveTestArticle() {
        webTestClient.post()
                .uri("/articles")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", testTitle)
                        .with("coverUrl", testCoverUrl)
                        .with("contents", testContents))
                .exchange()
                .expectStatus()
                .is3xxRedirection();
    }


    @Test
    void 첫_페이지_방문_시_사용자_이름_GUEST_확인() {
        webTestClient.get()
                .uri("/")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains("GUEST")).isTrue();
                    assertThat(body.contains("로그인")).isTrue();
                    assertThat(body.contains("회원가입")).isTrue();
                    assertThat(body.contains("로그아웃")).isFalse();
                });
    }
}
