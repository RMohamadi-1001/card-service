package ir.co.isc.cardservice.service.serviceImpl;


import ir.co.isc.cardservice.dao.AccountEntity;
import ir.co.isc.cardservice.dao.CardEntity;
import ir.co.isc.cardservice.enums.CardTypeEnum;
import ir.co.isc.cardservice.repository.AccountRepository;
import ir.co.isc.cardservice.repository.CardRepository;
import ir.co.isc.cardservice.service.CardService;
import ir.co.isc.cardservice.util.CalenderUtil;
import ir.co.isc.cardservice.util.ConstantsUtil;
import ir.co.isc.cardservice.web.dto.AddCardDto;
import ir.co.isc.cardservice.web.dto.SearchCardDto;
import ir.co.isc.cardservice.web.dto.response.GenericRestResponse;
import ir.co.isc.cardservice.web.error.ErrorConstants;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for Card-Service .
 */

@Service
@Transactional
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private static final ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    private CardRepository cardRepository;
    private AccountRepository accountRepository;

    /**
     * AddCard
     *
     * @param addCardDto adding new card to for an existing account
     * @return GenericRestResponse adding card response
     */
    @Override
    public GenericRestResponse addCard(AddCardDto addCardDto) {
        GenericRestResponse genericRestResponse = null;

        try {
            AccountEntity accountEntity = accountRepository.findByAccountNumber(addCardDto.getAccountNumber());
            genericRestResponse = inputValidation(addCardDto);
            if (genericRestResponse == null) {
                genericRestResponse = addCardValidation(addCardDto, accountEntity);
            }

            if (genericRestResponse == null) {
                genericRestResponse = generateCardEntity(addCardDto, accountEntity);
            }
        } catch (Exception e) {
            logger.error(ErrorConstants.CardMessage.INCORRECT_INPUT_MSG);
            genericRestResponse = new GenericRestResponse(GenericRestResponse.STATUS.FAILURE,
                    e.getMessage());
        }

        return genericRestResponse;
    }

    /**
     * validate input values to adding new card
     *
     * @param addCardDto adding card input data
     * @return GenericRestResponse input validation result
     */
    private GenericRestResponse inputValidation(AddCardDto addCardDto) {
        GenericRestResponse genericRestResponse = null;
        if (addCardDto.getAccountNumber().length() != 10 ||
                addCardDto.getIssuerCode().length() != 6 ||
                addCardDto.getNationalCode().length() != 10) {
            genericRestResponse = new GenericRestResponse(
                    GenericRestResponse.STATUS.FAILURE, ErrorConstants.CardMessage.INCORRECT_INPUT_KEY,
                    ErrorConstants.CardMessage.INCORRECT_INPUT_MSG);
            logger.error(ErrorConstants.CardMessage.INCORRECT_INPUT_MSG);
        }
        return genericRestResponse;
    }


    /**
     * validate logical
     *
     * @param addCardDto adding card input data
     * @return GenericRestResponse logical validation result
     */
    private GenericRestResponse addCardValidation(AddCardDto addCardDto,
                                                  AccountEntity accountEntity) {
        GenericRestResponse genericRestResponse = null;

        if (accountEntity == null) {
            genericRestResponse = new GenericRestResponse(
                    GenericRestResponse.STATUS.FAILURE, ErrorConstants.AccountMessage.ACCOUNT_NOT_VALID_MSG);
            logger.error(ErrorConstants.AccountMessage.ACCOUNT_NOT_VALID_MSG);
        } else if (!accountEntity.getPersonEntity().getNationalCode().equals(addCardDto.getNationalCode())) {
            genericRestResponse = new GenericRestResponse(
                    GenericRestResponse.STATUS.FAILURE, ErrorConstants.CardMessage.PERSON_NOT_MATCH_MSG);
            logger.error(ErrorConstants.CardMessage.PERSON_NOT_MATCH_MSG);
        } else if (accountEntity.getCardEntitySet().stream()
                .anyMatch(c -> (c.getIsActive() ||
                        checkCardExpired(c.getExpireDate())) &&
                        c.getCardType().equals(addCardDto.getCardType()))) {
            genericRestResponse = new GenericRestResponse(
                    GenericRestResponse.STATUS.FAILURE, ErrorConstants.CardMessage.DUPLICATE_CARD_MSG);
            logger.error(ErrorConstants.CardMessage.DUPLICATE_CARD_MSG);
        }
        return genericRestResponse;
    }

    /**
     * generate new Card
     *
     * @param addCardDto adding card input data
     * @return GenericRestResponse generate card response
     */
    private GenericRestResponse generateCardEntity(AddCardDto addCardDto, AccountEntity accountEntity) {
        GenericRestResponse genericRestResponse;
        CardEntity cardEntity = modelMapper.map(addCardDto, CardEntity.class);
        cardEntity.setCardNumber(generateCardNumber(addCardDto));
        cardEntity.setAccountEntity(accountEntity);
        cardEntity.setExpireDate(calculateExpireDate(addCardDto.getCardType()));
        cardEntity.setIsActive(true);
        cardRepository.saveAndFlush(cardEntity);
        genericRestResponse = new GenericRestResponse(GenericRestResponse.STATUS.SUCCESS,
                ConstantsUtil.ResponseMessage.CARD_ADDED_SUCCESSFULLY,
                cardEntity.toString());
        return genericRestResponse;
    }

    /**
     * generate card number
     *
     * @param addCardDto adding card input data
     * @return String generated card number
     */
    private String generateCardNumber(AddCardDto addCardDto) {
        StringBuilder cardNumber = new StringBuilder(addCardDto.getIssuerCode());
        cardNumber.append(String.format("%02d", addCardDto.getCardType().getValue()));
        int min = 1;
        int max = 99999999;
        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        cardNumber.append(String.format("%08d", random_int));
        return cardNumber.toString();
    }

    /**
     * calculate card expire month and year based on cardType and current Date
     *
     * @param cardTypeEnum type of card
     * @return String expire date string value(year && month)
     */
    private String calculateExpireDate(CardTypeEnum cardTypeEnum) {
        Date expireDate = null;
        switch (cardTypeEnum) {
            case CASH:
                expireDate = CalenderUtil.addYear(new Date(), ConstantsUtil.CardValidityValues.CASH_CARD_VALIDATION_YEAR);
                break;
            case CREDIT:
                expireDate = CalenderUtil.addYear(new Date(), ConstantsUtil.CardValidityValues.CREDIT_CARD_VALIDATION_YEAR);
                break;
        }
        return getDateStr(expireDate);
    }

    private String getDateStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String expireDateStr = String.valueOf(calendar.get(Calendar.YEAR)).concat(String.format("%02d", calendar.get(Calendar.MONTH)));
        return expireDateStr;
    }

    /**
     * compare card expire date and current day
     *
     * @param dateStr card expire date
     * @return Boolean checking card expire date result
     */
    private Boolean checkCardExpired(String dateStr) {
        String currentDateStr = getDateStr(new Date());
        if (Integer.parseInt(dateStr.substring(0,4)) < Integer.parseInt(currentDateStr.substring(0, 4))) {
            return true;
        }
        return Integer.parseInt(dateStr.substring(0, 4)) == Integer.parseInt(currentDateStr.substring(0, 4)) &&
                Integer.parseInt(dateStr.substring(4, 6)) < Integer.parseInt(currentDateStr.substring(4, 6));
    }

    /**
     * search for card
     *
     * @param searchCardDto search card input values
     * @return GenericRestResponse card search result
     */
    @Override
    public GenericRestResponse getCard(SearchCardDto searchCardDto) {
        GenericRestResponse genericRestResponse;

        try {
            List<CardEntity> cardEntities = cardRepository.searchForPersonCardType(searchCardDto.getCardNumber(),
                    searchCardDto.getIssuerCode(), searchCardDto.getCardType(), searchCardDto.getNationalCode());
            genericRestResponse = cardValidation(cardEntities);
            if (genericRestResponse == null) {
                //AddCardDto cardDto = modelMapper.map(cardEntity, AddCardDto.class);
                List<AddCardDto> cardDtoList = cardEntities.stream().map(this::convertToDto).collect(Collectors.toList());
                genericRestResponse = new GenericRestResponse(GenericRestResponse.STATUS.SUCCESS,
                        ConstantsUtil.ResponseMessage.CARD_FOUNDED, cardDtoList);
            }
        } catch (Exception e) {
            genericRestResponse = new GenericRestResponse(GenericRestResponse.STATUS.FAILURE,
                    e.getMessage());
        }

        return genericRestResponse;
    }

    private AddCardDto convertToDto(CardEntity cardEntity) {
        return modelMapper.map(cardEntity, AddCardDto.class);
    }

    /**
     * validate founded cardEntity
     *
     * @param cardEntity founded cardEntity
     * @return GenericRestResponse card entity validation response
     */
    private GenericRestResponse cardValidation(List<CardEntity> cardEntity) {
        GenericRestResponse genericRestResponse = null;
        if (cardEntity.isEmpty()) {

            genericRestResponse = new GenericRestResponse(
                    GenericRestResponse.STATUS.FAILURE, ErrorConstants.CardMessage.NOT_FOUND_KEY,
                    ErrorConstants.CardMessage.NOT_FOUND_MSG);
            logger.error(ErrorConstants.CardMessage.NOT_FOUND_MSG);
        } /*else if (!cardEntity.getIsActive()) {
            genericRestResponse = new GenericRestResponse(
                    GenericRestResponse.STATUS.FAILURE, ErrorConstants.CardMessage.NOT_ACTIVE_KEY,
                    ErrorConstants.CardMessage.NOT_ACTIVE_MSG);
            logger.error(ErrorConstants.CardMessage.NOT_ACTIVE_MSG);
        } else if (checkCardExpired(cardEntity.getExpireDate())) {
            genericRestResponse = new GenericRestResponse(
                    GenericRestResponse.STATUS.FAILURE, ErrorConstants.CardMessage.EXPIRED_KEY,
                    ErrorConstants.CardMessage.EXPIRED_MSG);
            logger.error(ErrorConstants.CardMessage.EXPIRED_MSG);
        }*/
        return genericRestResponse;
    }

}
