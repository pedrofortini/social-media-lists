package com.social.media.lists.api.application.controllers;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.networks.SocialMediaNetworkService;
import com.social.media.lists.api.domain.networks.converter.SocialMediaNetworkConverter;
import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.PeopleListService;
import com.social.media.lists.api.domain.people.converter.PeopleListConverter;
import io.swagger.model.ListTemplate;
import io.swagger.model.SocialMediaNetworkTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class SocialMediaNetworkControllerTest {

    private SocialMediaNetworkController controller;

    private SocialMediaNetworkConverter socialMediaNetworkConverter;
    private SocialMediaNetworkService socialMediaNetworkService;

    @Before
    public void setUp() {

        this.socialMediaNetworkConverter = PowerMockito.mock(SocialMediaNetworkConverter.class);
        this.socialMediaNetworkService = PowerMockito.mock(SocialMediaNetworkService.class);

        this.controller = new SocialMediaNetworkController(this.socialMediaNetworkConverter,
                this.socialMediaNetworkService);
    }

    @Test
    public void shouldExecuteMethodsFindAllSocialMediaNetworsAndConvertWhenGetSocialMediaNetworksData(){

        List<SocialMediaNetwork> networks = new ArrayList<>();
        PowerMockito.when(this.socialMediaNetworkService.findAllSocialMediaNetworks()).thenReturn(networks);

        this.controller.getNetworks();

        Mockito.verify(this.socialMediaNetworkService).findAllSocialMediaNetworks();
        Mockito.verify(this.socialMediaNetworkConverter).convert(networks);
    }

    @Test
    public void shouldReturnResponseEntityOKWhenGetAllSocialMediaNetworkData(){

        List<SocialMediaNetwork> networks = new ArrayList<>();
        PowerMockito.when(this.socialMediaNetworkService.findAllSocialMediaNetworks()).thenReturn(networks);

        ResponseEntity responseEntity = this.controller.getNetworks();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldExecuteMethodsConvertAndSaveSocialMediaNetworkWhenSavingSocialMediaNetworkData(){

        SocialMediaNetworkTemplate socialMediaNetworkTemplate = new SocialMediaNetworkTemplate();
        SocialMediaNetwork socialMediaNetwork = new SocialMediaNetwork();
        PowerMockito.when(this.socialMediaNetworkConverter
                .convert(socialMediaNetworkTemplate)).thenReturn(socialMediaNetwork);

        this.controller.saveNetwork(socialMediaNetworkTemplate);

        Mockito.verify(this.socialMediaNetworkConverter).convert(socialMediaNetworkTemplate);
        Mockito.verify(this.socialMediaNetworkService).saveSocialMediaNetwork(socialMediaNetwork);
    }

    @Test
    public void shouldReturnResponseEntityOKWhenSavingSocialMediaNetworkData(){

        SocialMediaNetworkTemplate socialMediaNetworkTemplate = new SocialMediaNetworkTemplate();
        SocialMediaNetwork socialMediaNetwork = new SocialMediaNetwork();
        PowerMockito.when(this.socialMediaNetworkConverter
                .convert(socialMediaNetworkTemplate)).thenReturn(socialMediaNetwork);

        ResponseEntity responseEntity = this.controller.saveNetwork(socialMediaNetworkTemplate);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}