package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.posts.Post;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class PostDAOTest {

    private PostDAO postDAO;

    private MongoTemplate mongoTemplate;

    @Before
    public void setUp() {

        this.mongoTemplate = PowerMockito.mock(MongoTemplate.class);
        this.postDAO = new PostDAO(this.mongoTemplate);
    }

    @Test
    public void shouldReturnEmptyListIfCurrentPageAndPageSizeParametersAreNull() {

        assertThat(this.postDAO.getAllPostsByFilters(null, null, null)).isEmpty();
    }

    @Test
    public void shouldExecuteMethodFindOfMongoTemplateQueryWithoutQueryList() {

        this.postDAO.getAllPostsByFilters(0L, 10L, new ArrayList<>());

        Pageable paginatedSortedByPostedDate =
                PageRequest.of(0, 10, Sort.by("createdDate").descending());

        Query query = new Query();
        query.with(paginatedSortedByPostedDate);

        ArgumentCaptor<Query> queryArgumentCaptor = forClass(Query.class);

        verify(mongoTemplate).find(queryArgumentCaptor.capture(), Mockito.eq(Post.class));

        Query queryParameter = queryArgumentCaptor.getValue();

        assertThat(queryParameter).isNotNull();
        assertThat(queryParameter).isEqualTo(query);
    }

    @Test
    public void shouldExecuteMethodFindOfMongoTemplateQueryWithCorrectQueryListWithCriteriaList() {

        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(where("person.$id")
                .in(Arrays.asList("1")));

        this.postDAO.getAllPostsByFilters(0L, 10L, criteriaList);

        Pageable paginatedSortedByPostedDate =
                PageRequest.of(0, 10, Sort.by("createdDate").descending());

        Query query = new Query();
        query.with(paginatedSortedByPostedDate);

        Criteria andOr = new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
        query.addCriteria(andOr);

        ArgumentCaptor<Query> queryArgumentCaptor = forClass(Query.class);

        verify(mongoTemplate).find(queryArgumentCaptor.capture(), Mockito.eq(Post.class));

        Query queryParameter = queryArgumentCaptor.getValue();

        assertThat(queryParameter).isNotNull();
        assertThat(queryParameter).isEqualTo(query);
    }
}