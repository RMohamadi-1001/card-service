package ir.co.isc.cardservice.service;

import ir.co.isc.cardservice.web.dto.AddCardDto;
import ir.co.isc.cardservice.web.dto.SearchCardDto;
import ir.co.isc.cardservice.web.dto.response.GenericRestResponse;

public interface CardService {
    GenericRestResponse addCard(AddCardDto addCardDto);

    GenericRestResponse getCard(SearchCardDto searchCardDto);

}
