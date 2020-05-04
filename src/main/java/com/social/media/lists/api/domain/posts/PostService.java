package com.social.media.lists.api.domain.posts;

import com.social.media.lists.api.infrastructure.persistence.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Inject
    private PostRepository postRepository;

    public List<Post> getAllPostPaginatedAndSortedByPostedDate(Long currentPage, Long pageSize){

        Pageable paginatedSortedByPostedDate =
                PageRequest.of(currentPage.intValue(), pageSize.intValue(), Sort.by("createdDate").descending());

        Slice<Post> posts = this.postRepository.findAll(paginatedSortedByPostedDate);

        if(posts.hasContent()){
            return posts.getContent();
        }
        return new ArrayList<>();
    }
}
