package com.cyager.spring6resttemplate.client;

import com.cyager.spring6resttemplate.model.BeerDTO;
import com.cyager.spring6resttemplate.model.BeerStyle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient{

    private static final String URL_PATH = "/api/v1/beer";
    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDTO> listBeers(String beerName) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(URL_PATH);

        if (beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }

        ResponseEntity<String> stringResponse =
                restTemplateBuilder.build().getForEntity(uriComponentsBuilder.toUriString(), String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<BeerDTO> beerList = new ArrayList<>();
        try {
            JsonNode responseNode = mapper.readTree(stringResponse.getBody());
            for (JsonNode n : responseNode.get("content")) {
                beerList.add(BeerDTO.builder()
                        .id(UUID.fromString(n.get("id").asText()))
                        .beerName(n.get("beerName").asText())
                        .beerStyle(BeerStyle.valueOf(n.get("beerStyle").asText()))
                        .upc(n.get("upc").asText())
                        .quantityOnHand(n.get("quantityOnHand").asInt())
                        .price(new BigDecimal(n.get("price").asText()))
                        .createdDate(LocalDateTime.parse(n.get("createdDate").asText()))
                        .updateDate(LocalDateTime.parse(n.get("updateDate").asText()))
                        .build());
            }

            int pageNumber = responseNode.get("pageable").get("pageNumber").asInt();
            int pageSize = responseNode.get("pageable").get("pageSize").asInt();
            long total =responseNode.get("totalElements").asLong();
            return new PageImpl<>(beerList, PageRequest.of(pageNumber, pageSize), total);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

