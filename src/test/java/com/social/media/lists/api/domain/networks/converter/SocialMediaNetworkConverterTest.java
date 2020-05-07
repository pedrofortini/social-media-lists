package com.social.media.lists.api.domain.networks.converter;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.Person;
import io.swagger.model.ListTemplate;
import io.swagger.model.PersonTemplate;
import io.swagger.model.SocialMediaAccountTemplate;
import io.swagger.model.SocialMediaNetworkTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class SocialMediaNetworkConverterTest {

    private SocialMediaNetworkConverter converter;

    @Before
    public void setUp() {

        this.converter = new SocialMediaNetworkConverter();
    }

    @Test
    public void shouldReturnEmptySocialMediaNetworkListWhenConvertingEmptyList() {

        assertThat(converter.convert(new ArrayList<>())).isEmpty();
    }

    @Test
    public void shouldReturnListOfSocialMediaNetworkTemplateWhenConvertingListOfSocialMediaNetwoksSuceeds() {

        List<SocialMediaNetwork> networks = new ArrayList<>();
        networks.add(new SocialMediaNetwork("Facebook", "localhost"));

        List<SocialMediaNetworkTemplate> listTemplate = converter.convert(networks);

        assertThat(listTemplate).isNotNull();
        assertThat(listTemplate.get(0).getName()).isEqualTo("Facebook");
        assertThat(listTemplate.get(0).getMainPageLink()).isEqualTo("localhost");
    }

    @Test
    public void shouldReturnSocialMediaNetworkWhenConvertingSocialMediaNetwokTemplateSuceeds() {

        SocialMediaNetworkTemplate template = new SocialMediaNetworkTemplate();
        template.setName("Facebook");
        template.setMainPageLink("localhost");

        SocialMediaNetwork network = converter.convert(template);

        assertThat(network).isNotNull();
        assertThat(network.getName()).isEqualTo("Facebook");
        assertThat(network.getMainPageLink()).isEqualTo("localhost");
    }
}