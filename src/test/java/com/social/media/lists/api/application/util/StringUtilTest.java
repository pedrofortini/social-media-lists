package com.social.media.lists.api.application.util;

import com.social.media.lists.api.domain.networks.SocialMediaNetwork;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void shouldReturnEmptyListIfStringIsNull() {

        assertThat(StringUtil.convertStringToStringList(null)).isEmpty();
    }

    @Test
    public void shouldReturnCorrectListIfStringIsCommaSeparatedValues() {

        assertThat(StringUtil.convertStringToStringList("List1, List2")).
            isEqualTo(Arrays.asList("List1", "List2"));
    }

    @Test
    public void shouldReturnCorrectListIfStringHasOneValue() {

        assertThat(StringUtil.convertStringToStringList("List1")).
                isEqualTo(Arrays.asList("List1"));
    }
}