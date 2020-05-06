package com.social.media.lists.api.application.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtil {

    public static List<String> convertStringToStringList(String commaSeparatedString){

        if(StringUtils.isNotEmpty(commaSeparatedString)) {

            if(commaSeparatedString.contains(",")) {

                List<String> separatedList = Stream.of(commaSeparatedString.split(",", -1))
                        .collect(Collectors.toList());

                return separatedList.stream().map(s -> s.trim()).collect(Collectors.toList());
            }
            return Arrays.asList(commaSeparatedString);
        }
        return new ArrayList<>();
    }
}
