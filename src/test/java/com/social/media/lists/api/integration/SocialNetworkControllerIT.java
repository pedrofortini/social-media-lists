package com.social.media.lists.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.ListTemplate;
import io.swagger.model.PersonTemplate;
import io.swagger.model.SocialMediaNetworkTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void integratedTestSaveSocialMediaNetworks() throws Exception {

        SocialMediaNetworkTemplate template = new SocialMediaNetworkTemplate();
        template.setName("Linkedin");
        template.setMainPageLink("localhost");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SocialMediaNetworkTemplate> entity = new HttpEntity<>(template, headers);

        ResponseEntity<Void> responseSave = restTemplate.exchange(
                createURLWithPort("/social-media-lists-api/v1/networks"), HttpMethod.POST, entity, Void.class);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/social-media-lists-api/v1/networks"), HttpMethod.GET, entity, String.class);

        String stringSocialMediaNetworks = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<SocialMediaNetworkTemplate> networks = Arrays.asList(mapper.readValue(stringSocialMediaNetworks, SocialMediaNetworkTemplate[].class));

        assertThat(responseSave.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(networks).isNotEmpty();
        assertThat(networks.size()).isEqualTo(3);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
