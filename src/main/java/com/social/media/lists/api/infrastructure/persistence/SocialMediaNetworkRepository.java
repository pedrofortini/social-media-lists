package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialMediaNetworkRepository extends MongoRepository<SocialMediaNetwork, String> {
}
