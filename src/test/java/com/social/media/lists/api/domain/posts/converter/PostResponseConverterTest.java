package com.social.media.lists.api.domain.posts.converter;

import com.social.media.lists.api.application.util.DateUtil;
import com.social.media.lists.api.domain.people.Person;
import com.social.media.lists.api.domain.posts.Post;
import io.swagger.model.PostResponse;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class PostResponseConverterTest {

    private PostResponseConverter responseConverter;

    @Before
    public void setUp() {

        this.responseConverter = new PostResponseConverter();
    }

    @Test
    public void shouldReturnPostResponseWithCorrectData(){

        Person person = new Person();
        Set<String> belongsToList = new HashSet<>();
        belongsToList.add("List1");
        person.setListsBelongsTo(belongsToList);

        Post post = new Post("Test", "localhost", null, person);
        Date now = new Date();
        post.setCreatedDate(now);
        List<PostResponse> responseList = this.responseConverter.convert(Arrays.asList(post));

        assertThat(responseList).isNotEmpty();
        assertThat(responseList.get(0).getAuthorName()).isNull();
        assertThat(responseList.get(0).getCreatedDate()).isEqualTo(DateUtil.convertDateToString(now));
        assertThat(responseList.get(0).getLinkOriginalPost()).isEqualTo("localhost");
        assertThat(responseList.get(0).getListsBelongsTo()).isEqualTo("List1");
        assertThat(responseList.get(0).getPostContent()).isEqualTo("Test");
        assertThat(responseList.get(0).getSocialNetwork()).isNull();
    }
}