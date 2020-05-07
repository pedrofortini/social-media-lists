package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.people.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

    @Query("{ 'lists_belongs_to' : { $all: ?0 } }")
    List<Person> findByListsBelongsTo(List<String> listsBelongsTo);

    List<Person> findByFullName(String fullName);

    List<Person> findBySsn(Integer ssn);
}
