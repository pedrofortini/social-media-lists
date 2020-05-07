package com.social.media.lists.api.domain.posts.converter;

import com.social.media.lists.api.application.ServiceConstants;
import com.social.media.lists.api.application.util.DateUtil;
import com.social.media.lists.api.domain.posts.Post;
import io.swagger.model.PostResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PostResponseConverter {

    public List<PostResponse> convert(List<Post> posts){

        return posts.stream().map(post -> convert(post)).collect(Collectors.toList());
    }

    private PostResponse convert(Post post){

        PostResponse postResponse = new PostResponse();

        if(Objects.nonNull(post.getCreatedDate())) postResponse.setCreatedDate(DateUtil.convertDateToString(post.getCreatedDate()));
        postResponse.setLinkOriginalPost(post.getLinkPostOriginalNetwork());

        if(Objects.nonNull(post.getPerson())) postResponse.setAuthorName(post.getPerson().getFullName());
        if(Objects.nonNull(post.getPerson()) && Objects.nonNull(post.getPerson().getListsBelongsTo())){

            postResponse.setListsBelongsTo(StringUtils.join(
                    post.getPerson().getListsBelongsTo(), ","));
        }
        if(Objects.nonNull(post.getNetwork())) postResponse.setSocialNetwork(post.getNetwork().getName());
        postResponse.setPostContent(post.getContent());

        return postResponse;
    }
}
