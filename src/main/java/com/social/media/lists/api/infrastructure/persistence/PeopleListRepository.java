package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleListRepository extends MongoRepository<PeopleList, String> {

    List<PeopleList> findByName(String name);
}
