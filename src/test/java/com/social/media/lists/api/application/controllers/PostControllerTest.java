package com.social.media.lists.api.application.controllers;

import com.social.media.lists.api.domain.posts.Post;
import com.social.media.lists.api.domain.posts.PostService;
import com.social.media.lists.api.domain.posts.converter.PostResponseConverter;
import io.swagger.model.PostResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class PostControllerTest {

    private PostController controller;

    private PostService postService;
    private PostResponseConverter postResponseConverter;

    @Before
    public void setUp() {

        this.postService = PowerMockito.mock(PostService.class);
        this.postResponseConverter = PowerMockito.mock(PostResponseConverter.class);

        this.controller = new PostController(this.postService, this.postResponseConverter);
    }

    @Test
    public void shouldExecuteMethodsGetAllPostsByFiltersAndConvertWhenGetPostsData(){

        Post post = new Post();
        PostResponse response = new PostResponse();

        PowerMockito.when(this.postService.getAllPostsByFilters(
                0L, 10L, null, null,
                null, null, null, null)).thenReturn(Arrays.asList(post));

        PowerMockito.when(this.postResponseConverter.convert(Arrays.asList(post))).thenReturn(Arrays.asList(response));

        controller.getPostsData(0L, 10L, null, null,
                null, null, null, null);

        Mockito.verify(this.postService).getAllPostsByFilters(
                0L, 10L, null, null,
                null, null, null, null);
        Mockito.verify(this.postResponseConverter).convert(Arrays.asList(post));
    }

    @Test
    public void shouldReturnResponseEntityOKWhenGetAllPostsByFiltersEConvertWhenGetPostsData(){

        Post post = new Post();
        PostResponse response = new PostResponse();

        PowerMockito.when(this.postService.getAllPostsByFilters(
                0L, 10L, null, null,
                null, null, null, null)).thenReturn(Arrays.asList(post));

        PowerMockito.when(this.postResponseConverter.convert(Arrays.asList(post))).thenReturn(Arrays.asList(response));

        ResponseEntity responseEntity = controller.getPostsData(0L, 10L, null, null,
                null, null, null, null);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}