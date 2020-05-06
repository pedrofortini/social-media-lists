package com.social.media.lists.api.domain.posts;

import com.social.media.lists.api.application.util.DateUtil;
import com.social.media.lists.api.application.util.StringUtil;
import com.social.media.lists.api.infrastructure.persistence.PostRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Inject
    private PostRepository postRepository;

    @Inject
    private MongoTemplate mongoTemplate;

    public List<Post> getAllPostsByFilters(Long currentPage,
                                           Long pageSize,
                                           String lists,
                                           String networks,
                                           String text,
                                           String userlogin,
                                           String fullname,
                                           String startDate,
                                           String endDate){

        Pageable paginatedSortedByPostedDate =
                PageRequest.of(currentPage.intValue(), pageSize.intValue(), Sort.by("createdDate").descending());

        Query query = new Query();
        query.with(paginatedSortedByPostedDate);

        List<Criteria> criteriaList = new ArrayList<>();
        if(StringUtils.isNotEmpty(lists)){

            criteriaList.add(where("social_media_account.$id.person.$id.lists_belongs_to")
                    .all(StringUtil.convertStringToStringList(lists)));
        }

        if(StringUtils.isNotEmpty(networks)){

            criteriaList.add(Criteria.where("social_media_account.social_media_network.name")
                    .in(StringUtil.convertStringToStringList(networks)));
        }

        if(StringUtils.isNotEmpty(text)){

            criteriaList.add(Criteria.where("content")
                    .regex(text));
        }

        if(StringUtils.isNotEmpty(userlogin)){

            criteriaList.add(Criteria.where("social_media_account.login")
                    .is(userlogin));
        }

        if(StringUtils.isNotEmpty(fullname)){

            criteriaList.add(Criteria.where("social_media_account.person.full_name")
                    .regex(fullname));
        }

        if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){

            criteriaList.add(Criteria.where("created_date").gte(
                    DateUtil.stringToDate(startDate)).lt(DateUtil.stringToDate(endDate)));
        }

        if(!CollectionUtils.isEmpty(criteriaList)) {

            Criteria andOr = new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
            query.addCriteria(andOr);
        }
        System.err.println(query.toString());
        return mongoTemplate.find(query, Post.class);
    }
}
