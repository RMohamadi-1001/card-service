package ir.co.isc.cardservice.web.controller;

import ir.co.isc.cardservice.service.CardService;
import ir.co.isc.cardservice.web.dto.AddCardDto;
import ir.co.isc.cardservice.web.dto.SearchCardDto;
import ir.co.isc.cardservice.web.dto.response.GenericRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card-service")
@AllArgsConstructor
@Api(value = "Card Service API")
public class CardController {
    private final Logger logger = LoggerFactory.getLogger(CardController.class);

    private CardService cardService;

    @PostMapping(value = "/addCard")
    @ApiOperation(value = "REST request to Add Card",
            produces = "Application/JSON", response = GenericRestResponse.class, httpMethod = "POST")
    public GenericRestResponse addCard(
            @ApiParam(value = "AddCardDto", required = true)
            @RequestBody AddCardDto addCardDto) {
        logger.debug("REST request to save Card : {}", addCardDto);
        return cardService.addCard(addCardDto);
    }

    @PostMapping(value = "/getCard")
    @ApiOperation(value = "REST request to Search Card",
            produces = "Application/JSON", response = GenericRestResponse.class, httpMethod = "POST")
    public GenericRestResponse getCard(
            @ApiParam(value = "SearchCardDto", required = true)
            @RequestBody SearchCardDto searchCardDto) {
        logger.debug("REST request to search Card : {}", searchCardDto);
        return cardService.getCard(searchCardDto);
    }
}
