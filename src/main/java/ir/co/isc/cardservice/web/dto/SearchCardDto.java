package ir.co.isc.cardservice.web.dto;

import ir.co.isc.cardservice.enums.CardTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCardDto {

    private String nationalCode;

    private String cardNumber;

    private CardTypeEnum cardType;

    private String issuerCode;}
