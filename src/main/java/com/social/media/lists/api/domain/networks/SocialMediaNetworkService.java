package com.social.media.lists.api.domain.networks;

import com.social.media.lists.api.application.MessageConstants;
import com.social.media.lists.api.application.exception.PersistenceException;
import com.social.media.lists.api.domain.people.PeopleList;
import com.social.media.lists.api.domain.people.PeopleListService;
import com.social.media.lists.api.domain.people.Person;
import com.social.media.lists.api.infrastructure.persistence.SocialMediaNetworkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialMediaNetworkService {

    private SocialMediaNetworkRepository repository;
    Logger logger = LoggerFactory.getLogger(SocialMediaNetworkService.class);

    public SocialMediaNetworkService(SocialMediaNetworkRepository repository){
        this.repository = repository;
    }

    public List<String> findByNameInList(List<String> networks){

        List<SocialMediaNetwork> socialMediaNetworks = this.repository.findByNameInList(networks);

        if(!CollectionUtils.isEmpty(socialMediaNetworks)){

            return socialMediaNetworks.stream().map(network -> network.getId()).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public List<SocialMediaNetwork> findSocialMediaNetworkByName(String name){

        return this.repository.findByName(name);
    }

    public List<SocialMediaNetwork> findAllSocialMediaNetworks(){

        return this.repository.findAll();
    }

    public void saveSocialMediaNetwork(SocialMediaNetwork network){

        try {
            this.repository.save(network);
        }
        catch (Exception e){

            logger.error(String.format(MessageConstants.MESSAGE_ERROR_PERSISTING_SOCIAL_MEDIA_NETWORK,
                    network.getName()), e);

            throw new PersistenceException(String.format(MessageConstants.MESSAGE_ERROR_PERSISTING_SOCIAL_MEDIA_NETWORK,
                    network.getName()));
        }
    }
}
