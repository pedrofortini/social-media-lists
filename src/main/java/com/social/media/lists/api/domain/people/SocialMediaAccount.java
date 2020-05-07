package com.social.media.lists.api.domain.people;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@CompoundIndex(name = "login_scnid_personid_idx", def = "{ 'login' : 1, 'socialMediaNetwork' : 1 }", unique = true)
public class SocialMediaAccount {

    @Field("login")
    private String login;

    @Field("social_media_network")
    private String socialMediaNetwork;

    public SocialMediaAccount(){}

    public SocialMediaAccount(String login, String socialMediaNetwork){

        this.login = login;
        this.socialMediaNetwork = socialMediaNetwork;
    }
}
