package com.social.media.lists.api.domain.people;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "lists")
@TypeAlias("list")
public class PeopleList {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @DBRef
    private List<Person> people;

    /**
     * Factory constructor for creating DbRefs
     * @param id
     */
    private PeopleList(String id) {
        this.id = id;
    }

    public static PeopleList ref(String id) {
        return new PeopleList(id);
    }

    public PeopleList(){}

    public PeopleList(String name, final List<Person> people){

        this.name = name;
        this.people = people;
    }
}
