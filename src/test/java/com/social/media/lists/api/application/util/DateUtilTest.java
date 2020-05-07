package com.social.media.lists.api.application.util;

import com.social.media.lists.api.application.exception.UnprocessableEntityException;
import com.social.media.lists.api.domain.people.Person;
import com.social.media.lists.api.domain.posts.Post;
import io.swagger.model.PostResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void shouldThrowUnprocessableEntityExceptionWithCorrectMessageIfConversionFailed(){

        assertThatThrownBy(() -> DateUtil.stringToDate("test"))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessage(String.format("Invalid date provided %s", "test"));
    }
}