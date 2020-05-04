package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.networks.SocialMediaAccount;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialMediaAccountRepository extends MongoRepository<SocialMediaAccount, String> {
}
