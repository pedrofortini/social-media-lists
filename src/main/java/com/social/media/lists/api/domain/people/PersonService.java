package com.social.media.lists.api.domain.people;

import com.social.media.lists.api.application.MessageConstants;
import com.social.media.lists.api.application.exception.PersistenceException;
import com.social.media.lists.api.application.exception.ResourceNotFoundException;
import com.social.media.lists.api.infrastructure.persistence.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    Logger logger = LoggerFactory.getLogger(PersonService.class);

    public PersonService(PersonRepository personRepository){

        this.personRepository = personRepository;
    }

    public void savePerson(Person person){

        try {
            this.personRepository.save(person);
        }
        catch (Exception e){

            logger.error(String.format(MessageConstants.MESSAGE_ERROR_PERSISTING_PERSON,
                    person.getSsn()), e);

            throw new PersistenceException(String.format(MessageConstants.MESSAGE_ERROR_PERSISTING_PERSON,
                    person.getSsn()));
        }
    }

    public List<Person> findPersonBySsn(Integer ssn){

        return this.personRepository.findBySsn(ssn);
    }

    public List<String> findPeopleBelongsToLists(List<String> lists){

        return getFilteredResultIdList(this.personRepository.findByListsBelongsTo(lists));
    }

    public List<String> findPeopleByFullname(String fullName){

        return getFilteredResultIdList(this.personRepository.findByFullName(fullName));
    }

    private List<String> getFilteredResultIdList(List<Person> people){

        if(!CollectionUtils.isEmpty(people)){

            return people.stream().map(person -> person.getId()).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
