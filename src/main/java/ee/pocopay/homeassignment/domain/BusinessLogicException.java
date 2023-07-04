package ee.pocopay.homeassignment.domain;

public class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String code) {
        super(code);
    }
}
