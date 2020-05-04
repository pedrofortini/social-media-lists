# -*- coding: utf-8 -*-

import json, datetime, random, time

SOCIAL_MEDIA_NETWORK_CLASS_PATH = "com.social.media.lists.api.domain.networks.SocialMediaNetwork"
SOCIAL_MEDIA_ACCOUNT_CLASS_PATH = "com.social.media.lists.api.domain.networks.SocialMediaAccount"
PERSON_CLASS_PATH = "com.social.media.lists.api.domain.people.Person"
PEOPLE_LIST_CLASS_PATH = "com.social.media.lists.api.domain.people.PeopleList"
POST_CLASS_PATH = "com.social.media.lists.api.domain.posts.Post"

NUM_POSTS = 4

DATA_FILE_PATH = "./src/main/resources/data.json"

def generate_social_network_data(jsons):

    social_networks = [{   "_class": "com.social.media.lists.api.domain.networks.SocialMediaNetwork",
                           "name": "Facebook",
                           "mainPageLink": "https://www.facebook.com/",
                           "id": "1"  },

                        {   "_class": "com.social.media.lists.api.domain.networks.SocialMediaNetwork",
                            "name": "Twitter",
                            "mainPageLink": "https://twitter.com/login?lang=pt",
                            "id": "2"}]

    for social_network in social_networks:
        jsons.append(json.dumps(social_network))

def generate_person_data(jsons):

    people = [{ "_class": "com.social.media.lists.api.domain.people.Person",
                "ssn": 123456789,
                "fullName": "Pedro Fortini",
                "listsBelongsTo": ["List1", "List2"],
                "id": "1"  },

              { "_class": "com.social.media.lists.api.domain.people.Person",
                "ssn": 123456787,
                "fullName": "Bruce Dickinson",
                "listsBelongsTo": ["List1"],
                "id": "2"}]

    for person in people:
        jsons.append(json.dumps(person))

def generate_people_list_data(jsons):

    peopleList = [ { "_class": "com.social.media.lists.api.domain.people.PeopleList",
                     "name": "List1",
                     "people": ["1", "2"],
                     "id": "1"  },

                    { "_class": "com.social.media.lists.api.domain.people.PeopleList",
                      "name": "List2",
                      "people": ["1"],
                      "id": "2"}]

    for people in peopleList:
        jsons.append(json.dumps(people))

def generate_social_network_account_data(jsons):

    accounts = [{ "_class": "com.social.media.lists.api.domain.networks.SocialMediaAccount",
                  "socialMediaNetwork": "1",
                  "person": "1",
                  "login": "pedrofortini1",
                  "id": "1"  },
               { "_class": "com.social.media.lists.api.domain.networks.SocialMediaAccount",
                 "socialMediaNetwork": "1",
                 "person": "1",
                 "login": "pedrofortini2",
                  "id": "2"  },
               { "_class": "com.social.media.lists.api.domain.networks.SocialMediaAccount",
                 "socialMediaNetwork": "2",
                 "person": "1",
                 "login": "pedrofortini",
                 "id": "3"  },
               { "_class": "com.social.media.lists.api.domain.networks.SocialMediaAccount",
                 "socialMediaNetwork": "1",
                 "person": "2",
                 "login": "bruce",
                 "id": "4"  },
               { "_class": "com.social.media.lists.api.domain.networks.SocialMediaAccount",
                 "socialMediaNetwork": "2",
                 "person": "2",
                 "login": "bruce1",
                 "id": "5"  }]

    for account in accounts:
        jsons.append(json.dumps(account))

def generate_posts_data(jsons):

    person1_posts = []
    person2_posts = []

    for i in range(NUM_POSTS):

        time.sleep(1)
        isPostFromPerson1 = generate_random_boolean()
        isPostFromFacebook = generate_random_boolean()
        if isPostFromFacebook:
            if(isPostFromPerson1):
                isPostPerson1FromAccount1 = generate_random_boolean()
                if(isPostPerson1FromAccount1):
                    person1_posts.append(generate_post_for_account(i, "1", isPostFromFacebook))
                else:
                    person1_posts.append(generate_post_for_account(i, "2", isPostFromFacebook))

            else:
                person2_posts.append(generate_post_for_account(i, "4", isPostFromFacebook))

        else:
            if isPostFromPerson1:
                person1_posts.append(generate_post_for_account(i, "3", isPostFromFacebook))
            else:
                person2_posts.append(generate_post_for_account(i, "5", isPostFromFacebook))

    for post in person1_posts:
        jsons.append(json.dumps(post))

    for post in person2_posts:
        jsons.append(json.dumps(post))

def generate_post_for_account(postNumber, account, isPostFromFacebook):

    postContent = "Post number: " + str(postNumber)
    linkOriginalPost = "https://"

    if(isPostFromFacebook):
        postContent += " from Facebook"
        linkOriginalPost += "www.facebook.com/" + str(postNumber)

    else:
        postContent += " from Twitter"
        linkOriginalPost += "twitter.com/login?lang=pt/" + str(postNumber)

    return {    "_class": "com.social.media.lists.api.domain.posts.Post",
                "content": postContent,
                "linkPostOriginalNetwork": linkOriginalPost,
                "account": account,
                "createdDate": str(datetime.datetime.now().isoformat()),
                "id": str(postNumber) }


def generate_random_boolean():
    return bool(random.getrandbits(1))

def write_data_to_file(file):

    jsons = []
    generate_social_network_data(jsons)
    generate_person_data(jsons)
    generate_people_list_data(jsons)
    generate_social_network_account_data(jsons)
    generate_posts_data(jsons)

    tam = len(jsons)
    for i in range(tam):
        if i != tam - 1:
            file.write(jsons[i] + ",\n")
        else:
            file.write(jsons[i] + "\n")

if __name__ == "__main__":

    file = open(DATA_FILE_PATH, "w+t")
    file.write("[\n")

    write_data_to_file(file)

    file.write("]")
    file.close()