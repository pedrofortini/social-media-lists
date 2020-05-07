package com.social.media.lists.api.application;

public interface MessageConstants {

    String MESSAGE_INVALID_DATE_FORMAT = "Invalid date provided %s";
    String MESSAGE_PEOPLE_LIST_ALREADY_EXISTS = "List of People with name %s already exists";
    String MESSAGE_SOCIAL_MEDIA_NETWORK_NOT_FOUND = "Couldn't find Social Media Network with name %s";
    String MESSAGE_ERROR_PERSISTING_SOCIAL_MEDIA_NETWORK = "An error ocurred while persisting Social Media Network with name %s";
    String MESSAGE_ERROR_PERSISTING_PERSON = "An error ocurred while persisting Person with ssn %s";
    String MESSAGE_ERROR_PERSISTING_PEOPLE_LIST = "An error ocurred while persisting List of People with name %s";
}
