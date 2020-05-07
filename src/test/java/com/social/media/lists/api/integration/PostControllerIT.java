package com.social.media.lists.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.PostResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void integratedTestGetFilteredPostsPaginated() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("networks", "Facebook");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/social-media-lists-api/v1/posts?currentPage=0&pageSize=10"), HttpMethod.GET, entity, String.class);

        String stringPosts = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<PostResponse> posts = Arrays.asList(mapper.readValue(stringPosts, PostResponse[].class));

        assertThat(posts).isNotEmpty();
        assertThat(posts.size()).isEqualTo(10);
        assertThat(posts.get(0).getSocialNetwork()).isEqualTo("Facebook");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
