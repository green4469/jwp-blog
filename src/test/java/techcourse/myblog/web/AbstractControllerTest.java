package techcourse.myblog.web;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import techcourse.myblog.domain.UserVO;

import static techcourse.myblog.web.UserControllerTest.testEmail;
import static techcourse.myblog.web.UserControllerTest.testPassword;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerTest {
    private WebTestClient webTestClient;
    private String cookie;

    public AbstractControllerTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public void doLogin(String uri, String id, String password) {
        this.cookie = webTestClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(mapBy(UserVO.class, "", testPassword, testEmail))
                .exchange()
                .expectStatus()
                .isFound()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

    private <T> BodyInserters.FormInserter<String> mapBy(Class<T> classType, String... parameters) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData(Strings.EMPTY, Strings.EMPTY);

        for (int i = 0; i < classType.getDeclaredFields().length; i++) {
            body.with(classType.getDeclaredFields()[i].getName(), parameters[i]);
        }
        return body;
    }

    public WebTestClient getWebTestClient() {
        return webTestClient;
    }

    public String getCookie() {
        return cookie;
    }
}
