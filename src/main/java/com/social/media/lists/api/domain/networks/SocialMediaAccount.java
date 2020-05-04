package com.social.media.lists.api.domain.networks;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.people.Person;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "social_media_accounts")
@TypeAlias("social_media_account")
@CompoundIndex(name = "login_scnid_personid_idx", def = "{'login' : 1, 'socialMediaNetwork.id' : 1, 'person.id' : 1 }", unique = true)
public class SocialMediaAccount {

    @Id
    private String id;

    @Field("login")
    private String login;

    @DBRef
    @Field("social_media_network")
    private SocialMediaNetwork socialMediaNetwork;

    @DBRef
    @Field("person")
    private Person person;

    /**
     * Factory constructor for creating DbRefs
     * @param id
     */
    private SocialMediaAccount(String id) {
        this.id = id;
    }

    public static SocialMediaAccount ref(String id) {
        return new SocialMediaAccount(id);
    }

    public SocialMediaAccount(){}

    public SocialMediaAccount(String login, final SocialMediaNetwork socialMediaNetwork,
                              final Person person){

        this.login = login;
        this.socialMediaNetwork = socialMediaNetwork;
        this.person = person;
    }
}
