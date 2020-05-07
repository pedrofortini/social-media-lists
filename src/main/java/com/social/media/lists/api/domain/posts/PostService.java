package com.social.media.lists.api.domain.posts;

import com.social.media.lists.api.application.util.DateUtil;
import com.social.media.lists.api.application.util.StringUtil;
import com.social.media.lists.api.domain.networks.SocialMediaNetworkService;
import com.social.media.lists.api.domain.people.PersonService;
import com.social.media.lists.api.infrastructure.persistence.PostDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class PostService {

    private PostDAO postDAO;
    private PersonService personService;
    private SocialMediaNetworkService socialMediaNetworkService;

    public PostService(PostDAO postDAO,
                       PersonService personService,
                       SocialMediaNetworkService socialMediaNetworkService){

        this.postDAO = postDAO;
        this.personService = personService;
        this.socialMediaNetworkService = socialMediaNetworkService;
    }

    public List<Post> getAllPostsByFilters(Long currentPage,
                                           Long pageSize,
                                           String lists,
                                           String networks,
                                           String text,
                                           String fullname,
                                           String startDate,
                                           String endDate){

        List<Criteria> criteriaList = new ArrayList<>();
        if(StringUtils.isNotEmpty(lists)){

            List<String> peopleInLists = this.personService.
                    findPeopleBelongsToLists(StringUtil.convertStringToStringList(lists));

            criteriaList.add(where("person.$id")
                    .in(peopleInLists));
        }

        if(StringUtils.isNotEmpty(networks)){

            List<String> networksInList = this.socialMediaNetworkService.
                    findByNameInList(StringUtil.convertStringToStringList(networks));

            criteriaList.add(Criteria.where("social_network.$id")
                    .in(networksInList));
        }

        if(StringUtils.isNotEmpty(text)){

            criteriaList.add(Criteria.where("content")
                    .regex(text));
        }

        if(StringUtils.isNotEmpty(fullname)){

            List<String> peopleWithFullName = this.personService.
                    findPeopleByFullname(fullname);

            criteriaList.add(where("person.$id")
                    .in(peopleWithFullName));
        }

        if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){

            criteriaList.add(Criteria.where("created_date").gte(
                    DateUtil.stringToDate(startDate)).lte(DateUtil.stringToDate(endDate)));
        }

        return postDAO.getAllPostsByFilters(currentPage, pageSize, criteriaList);
    }
}
