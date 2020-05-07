package com.social.media.lists.api.domain.people;

import com.social.media.lists.api.application.exception.PersistenceException;
import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.infrastructure.persistence.PeopleListRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

public class PeopleListServiceTest {

    private PeopleListService service;

    private PeopleListRepository peopleListRepository;
    private PersonService personService;

    @Before
    public void setUp() {

        this.peopleListRepository = PowerMockito.mock(PeopleListRepository.class);
        this.personService = PowerMockito.mock(PersonService.class);

        this.service = new PeopleListService(this.peopleListRepository, this.personService);
    }

    @Test
    public void shouldCallMethodFindByNameOfTheRepositoryWhenFindingPeopleListByName() {

        this.service.findPeopleListByName("List1");
        Mockito.verify(peopleListRepository).findByName("List1");
    }

    @Test
    public void shouldCallMethodFindAllOfTheRepositoryWhenFindingAllPeopleLists() {

        this.service.findAllPeopleList();
        Mockito.verify(peopleListRepository).findAll();
    }

    @Test
    public void shouldCallRepositorySaveMethodOnPeopleListWithCorrectData(){

        List<Person> people = new ArrayList<>();
        Person person = new Person();
        people.add(person);
        PeopleList peopleList = new PeopleList("teste", people);
        this.service.savePeopleList(peopleList);

        Mockito.verify(personService).savePerson(person);
        Mockito.verify(peopleListRepository).save(peopleList);
    }

    @Test
    public void shouldThrowPersistenceExceptionWhenProblemPersistingPeopleList(){

        PowerMockito.when(peopleListRepository.save(Mockito.any(PeopleList.class))).thenThrow(new RuntimeException());

        List<Person> people = new ArrayList<>();
        Person person = new Person();
        people.add(person);
        PeopleList peopleList = new PeopleList("teste", people);

        assertThatThrownBy(() -> service.savePeopleList(peopleList))
                .isInstanceOf(PersistenceException.class)
                .hasMessage(String.format("An error ocurred while persisting List of People with name %s",
                        peopleList.getName()));
    }
}