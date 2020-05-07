package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialMediaNetworkRepository extends MongoRepository<SocialMediaNetwork, String> {

    @Query("{ 'name' : { $in: ?0 } }")
    List<SocialMediaNetwork> findByNameInList(List<String> networks);

    List<SocialMediaNetwork> findByName(String name);
}
