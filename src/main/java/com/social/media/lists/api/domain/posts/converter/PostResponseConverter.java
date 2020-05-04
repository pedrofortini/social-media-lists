package com.social.media.lists.api.domain.posts.converter;

import com.social.media.lists.api.application.ServiceConstants;
import com.social.media.lists.api.domain.posts.Post;
import io.swagger.model.PostResponse;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostResponseConverter {

    public List<PostResponse> convert(List<Post> posts){

        return posts.stream().map(post -> convert(post)).collect(Collectors.toList());
    }

    public PostResponse convert(Post post){

        PostResponse postResponse = new PostResponse();

        postResponse.setCreatedDate(convertDateToString(post.getCreatedDate()));
        postResponse.setLinkOriginalPost(post.getLinkPostOriginalNetwork());
        postResponse.setAuthorName(post.getAccount().getPerson().getFullName());
        postResponse.setListsBelongsTo(post.getAccount().getPerson().getListsBelongsTo());
        postResponse.setSocialNetwork(post.getAccount().getSocialMediaNetwork().getName());
        postResponse.setPostContentSnippet(getPostContentSnippet(post.getContent()));

        return postResponse;
    }

    private String getPostContentSnippet(String postContent){

        if(postContent.length() <= ServiceConstants.POST_SNIPPET_SIZE){
            return postContent;
        }
        return postContent.substring(0, ServiceConstants.POST_SNIPPET_SIZE);
    }

    private String convertDateToString(Date date){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
