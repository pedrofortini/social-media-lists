package com.social.media.lists.api.domain.people.converter;

import com.social.media.lists.api.application.MessageConstants;
import com.social.media.lists.api.application.exception.ResourceAlreadyExistsException;
import com.social.media.lists.api.application.exception.ResourceNotFoundException;
import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.networks.SocialMediaNetworkService;
import com.social.media.lists.api.domain.networks.converter.SocialMediaNetworkConverter;
import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.Person;
import com.social.media.lists.api.domain.people.SocialMediaAccount;
import io.swagger.model.ListTemplate;
import io.swagger.model.PersonTemplate;
import io.swagger.model.SocialMediaAccountTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class SocialMediaAccountConverterTest {

    private SocialMediaAccountConverter converter;
    private SocialMediaNetworkService service;

    @Before
    public void setUp() {

        this.service = PowerMockito.mock(SocialMediaNetworkService.class);
        this.converter = new SocialMediaAccountConverter(this.service);
    }

    @Test
    public void shouldThrowResourceAlreadyExistsExceptionWithCorrectMessageIfSocialMediaNetworkDoesntExists(){

        SocialMediaAccountTemplate template = new SocialMediaAccountTemplate();
        template.setLogin("teste");
        template.setSocialMediaNetwork("Facebook");

        PowerMockito.when(service.findSocialMediaNetworkByName("Facebook")).thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> converter.convert(template))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(String.format("Couldn't find Social Media Network with name %s",
                        "Facebook"));
    }

    @Test
    public void shouldReturnNewSocialMediaAccountWhenConvertingSocialMediaAccountTemplateSuceeds() {

        SocialMediaAccountTemplate template = new SocialMediaAccountTemplate();
        template.setLogin("teste");
        template.setSocialMediaNetwork("Facebook");

        List<SocialMediaNetwork> networkList = new ArrayList<>();
        networkList.add(new SocialMediaNetwork("Facebook", "localhost"));

        PowerMockito.when(service.findSocialMediaNetworkByName("Facebook")).thenReturn(networkList);

        SocialMediaAccount account = converter.convert(template);

        assertThat(account).isNotNull();
        assertThat(account.getLogin()).isEqualTo("teste");
        assertThat(account.getSocialMediaNetwork()).isEqualTo("Facebook");
    }

    @Test
    public void shouldReturnSocialMediaAccountTemplateWhenConvertingSocialMediaAccountSuceeds() {

        SocialMediaAccount socialMediaAccount = new SocialMediaAccount();
        socialMediaAccount.setLogin("teste");
        socialMediaAccount.setSocialMediaNetwork("Facebook");

        SocialMediaAccountTemplate accountTemplate = converter.convert(socialMediaAccount);

        assertThat(accountTemplate).isNotNull();
        assertThat(accountTemplate.getLogin()).isEqualTo("teste");
        assertThat(accountTemplate.getSocialMediaNetwork()).isEqualTo("Facebook");
    }
}