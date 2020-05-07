package com.social.media.lists.api.domain.people.converter;

import com.social.media.lists.api.application.MessageConstants;
import com.social.media.lists.api.application.exception.ResourceAlreadyExistsException;
import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.PeopleListService;
import com.social.media.lists.api.domain.people.Person;
import io.swagger.model.ListTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PeopleListConverter {

    private PeopleListService peopleListService;
    private PersonConverter personConverter;

    public PeopleListConverter(PeopleListService peopleListService,
                               PersonConverter personConverter){

        this.peopleListService = peopleListService;
        this.personConverter = personConverter;
    }

    public PeopleList convert(ListTemplate listRequest){

        List<PeopleList> peopleListsOnDB = peopleListService.findPeopleListByName(listRequest.getName());
        if(!CollectionUtils.isEmpty(peopleListsOnDB)){

            throw new ResourceAlreadyExistsException(String.format(MessageConstants.MESSAGE_PEOPLE_LIST_ALREADY_EXISTS,
                    listRequest.getName()));
        }

        List<Person> peopleList = listRequest.getPeople().stream().
                map(l -> this.personConverter.convert(l)).collect(Collectors.toList());

        return new PeopleList(listRequest.getName(), peopleList);
    }

    public List<ListTemplate> convert(List<PeopleList> lists){

        if(!CollectionUtils.isEmpty(lists)) {
            return lists.stream().map(list -> convert(list)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private ListTemplate convert(PeopleList peopleList){

        ListTemplate response = new ListTemplate();

        response.setName(peopleList.getName());
        response.setPeople(peopleList.getPeople().stream()
                .map(p -> personConverter.convert(p)).collect(Collectors.toList()));

        return response;
    }
}
