package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.posts.Post;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PostDAO {

    private MongoTemplate mongoTemplate;
    private PostRepository postRepository;

    public PostDAO(MongoTemplate mongoTemplate,
                   PostRepository postRepository){

        this.mongoTemplate = mongoTemplate;
        this.postRepository = postRepository;
    }

    public Page<Post> getAllPostsByFilters(Long currentPage,
                                           Long pageSize, List<Criteria> criteriaList){

        List<Post> result = new ArrayList<>();
        if(Objects.nonNull(currentPage) && Objects.nonNull(pageSize)) {

            Pageable paginatedSortedByPostedDate =
                    PageRequest.of(currentPage.intValue(), pageSize.intValue(), Sort.by("createdDate").descending());

            Query query = new Query();
            query.with(paginatedSortedByPostedDate);

            if (!CollectionUtils.isEmpty(criteriaList)) {

                Criteria andOr = new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
                query.addCriteria(andOr);
            }

            result = mongoTemplate.find(query, Post.class);

            long count = mongoTemplate.count(query, Post.class);
            Page<Post> resultPage = new PageImpl<Post>(result , paginatedSortedByPostedDate, count);

            return resultPage;
        }

        return new PageImpl<>(result);
    }
}
