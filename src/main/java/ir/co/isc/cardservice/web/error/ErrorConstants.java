package ir.co.isc.cardservice.web.error;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://www.isc.com/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");

    private ErrorConstants() {
    }

    public static class AccountMessage {
        public static final String ACCOUNT_NOT_VALID_KEY = "accountNotValid";
        public static final String ACCOUNT_NOT_VALID_MSG = "The Account Is not Valid";
    }

    public static class CardMessage {
        public static final String INCORRECT_INPUT_KEY = "incorrectInput";
        public static final String INCORRECT_INPUT_MSG = "Input Is ot Correct";
        public static final String PERSON_NOT_MATCH_KEY = "personNotMatch";
        public static final String PERSON_NOT_MATCH_MSG = "The Person You Entered is Not The Account Owner";
        public static final String DUPLICATE_CARD_KEY = "duplicateCard";
        public static final String DUPLICATE_CARD_MSG = "The Customer Has An Active Card With Same Type";
        public static final String NOT_FOUND_KEY = "notFound";
        public static final String NOT_FOUND_MSG = "Card Not Found";
        public static final String CARD_TYPE_NOT_FOUND_KEY = "cardTypeNotFound";
        public static final String CARD_TYPE_NOT_FOUND_MSG = "The Entered Card Type is Not Found";
        public static final String ISSUER_CODE_NOT_FOUND_KEY = "codeIssuerNotFound";
        public static final String ISSUER_CODE_NOT_FOUND_MSG = "The Entered Code Issuer is Not Found";
        public static final String NOT_ACTIVE_KEY = "notActive";
        public static final String NOT_ACTIVE_MSG = "The Card Is Not Active";
        public static final String EXPIRED_KEY = "cardExpired";
        public static final String EXPIRED_MSG = "The Card Is Expired";
    }
}