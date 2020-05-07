package com.social.media.lists.api.application.controllers;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import com.social.media.lists.api.domain.networks.SocialMediaNetworkService;
import com.social.media.lists.api.domain.networks.converter.SocialMediaNetworkConverter;
import com.social.media.lists.api.domain.people.PeopleList;
import io.swagger.api.NetworksApi;
import io.swagger.model.SocialMediaNetworkTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SocialMediaNetworkController implements NetworksApi {

    private SocialMediaNetworkConverter socialMediaNetworkConverter;
    private SocialMediaNetworkService socialMediaNetworkService;

    public SocialMediaNetworkController(SocialMediaNetworkConverter socialMediaNetworkConverter,
            SocialMediaNetworkService socialMediaNetworkService){

        this.socialMediaNetworkConverter = socialMediaNetworkConverter;
        this.socialMediaNetworkService = socialMediaNetworkService;
    }

    @Override
    public ResponseEntity<List<SocialMediaNetworkTemplate>> getNetworks() {

        List<SocialMediaNetwork> networks = socialMediaNetworkService.findAllSocialMediaNetworks();
        List<SocialMediaNetworkTemplate> networksResponses = socialMediaNetworkConverter.convert(networks);
        return ResponseEntity.ok(networksResponses);
    }

    @Override
    public ResponseEntity<Void> saveNetwork(@Valid @RequestBody SocialMediaNetworkTemplate network) {

        SocialMediaNetwork socialMediaNetwork = socialMediaNetworkConverter.convert(network);
        socialMediaNetworkService.saveSocialMediaNetwork(socialMediaNetwork);

        return ResponseEntity.ok().build();
    }
}
