package com.social.media.lists.api.domain.networks;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "social_media_networks")
@TypeAlias("social_network")
public class SocialMediaNetwork {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @Field("main_page_link")
    private String mainPageLink;

    /**
     * Factory constructor for creating DbRefs
     * @param id
     */
    private SocialMediaNetwork(String id) {
        this.id = id;
    }

    public static SocialMediaNetwork ref(String id) {
        return new SocialMediaNetwork(id);
    }

    public SocialMediaNetwork(){}

    public SocialMediaNetwork(String name, String mainPageLink){

        this.name = name;
        this.mainPageLink = mainPageLink;
    }
}
