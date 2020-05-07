package com.social.media.lists.api.domain.people;

import com.social.media.lists.api.application.exception.PersistenceException;
import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.infrastructure.persistence.PersonRepository;
import com.social.media.lists.api.infrastructure.persistence.SocialMediaNetworkRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

public class PersonServiceTest {

    private PersonService personService;
    private PersonRepository personRepository;

    @Before
    public void setUp() {

        personRepository = PowerMockito.mock(PersonRepository.class);
        personService = new PersonService(this.personRepository);
    }

    @Test
    public void shouldReturnEmptyListIfFindPeopleBelongsToListsReturnsEmptyList() {

        PowerMockito.when(personRepository.findByListsBelongsTo(Arrays.asList("List1"))).thenReturn(new ArrayList<>());
        assertThat(personService.findPeopleBelongsToLists(Arrays.asList("List1"))).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListIfFindPeopleByFullnameReturnsEmptyList() {

        PowerMockito.when(personRepository.findByFullName("Teste")).thenReturn(new ArrayList<>());
        assertThat(personService.findPeopleByFullname("Teste")).isEmpty();
    }

    @Test
    public void shouldReturnListOfPersonIdsIfFindByNameInListReturnsValidList() {

        Person person = new Person(123456, "Teste", new HashSet<>(),
                new ArrayList<>());
        person.setId("1");

        PowerMockito.when(personRepository.findByListsBelongsTo(Arrays.asList("List1"))).thenReturn(Arrays.asList(person));

        List<String> personIds = personService.findPeopleBelongsToLists(Arrays.asList("List1"));

        assertThat(personIds).isNotEmpty();
        assertThat(personIds).isEqualTo(Arrays.asList("1"));
    }

    @Test
    public void shouldReturnListOfPersonIdsIfFindPeopleBelongsToListsReturnsValidList() {

        Person person = new Person(123456, "Teste", new HashSet<>(),
                new ArrayList<>());
        person.setId("1");

        PowerMockito.when(personRepository.findByFullName("Teste")).thenReturn(Arrays.asList(person));

        List<String> personIds = personService.findPeopleByFullname("Teste");

        assertThat(personIds).isNotEmpty();
        assertThat(personIds).isEqualTo(Arrays.asList("1"));
    }

    @Test
    public void shouldCallMethodFindBySsnOfTheRepositoryWhenFindingPersonBySsn() {

        this.personService.findPersonBySsn(1);
        Mockito.verify(personRepository).findBySsn(1);
    }

    @Test
    public void shouldCallRepositorySaveMethodOnPersonWithCorrectData(){

        Person person = new Person();
        this.personService.savePerson(person);

        Mockito.verify(personRepository).save(person);
    }

    @Test
    public void shouldThrowPersistenceExceptionWhenProblemPersistingPerson(){

        PowerMockito.when(personRepository.save(Mockito.any(Person.class))).thenThrow(new RuntimeException());

        Person person = new Person();
        person.setSsn(1);

        assertThatThrownBy(() -> personService.savePerson(person))
                .isInstanceOf(PersistenceException.class)
                .hasMessage(String.format("An error ocurred while persisting Person with ssn %s",
                        person.getSsn()));
    }
}