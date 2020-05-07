package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.posts.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PostDAO {

    private MongoTemplate mongoTemplate;

    public PostDAO(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<Post> getAllPostsByFilters(Long currentPage,
                                           Long pageSize, List<Criteria> criteriaList){

        if(Objects.nonNull(currentPage) && Objects.nonNull(pageSize)) {

            Pageable paginatedSortedByPostedDate =
                    PageRequest.of(currentPage.intValue(), pageSize.intValue(), Sort.by("createdDate").descending());

            Query query = new Query();
            query.with(paginatedSortedByPostedDate);

            if (!CollectionUtils.isEmpty(criteriaList)) {

                Criteria andOr = new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
                query.addCriteria(andOr);
            }
            return mongoTemplate.find(query, Post.class);
        }

        return new ArrayList<>();
    }
}
