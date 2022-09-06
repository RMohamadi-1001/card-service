package ir.co.isc.cardservice.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ConstantsUtil {

    public static class ResponseMessage {
        public static final String CARD_FOUNDED = "Card Founded";
        public static final String CARD_ADDED_SUCCESSFULLY = "Card Added Successfully.";
    }

    public static class CardValidityValues {
        public static final int CASH_CARD_VALIDATION_YEAR = 2;
        public static final int CREDIT_CARD_VALIDATION_YEAR = 5;
    }

    public static class SpecialCharacter {
        public static final String COMMA = ",";
    }
}