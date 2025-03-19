package com.cyager.spring6resttemplate.client;

import com.cyager.spring6resttemplate.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void listBeersNoBeerName() {
        Page<BeerDTO> resultPage = beerClient.listBeers(null);

        assertThat(resultPage.getContent().size()).isEqualTo(25);
        assertThat(resultPage.getTotalElements()).isEqualTo(2413L);
        assertThat(resultPage.getSize()).isEqualTo(25);
    }

    @Test
    void listBeersWithBeerName() {
        Page<BeerDTO> resultPage = beerClient.listBeers("Lager");

        assertThat(resultPage.getContent().size()).isEqualTo(25);
        assertThat(resultPage.getTotalElements()).isEqualTo(110L);
        assertThat(resultPage.getSize()).isEqualTo(25);
    }

}