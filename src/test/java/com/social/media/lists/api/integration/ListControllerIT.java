package com.social.media.lists.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.ListTemplate;
import io.swagger.model.PersonTemplate;
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
public class ListControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void integratedTestSavePeopleList() throws Exception {

        ListTemplate listTemplate = new ListTemplate();
        listTemplate.setName("List3");

        PersonTemplate personTemplate = new PersonTemplate();
        personTemplate.setSsn(1L);
        personTemplate.setAccounts(new ArrayList<>());
        personTemplate.setListsBelongsTo(Arrays.asList("List3"));
        personTemplate.setFullname("Teste");

        List<PersonTemplate> peopleTemplate = new ArrayList<>();
        peopleTemplate.add(personTemplate);

        listTemplate.setPeople(peopleTemplate);


        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ListTemplate> entity = new HttpEntity<>(listTemplate, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                createURLWithPort("/social-media-lists-api/v1/lists"), HttpMethod.POST, entity, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void integratedTestGetAllPeopleLists() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/social-media-lists-api/v1/lists"), HttpMethod.GET, entity, String.class);

        String stringPeopleLists = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<ListTemplate> peopleLists = Arrays.asList(mapper.readValue(stringPeopleLists, ListTemplate[].class));

        assertThat(peopleLists).isNotEmpty();
        assertThat(peopleLists.size()).isEqualTo(2);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
