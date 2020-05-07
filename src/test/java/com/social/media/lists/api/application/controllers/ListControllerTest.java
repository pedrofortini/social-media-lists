package com.social.media.lists.api.application.controllers;

import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.PeopleListService;
import com.social.media.lists.api.domain.people.converter.PeopleListConverter;
import com.social.media.lists.api.domain.posts.Post;
import io.swagger.model.ListTemplate;
import io.swagger.model.PostResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ListControllerTest {

    private ListController controller;

    private PeopleListConverter peopleListConverter;
    private PeopleListService peopleListService;

    @Before
    public void setUp() {

        this.peopleListConverter = PowerMockito.mock(PeopleListConverter.class);
        this.peopleListService = PowerMockito.mock(PeopleListService.class);

        this.controller = new ListController(this.peopleListConverter, this.peopleListService);
    }

    @Test
    public void shouldExecuteMethodsFindAllPeopleListAndConvertWhenGetPeopleListsData(){

        List<PeopleList> peopleList = new ArrayList<>();
        PowerMockito.when(this.peopleListService.findAllPeopleList()).thenReturn(peopleList);

        this.controller.getLists();

        Mockito.verify(this.peopleListService).findAllPeopleList();
        Mockito.verify(this.peopleListConverter).convert(peopleList);
    }

    @Test
    public void shouldReturnResponseEntityOKWhenGetAllPeopleListsData(){

        List<PeopleList> peopleList = new ArrayList<>();
        PowerMockito.when(this.peopleListService.findAllPeopleList()).thenReturn(peopleList);

        ResponseEntity responseEntity = this.controller.getLists();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldExecuteMethodsConvertAndSavePeopleListWhenSavingPeopleListsData(){

        PeopleList peopleList = new PeopleList();
        ListTemplate template = new ListTemplate();
        PowerMockito.when(this.peopleListConverter.convert(template)).thenReturn(peopleList);

        this.controller.savePeopleList(template);

        Mockito.verify(this.peopleListConverter).convert(template);
        Mockito.verify(this.peopleListService).savePeopleList(peopleList);
    }

    @Test
    public void shouldReturnResponseEntityOKWhenSavingPeopleListsData(){

        PeopleList peopleList = new PeopleList();
        ListTemplate template = new ListTemplate();
        PowerMockito.when(this.peopleListConverter.convert(template)).thenReturn(peopleList);

        ResponseEntity responseEntity = this.controller.savePeopleList(template);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}