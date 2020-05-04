package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.people.PeopleList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleListRepository extends MongoRepository<PeopleList, String> {
}
