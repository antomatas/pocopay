package ee.pocopay.homeassignment.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

record TransferDto(@NotNull BigDecimal amount,
                   @NotBlank String recipientAccount,
                   @NotBlank @Size(max = 300) String description) {
}
