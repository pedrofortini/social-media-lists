package com.social.media.lists.api.domain.posts;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.people.Person;
import com.social.media.lists.api.domain.people.SocialMediaAccount;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Document(collection = "posts")
@TypeAlias("post")
public class Post {

    @Id
    private String id;

    @TextIndexed
    private String content;

    @CreatedDate
    @Field("created_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdDate;

    @Field("link_post_original_network")
    private String linkPostOriginalNetwork;

    @DBRef
    @Field("social_network")
    private SocialMediaNetwork network;

    @DBRef
    @Field("person")
    private Person person;


    public Post(){}

    public Post(String content, String linkPostOriginalNetwork,
                final SocialMediaNetwork network,
                final Person person){

        this.content = content;
        this.linkPostOriginalNetwork = linkPostOriginalNetwork;
        this.network = network;
        this.person = person;
    }
}
