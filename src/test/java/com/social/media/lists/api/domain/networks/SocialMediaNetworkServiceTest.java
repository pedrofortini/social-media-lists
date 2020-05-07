package com.social.media.lists.api.domain.networks;

import com.social.media.lists.api.application.exception.PersistenceException;
import com.social.media.lists.api.infrastructure.persistence.SocialMediaNetworkRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SocialMediaNetworkServiceTest {

    private SocialMediaNetworkService socialMediaNetworkService;
    private SocialMediaNetworkRepository repository;

    @Before
    public void setUp() {

        this.repository = PowerMockito.mock(SocialMediaNetworkRepository.class);
        this.socialMediaNetworkService = new SocialMediaNetworkService(this.repository);
    }

    @Test
    public void shouldReturnEmptyListIfFindByNameInListReturnsEmptyList() {

        PowerMockito.when(repository.findByNameInList(Arrays.asList("Facebook"))).thenReturn(new ArrayList<>());
        assertThat(socialMediaNetworkService.findByNameInList(Arrays.asList("Facebook"))).isEmpty();
    }

    @Test
    public void shouldReturnListOfNetworksIdsIfFindByNameInListReturnsValidList() {

        SocialMediaNetwork socialMediaNetwork = new SocialMediaNetwork("Facebook", "https://www.facebook.com/");
        socialMediaNetwork.setId("1");
        PowerMockito.when(repository.findByNameInList(Arrays.asList("Facebook"))).thenReturn(Arrays.asList(socialMediaNetwork));

        List<String> networksIds = socialMediaNetworkService.findByNameInList(Arrays.asList("Facebook"));

        assertThat(networksIds).isNotEmpty();
        assertThat(networksIds).isEqualTo(Arrays.asList("1"));
    }

    @Test
    public void shouldCallMethodFindByNameOfTheRepositoryWhenFindingSocialMediaNetworkByName() {

        this.socialMediaNetworkService.findSocialMediaNetworkByName("Facebook");
        Mockito.verify(repository).findByName("Facebook");
    }

    @Test
    public void shouldCallMethodFindAllOfTheRepositoryWhenFindingAllSocialMediaNetworks() {

        this.socialMediaNetworkService.findAllSocialMediaNetworks();
        Mockito.verify(repository).findAll();
    }

    @Test
    public void shouldCallRepositorySaveMethodOnSocialMediaNetworkWithCorrectData(){

        SocialMediaNetwork network = new SocialMediaNetwork();
        this.socialMediaNetworkService.saveSocialMediaNetwork(network);
        Mockito.verify(repository).save(network);
    }

    @Test
    public void shouldThrowPersistenceExceptionWhenProblemPersistingSocialMediaNetwork(){

        PowerMockito.when(repository.save(Mockito.any(SocialMediaNetwork.class))).thenThrow(new RuntimeException());

        SocialMediaNetwork network = new SocialMediaNetwork("Facebook", "localhost");

        assertThatThrownBy(() -> socialMediaNetworkService.saveSocialMediaNetwork(network))
                .isInstanceOf(PersistenceException.class)
                .hasMessage(String.format("An error ocurred while persisting Social Media Network with name %s",
                        network.getName()));
    }
}