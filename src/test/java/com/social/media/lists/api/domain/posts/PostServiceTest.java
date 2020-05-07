package com.social.media.lists.api.domain.posts;

import com.social.media.lists.api.application.util.DateUtil;
import com.social.media.lists.api.application.util.StringUtil;
import com.social.media.lists.api.domain.networks.SocialMediaNetworkService;
import com.social.media.lists.api.domain.people.PersonService;
import com.social.media.lists.api.infrastructure.persistence.PostDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class PostServiceTest {

    private PostService postService;

    private PostDAO postDAO;
    private PersonService personService;
    private SocialMediaNetworkService socialMediaNetworkService;

    @Before
    public void setUp() {

        this.postDAO = PowerMockito.mock(PostDAO.class);
        this.personService = PowerMockito.mock(PersonService.class);
        this.socialMediaNetworkService = PowerMockito.mock(SocialMediaNetworkService.class);

        this.postService = new PostService(this.postDAO, this.personService, this.socialMediaNetworkService);
    }

    @Test
    public void shouldExecuteMethodGetAllPostsByFiltersWithEmptyCriteriaList() {

        this.postService.getAllPostsByFilters(null, null, null, null,
                null, null, null, null);

        ArgumentCaptor<List<Criteria>> stringListArgumentCaptor = forClass(List.class);

        verify(postDAO).getAllPostsByFilters(Mockito.eq(null), Mockito.eq(null),
                stringListArgumentCaptor.capture());

        List<Criteria> listCriteria = stringListArgumentCaptor.getValue();

        assertThat(listCriteria).isEmpty();
    }

    @Test
    public void shouldExecuteMethodGetAllPostsByFiltersWithCriteriaListWithPeopleListCriteria() {

        PowerMockito.when(personService.findPeopleBelongsToLists(
                StringUtil.convertStringToStringList("List1")
        )).thenReturn(Arrays.asList("1"));

        this.postService.getAllPostsByFilters(0L, 10L, "List1", null,
                null, null, null, null);

        ArgumentCaptor<List<Criteria>> stringListArgumentCaptor = forClass(List.class);

        verify(postDAO).getAllPostsByFilters(Mockito.eq(0L), Mockito.eq(10L),
                stringListArgumentCaptor.capture());

        List<Criteria> listCriteria = stringListArgumentCaptor.getValue();
        Criteria peopleListCriteria = where("person.$id")
                .in(Arrays.asList("1"));

        assertThat(listCriteria).isNotEmpty();
        assertThat(listCriteria).contains(peopleListCriteria);
    }

    @Test
    public void shouldExecuteMethodGetAllPostsByFiltersWithCriteriaListWithSocialNetworksCriteria() {

        PowerMockito.when(socialMediaNetworkService.findByNameInList(
                StringUtil.convertStringToStringList("Facebook")
        )).thenReturn(Arrays.asList("1"));

        this.postService.getAllPostsByFilters(0L, 10L, null, "Facebook",
                null, null, null, null);

        ArgumentCaptor<List<Criteria>> stringListArgumentCaptor = forClass(List.class);

        verify(postDAO).getAllPostsByFilters(Mockito.eq(0L), Mockito.eq(10L),
                stringListArgumentCaptor.capture());

        List<Criteria> listCriteria = stringListArgumentCaptor.getValue();
        Criteria networksListCriteria = where("social_network.$id")
                .in(Arrays.asList("1"));

        assertThat(listCriteria).isNotEmpty();
        assertThat(listCriteria).contains(networksListCriteria);
    }

    @Test
    public void shouldExecuteMethodGetAllPostsByFiltersWithCriteriaListWithTextCriteria() {

        this.postService.getAllPostsByFilters(0L, 10L, null, null,
                "Post Content", null, null, null);

        ArgumentCaptor<List<Criteria>> stringListArgumentCaptor = forClass(List.class);

        verify(postDAO).getAllPostsByFilters(Mockito.eq(0L), Mockito.eq(10L),
                stringListArgumentCaptor.capture());

        List<Criteria> listCriteria = stringListArgumentCaptor.getValue();
        Criteria textCriteria = Criteria.where("content")
                .regex("Post Content");

        assertThat(listCriteria).isNotEmpty();
        assertThat(listCriteria).contains(textCriteria);
    }

    @Test
    public void shouldExecuteMethodGetAllPostsByFiltersWithCriteriaListWithAuthorFullNameCriteria() {

        PowerMockito.when(personService.findPeopleByFullname("Teste")).thenReturn(Arrays.asList("1"));

        this.postService.getAllPostsByFilters(0L, 10L, null, null,
                null, "Teste", null, null);

        ArgumentCaptor<List<Criteria>> stringListArgumentCaptor = forClass(List.class);

        verify(postDAO).getAllPostsByFilters(Mockito.eq(0L), Mockito.eq(10L),
                stringListArgumentCaptor.capture());

        List<Criteria> listCriteria = stringListArgumentCaptor.getValue();
        Criteria fullNameCriteria = where("person.$id")
                .in(Arrays.asList("1"));

        assertThat(listCriteria).isNotEmpty();
        assertThat(listCriteria).contains(fullNameCriteria);
    }

    @Test
    public void shouldExecuteMethodGetAllPostsByFiltersWithEmptyCriteriaListIfGivenOnlyStartDate() {

        this.postService.getAllPostsByFilters(null, null, null, null,
                null, null, "03/05/2020", null);

        ArgumentCaptor<List<Criteria>> stringListArgumentCaptor = forClass(List.class);

        verify(postDAO).getAllPostsByFilters(Mockito.eq(null), Mockito.eq(null),
                stringListArgumentCaptor.capture());

        List<Criteria> listCriteria = stringListArgumentCaptor.getValue();

        assertThat(listCriteria).isEmpty();
    }

    @Test
    public void shouldExecuteMethodGetAllPostsByFiltersWithEmptyCriteriaListIfGivenOnlyEndDate() {

        this.postService.getAllPostsByFilters(null, null, null, null,
                null, null, null, "07/05/2020");

        ArgumentCaptor<List<Criteria>> stringListArgumentCaptor = forClass(List.class);

        verify(postDAO).getAllPostsByFilters(Mockito.eq(null), Mockito.eq(null),
                stringListArgumentCaptor.capture());

        List<Criteria> listCriteria = stringListArgumentCaptor.getValue();

        assertThat(listCriteria).isEmpty();
    }

    @Test
    public void shouldExecuteMethodGetAllPostsByFiltersWithCriteriaListWithDateRangeCriteriaIfStartAndEndDatesAreFilled() {

        this.postService.getAllPostsByFilters(0L, 10L, null, null,
                "Post Content", null, "03/05/2020", "07/05/2020");

        ArgumentCaptor<List<Criteria>> stringListArgumentCaptor = forClass(List.class);

        verify(postDAO).getAllPostsByFilters(Mockito.eq(0L), Mockito.eq(10L),
                stringListArgumentCaptor.capture());

        List<Criteria> listCriteria = stringListArgumentCaptor.getValue();
        Criteria dateRangeCriteria = where("created_date").gte(
                DateUtil.stringToDate("03/05/2020")).lte(DateUtil.stringToDate("07/05/2020"));

        assertThat(listCriteria).isNotEmpty();
        assertThat(listCriteria).contains(dateRangeCriteria);
    }

    @Test
    public void shouldExecuteMethodGetAllPostsByFiltersWithCriteriaListWithAllElementsIfAllParametersAreSet() {

        PowerMockito.when(personService.findPeopleByFullname("Teste")).thenReturn(Arrays.asList("1"));

        PowerMockito.when(personService.findPeopleBelongsToLists(
                StringUtil.convertStringToStringList("List1")
        )).thenReturn(Arrays.asList("1"));

        PowerMockito.when(socialMediaNetworkService.findByNameInList(
                StringUtil.convertStringToStringList("Facebook")
        )).thenReturn(Arrays.asList("1"));


        this.postService.getAllPostsByFilters(0L, 10L, "List1", "Facebook",
                "Post Content", "Teste", "03/05/2020", "07/05/2020");

        ArgumentCaptor<List<Criteria>> stringListArgumentCaptor = forClass(List.class);

        verify(postDAO).getAllPostsByFilters(Mockito.eq(0L), Mockito.eq(10L),
                stringListArgumentCaptor.capture());

        List<Criteria> listCriteria = stringListArgumentCaptor.getValue();

        assertThat(listCriteria).isNotEmpty();
        assertThat(listCriteria.size()).isEqualTo(5);
    }
}