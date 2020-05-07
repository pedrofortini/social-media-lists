package com.social.media.lists.api.application.util;

import com.social.media.lists.api.application.MessageConstants;
import com.social.media.lists.api.application.exception.UnprocessableEntityException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date stringToDate(String dateString){

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date date = formatter.parse(dateString);
            return date;

        } catch (ParseException e) {

            throw new UnprocessableEntityException(
                    String.format(MessageConstants.MESSAGE_INVALID_DATE_FORMAT, dateString));
        }
    }

    public static String convertDateToString(Date date){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        return dateFormat.format(date);
    }
}
