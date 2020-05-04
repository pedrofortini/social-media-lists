package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.people.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
}
