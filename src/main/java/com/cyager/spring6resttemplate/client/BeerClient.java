package com.cyager.spring6resttemplate.client;

import com.cyager.spring6resttemplate.model.BeerDTO;
import org.springframework.data.domain.Page;


public interface BeerClient {
    Page<BeerDTO> listBeers();
}
