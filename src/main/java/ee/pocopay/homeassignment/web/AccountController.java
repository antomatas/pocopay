package ee.pocopay.homeassignment.web;

import ee.pocopay.homeassignment.domain.usecase.TransferFundsUseCase;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("accounts")
@RequiredArgsConstructor
public class AccountController {

    private final TransferFundsUseCase transferFundsUseCase;

    @PostMapping("{senderAccountId}")
    public void transfer(@PathVariable @NotBlank String senderAccountId, @RequestBody @Validated TransferDto transferDto) {
        var request = new TransferFundsUseCase.Request(senderAccountId,
                transferDto.amount(),
                transferDto.recipientAccount(),
                transferDto.description());
        transferFundsUseCase.execute(request);
    }
}
