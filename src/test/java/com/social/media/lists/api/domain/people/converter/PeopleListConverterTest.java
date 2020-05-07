package com.social.media.lists.api.domain.people.converter;

import com.social.media.lists.api.application.MessageConstants;
import com.social.media.lists.api.application.exception.ResourceAlreadyExistsException;
import com.social.media.lists.api.application.exception.UnprocessableEntityException;
import com.social.media.lists.api.application.util.DateUtil;
import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.PeopleListService;
import com.social.media.lists.api.domain.people.Person;
import io.swagger.model.ListTemplate;
import io.swagger.model.PersonTemplate;
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

public class PeopleListConverterTest {

    private PeopleListConverter peopleListConverter;

    private PeopleListService peopleListService;
    private PersonConverter personConverter;

    @Before
    public void setUp() {

        this.peopleListService = PowerMockito.mock(PeopleListService.class);
        this.personConverter = PowerMockito.mock(PersonConverter.class);

        this.peopleListConverter = new PeopleListConverter(this.peopleListService,
                this.personConverter);
    }

    @Test
    public void shouldThrowResourceAlreadyExistsExceptionWithCorrectMessageIfPeopleListWithGivenNameAlreadyExists(){

        ListTemplate listTemplate = new ListTemplate();
        listTemplate.setName("Teste");
        listTemplate.setPeople(new ArrayList<>());

        PowerMockito.when(peopleListService.findPeopleListByName("Teste")).thenReturn(Arrays.asList(
                new PeopleList("Teste", new ArrayList<>())));

        assertThatThrownBy(() -> peopleListConverter.convert(listTemplate))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage(String.format("List of People with name %s already exists",
                        "Teste"));
    }

    @Test
    public void shouldReturnPeopleListWhenConvertingListTemplateSuceeds() {

        ListTemplate listTemplate = new ListTemplate();
        listTemplate.setName("Teste");
        List<PersonTemplate> people = new ArrayList<>();
        people.add(new PersonTemplate());
        listTemplate.setPeople(people);

        PowerMockito.when(peopleListService.findPeopleListByName("Teste")).thenReturn(new ArrayList<>());
        PowerMockito.when(personConverter.convert(Mockito.any(PersonTemplate.class))).thenReturn(new Person());

        PeopleList peopleList = peopleListConverter.convert(listTemplate);

        assertThat(peopleList).isNotNull();
        assertThat(peopleList.getName()).isEqualTo("Teste");
        assertThat(peopleList.getPeople()).isNotEmpty();
    }

    @Test
    public void shouldReturnEmptyPeopleListWhenConvertingEmptyPeopleList() {

        assertThat(peopleListConverter.convert(new ArrayList<>())).isEmpty();
    }

    @Test
    public void shouldReturnListOfListTemplateWhenConvertingListOfPeopleListSuceeds() {

        List<PeopleList> listsPeople = new ArrayList<>();
        PeopleList peopleList = new PeopleList("Teste", new ArrayList<>());
        listsPeople.add(peopleList);

        PowerMockito.when(personConverter.convert(Mockito.any(Person.class))).thenReturn(new PersonTemplate());

        List<ListTemplate> listTemplate = peopleListConverter.convert(listsPeople);

        assertThat(listTemplate).isNotNull();
        assertThat(listTemplate.get(0).getName()).isEqualTo("Teste");
        assertThat(listTemplate.get(0).getPeople()).isEmpty();
    }
}