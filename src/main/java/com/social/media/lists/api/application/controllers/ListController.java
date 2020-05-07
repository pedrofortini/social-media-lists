package com.social.media.lists.api.application.controllers;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.PeopleListService;
import com.social.media.lists.api.domain.people.converter.PeopleListConverter;
import com.social.media.lists.api.domain.posts.PostService;
import com.social.media.lists.api.domain.posts.converter.PostResponseConverter;
import io.swagger.api.ListsApi;
import io.swagger.model.ListTemplate;
import io.swagger.model.SocialMediaNetworkTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ListController implements ListsApi {

    private PeopleListConverter peopleListConverter;
    private PeopleListService peopleListService;

    public ListController(PeopleListConverter peopleListConverter,
                          PeopleListService peopleListService){

        this.peopleListConverter = peopleListConverter;
        this.peopleListService = peopleListService;
    }

    @Override
    public ResponseEntity<List<ListTemplate>> getLists() {

        List<PeopleList> peopleList = peopleListService.findAllPeopleList();
        List<ListTemplate> listsTemplates = peopleListConverter.convert(peopleList);
        return ResponseEntity.ok(listsTemplates);
    }

    @Override
    public ResponseEntity<Void> savePeopleList(@Valid @RequestBody ListTemplate list) {

        PeopleList peopleList = peopleListConverter.convert(list);
        peopleListService.savePeopleList(peopleList);

        return ResponseEntity.ok().build();
    }
}
