package com.social.media.lists.api.domain.people.converter;

import com.social.media.lists.api.application.MessageConstants;
import com.social.media.lists.api.application.exception.ResourceAlreadyExistsException;
import com.social.media.lists.api.application.exception.ResourceNotFoundException;
import com.social.media.lists.api.domain.people.*;
import io.swagger.model.ListTemplate;
import io.swagger.model.PersonTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonConverter {

    private PersonService personService;
    private SocialMediaAccountConverter socialMediaAccountConverter;

    public PersonConverter(PersonService personService, SocialMediaAccountConverter socialMediaAccountConverter){

        this.socialMediaAccountConverter = socialMediaAccountConverter;
        this.personService = personService;
    }

    public Person convert(PersonTemplate personTemplate){

        Set<String> listsBelongsTo = new HashSet<>(personTemplate.getListsBelongsTo());
        List<SocialMediaAccount> accounts = personTemplate.getAccounts().stream().
                map(a -> socialMediaAccountConverter.convert(a)).collect(Collectors.toList());

        List<Person> peopleOnDB = personService.findPersonBySsn(new Integer(personTemplate.getSsn().intValue()));
        if(!CollectionUtils.isEmpty(peopleOnDB)){

            Person personOnDB = peopleOnDB.get(0);

            personOnDB.getListsBelongsTo().addAll(listsBelongsTo);
            personOnDB.setFullName(personTemplate.getFullname());
            personOnDB.setAccounts(accounts);

            return personOnDB;
        }

        return new Person(new Integer(personTemplate.getSsn().intValue()), personTemplate.getFullname(),
                listsBelongsTo, accounts);
    }

    public PersonTemplate convert(Person person){

        PersonTemplate response = new PersonTemplate();

        response.setAccounts(person.getAccounts().stream().
                map(a -> socialMediaAccountConverter.convert(a)).collect(Collectors.toList()));
        response.setFullname(person.getFullName());
        response.setListsBelongsTo(new ArrayList<>(person.getListsBelongsTo()));
        response.setSsn(new Long(person.getSsn()));

        return response;
    }
}
