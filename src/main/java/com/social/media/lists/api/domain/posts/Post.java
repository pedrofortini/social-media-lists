package com.social.media.lists.api.domain.posts;

import com.social.media.lists.api.domain.networks.SocialMediaAccount;
import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.Person;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
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
    @Field("social_media_account")
    private SocialMediaAccount account;

    /**
     * Factory constructor for creating DbRefs
     * @param id
     */
    private Post(String id) {
        this.id = id;
    }

    public static Post ref(String id) {
        return new Post(id);
    }

    public Post(){}

    public Post(String content, String linkPostOriginalNetwork,
                final SocialMediaAccount account){

        this.content = content;
        this.linkPostOriginalNetwork = linkPostOriginalNetwork;
        this.account = account;
    }
}
