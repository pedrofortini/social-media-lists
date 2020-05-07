package com.social.media.lists.api.domain.people.converter;

import com.social.media.lists.api.application.MessageConstants;
import com.social.media.lists.api.application.exception.ResourceNotFoundException;
import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.networks.SocialMediaNetworkService;
import com.social.media.lists.api.domain.people.SocialMediaAccount;
import io.swagger.model.SocialMediaAccountTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Component
public class SocialMediaAccountConverter {

    private SocialMediaNetworkService socialMediaNetworkService;

    public SocialMediaAccountConverter(SocialMediaNetworkService socialMediaNetworkService){
        this.socialMediaNetworkService = socialMediaNetworkService;
    }

    public SocialMediaAccount convert(SocialMediaAccountTemplate socialMediaAccountTemplate){

        List<SocialMediaNetwork> networksOnDB =
                socialMediaNetworkService.findSocialMediaNetworkByName(
                        socialMediaAccountTemplate.getSocialMediaNetwork());

        if(CollectionUtils.isEmpty(networksOnDB)){

            throw new ResourceNotFoundException(String.format(MessageConstants.MESSAGE_SOCIAL_MEDIA_NETWORK_NOT_FOUND,
                    socialMediaAccountTemplate.getSocialMediaNetwork()));
        }

        return new SocialMediaAccount(socialMediaAccountTemplate.getLogin(),
                networksOnDB.get(0).getName());
    }

    public SocialMediaAccountTemplate convert(SocialMediaAccount account){

        SocialMediaAccountTemplate response = new SocialMediaAccountTemplate();

        response.setLogin(account.getLogin());
        if(Objects.nonNull(account.getSocialMediaNetwork())) {

            response.setSocialMediaNetwork(account.getSocialMediaNetwork());
        }

        return response;
    }
}
