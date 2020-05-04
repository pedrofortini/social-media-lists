package com.social.media.lists.api.infrastructure.persistence;

import com.social.media.lists.api.domain.posts.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
}
