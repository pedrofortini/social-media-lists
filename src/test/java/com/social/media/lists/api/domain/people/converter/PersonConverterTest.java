package com.social.media.lists.api.domain.people.converter;

import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.Person;
import com.social.media.lists.api.domain.people.PersonService;
import com.social.media.lists.api.domain.people.SocialMediaAccount;
import io.swagger.model.ListTemplate;
import io.swagger.model.PersonTemplate;
import io.swagger.model.SocialMediaAccountTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class PersonConverterTest {

    private PersonConverter personConverter;

    private PersonService personService;
    private SocialMediaAccountConverter socialMediaAccountConverter;

    @Before
    public void setUp() {

        this.personService = PowerMockito.mock(PersonService.class);
        this.socialMediaAccountConverter = PowerMockito.mock(SocialMediaAccountConverter.class);

        this.personConverter = new PersonConverter(this.personService, this.socialMediaAccountConverter);
    }

    @Test
    public void shouldReturnPersonOnDbWhenConvertingPersonTemplateSuceeds() {

        PersonTemplate personTemplate = new PersonTemplate();
        personTemplate.setFullname("Teste");
        personTemplate.setSsn(1L);
        personTemplate.setListsBelongsTo(new ArrayList<>());
        personTemplate.setAccounts(new ArrayList<>());

        List<Person> people = new ArrayList<>();
        Person person = new Person();
        person.setSsn(new Integer(1));
        person.setListsBelongsTo(new HashSet<>());
        people.add(person);

        PowerMockito.when(personService.findPersonBySsn(new Integer(1)))
                .thenReturn(people);
        PowerMockito.when(socialMediaAccountConverter.convert(Mockito.any(SocialMediaAccountTemplate.class)))
                .thenReturn(new SocialMediaAccount());

        Person personConverted = personConverter.convert(personTemplate);

        assertThat(personConverted).isNotNull();
        assertThat(personConverted.getSsn()).isEqualTo(1);
        assertThat(personConverted.getListsBelongsTo()).isEmpty();
        assertThat(personConverted.getAccounts()).isEmpty();
        assertThat(personConverted.getFullName()).isEqualTo("Teste");
    }

    @Test
    public void shouldReturnNewPersonWhenConvertingPersonTemplateSuceeds() {

        PersonTemplate personTemplate = new PersonTemplate();
        personTemplate.setFullname("Teste");
        personTemplate.setSsn(1L);
        personTemplate.setListsBelongsTo(new ArrayList<>());
        personTemplate.setAccounts(new ArrayList<>());

        PowerMockito.when(personService.findPersonBySsn(new Integer(1)))
                .thenReturn(new ArrayList<>());
        PowerMockito.when(socialMediaAccountConverter.convert(Mockito.any(SocialMediaAccountTemplate.class)))
                .thenReturn(new SocialMediaAccount());

        Person personConverted = personConverter.convert(personTemplate);

        assertThat(personConverted).isNotNull();
        assertThat(personConverted.getSsn()).isEqualTo(1);
        assertThat(personConverted.getListsBelongsTo()).isEmpty();
        assertThat(personConverted.getAccounts()).isEmpty();
        assertThat(personConverted.getFullName()).isEqualTo("Teste");
    }

    @Test
    public void shouldReturnPersonTemplateWhenConvertingPersonSuceeds() {

        Person person = new Person();
        person.setSsn(new Integer(1));
        person.setListsBelongsTo(new HashSet<>());
        person.setAccounts(new ArrayList<>());
        person.setSsn(1);
        person.setFullName("Teste");

        PowerMockito.when(socialMediaAccountConverter.convert(Mockito.any(SocialMediaAccountTemplate.class)))
                .thenReturn(new SocialMediaAccount());

        PersonTemplate personConverted = personConverter.convert(person);

        assertThat(personConverted).isNotNull();
        assertThat(personConverted.getSsn()).isEqualTo(1);
        assertThat(personConverted.getListsBelongsTo()).isEmpty();
        assertThat(personConverted.getAccounts()).isEmpty();
        assertThat(personConverted.getFullname()).isEqualTo("Teste");
    }
}