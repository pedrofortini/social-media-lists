package com.social.media.lists.api.domain.people;

import com.social.media.lists.api.application.MessageConstants;
import com.social.media.lists.api.application.exception.PersistenceException;
import com.social.media.lists.api.infrastructure.persistence.PeopleListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleListService {

    private PeopleListRepository peopleListRepository;
    private PersonService personService;

    Logger logger = LoggerFactory.getLogger(PeopleListService.class);

    public PeopleListService(PeopleListRepository peopleListRepository,
                             PersonService personService){

        this.peopleListRepository = peopleListRepository;
        this.personService = personService;
    }

    public List<PeopleList> findPeopleListByName(String name){

        return this.peopleListRepository.findByName(name);
    }

    public List<PeopleList> findAllPeopleList(){

        return this.peopleListRepository.findAll();
    }

    public void savePeopleList(PeopleList peopleList){

        try {
            peopleList.getPeople().forEach(
                   p -> personService.savePerson(p));
            this.peopleListRepository.save(peopleList);
        }
        catch (Exception e){

            logger.error(String.format(MessageConstants.MESSAGE_ERROR_PERSISTING_PEOPLE_LIST,
                    peopleList.getName()), e);

            throw new PersistenceException(String.format(MessageConstants.MESSAGE_ERROR_PERSISTING_PEOPLE_LIST,
                    peopleList.getName()));
        }
    }
}
