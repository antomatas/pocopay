package ee.pocopay.homeassignment.domain.usecase;

import ee.pocopay.homeassignment.domain.ViolationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

public interface TransferFundsUseCase {

    /*
    TODO! For some reason this validation does not work.
     Currently the validation has been added to the controller and TransferDto to ensure functionality, but it is not
     ideal since the architectural pattern dictates use-case should not rely on web port.
     */
    void execute(@Valid Request request);

    record Request(@NotBlank String senderAccount,
                   @NotNull BigDecimal amount,
                   @NotBlank String recipientAccount,
                   @NotBlank @Size(max = 300) String description) {
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
