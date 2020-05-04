package com.social.media.lists.api.domain.networks;

import com.social.media.lists.api.infrastructure.persistence.SocialMediaNetworkRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SocialMediaNetworkService {

    @Inject
    private SocialMediaNetworkRepository repository;

    public List<SocialMediaNetwork> getAllSocialNetworks(){

        return this.repository.findAll();
    }

}
