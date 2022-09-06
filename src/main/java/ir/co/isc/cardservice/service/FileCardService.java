package ir.co.isc.cardservice.service;

import ir.co.isc.cardservice.web.dto.SearchCardDto;
import ir.co.isc.cardservice.web.dto.response.GenericRestResponse;

public interface FileCardService {
    GenericRestResponse searchInFile(String directory, SearchCardDto searchCardDto);
}
