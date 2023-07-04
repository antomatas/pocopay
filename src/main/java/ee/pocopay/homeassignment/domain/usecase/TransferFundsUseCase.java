package ee.pocopay.homeassignment.domain.usecase;

import ee.pocopay.homeassignment.domain.ViolationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

public interface TransferFundsUseCase {

    void execute(Request request);

    record Request(String senderAccount,
                   BigDecimal amount,
                   String recipientAccount,
                   String description) {
    }

    @Getter
    @RequiredArgsConstructor
    enum TransferFundsViolation implements ViolationType {
        NOT_ENOUGH_FUNDS("NOT_ENOUGH_FUNDS"),
        UNKNOWN_RECIPIENT_ACCOUNT("UNKNOWN_RECIPIENT_ACCOUNT"),
        UNKNOWN_SENDER_ACCOUNT("UNKNOWN_SENDER_ACCOUNT"),
        SENDER_AND_RECIPIENT_ARE_SAME("SENDER_AND_RECIPIENT_ARE_SAME");

        private final String errorCode;
    }
}
