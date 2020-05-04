package com.social.media.lists.api.application.controllers;

import com.social.media.lists.api.domain.posts.Post;
import com.social.media.lists.api.domain.posts.PostService;
import com.social.media.lists.api.domain.posts.converter.PostResponseConverter;
import io.swagger.api.PostsApi;
import io.swagger.model.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class PostController implements PostsApi {

   @Inject
   private PostService postService;

   @Inject
   private PostResponseConverter postResponseConverter;

   @Override
   public ResponseEntity<List<PostResponse>> getPostsData(
           @NotNull @RequestParam(value = "currentPage", required = true) Long currentPage,
           @NotNull @RequestParam(value = "pageSize", required = true) Long pageSize) {

       List<Post> dataBasePosts = postService.getAllPostPaginatedAndSortedByPostedDate(currentPage, pageSize);
       List<PostResponse> responses = postResponseConverter.convert(dataBasePosts);
       return ResponseEntity.ok(responses);
   }
}
