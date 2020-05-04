package com.social.media.lists.api.domain.people;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Document(collection = "people")
@TypeAlias("person")
public class Person {

    @Id
    private String id;

    @Indexed(unique = true)
    private Integer ssn;

    @Indexed
    @Field("full_name")
    private String fullName;

    @Field("lists_belongs_to")
    private List<String> listsBelongsTo;

    /**
     * Factory constructor for creating DbRefs
     * @param id
     */
    private Person(String id) {
        this.id = id;
    }

    public static Person ref(String id) {
        return new Person(id);
    }

    public Person(){}

    public Person(Integer ssn, String fullName, List<String> listsBelongsTo){

        this.ssn = ssn;
        this.fullName = fullName;
        this.listsBelongsTo = listsBelongsTo;
    }
}
