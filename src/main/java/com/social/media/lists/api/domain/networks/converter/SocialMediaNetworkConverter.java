package com.social.media.lists.api.domain.networks.converter;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.posts.Post;
import io.swagger.model.PostResponse;
import io.swagger.model.SocialMediaNetworkTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SocialMediaNetworkConverter {

    public SocialMediaNetwork convert(SocialMediaNetworkTemplate networkTemplate){

        return new SocialMediaNetwork(networkTemplate.getName(), networkTemplate.getMainPageLink());
    }

    public List<SocialMediaNetworkTemplate> convert(List<SocialMediaNetwork> networks){

        if(!CollectionUtils.isEmpty(networks)){
            return networks.stream().map(network -> convert(network)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private SocialMediaNetworkTemplate convert(SocialMediaNetwork network){

        SocialMediaNetworkTemplate response = new SocialMediaNetworkTemplate();

        response.setName(network.getName());
        response.setMainPageLink(network.getMainPageLink());

        return response;
    }
}
