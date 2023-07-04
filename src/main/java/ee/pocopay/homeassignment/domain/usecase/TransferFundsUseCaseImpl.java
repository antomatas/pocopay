package ee.pocopay.homeassignment.domain.usecase;

import ee.pocopay.homeassignment.domain.BusinessLogicException;
import ee.pocopay.homeassignment.domain.port.FetchAccountPort;
import ee.pocopay.homeassignment.domain.port.SaveBalancePort;
import ee.pocopay.homeassignment.domain.port.SaveTransactionPort;
import ee.pocopay.homeassignment.model.Account;
import ee.pocopay.homeassignment.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ee.pocopay.homeassignment.domain.usecase.TransferFundsUseCase.TransferFundsViolation.*;

@Service
@RequiredArgsConstructor
class TransferFundsUseCaseImpl implements TransferFundsUseCase {

    private final FetchAccountPort fetchAccountPort;
    private final SaveBalancePort saveBalancePort;
    private final SaveTransactionPort saveTransactionPort;

    @Override
    @Transactional
    public void execute(Request request) {
        var senderAccount = fetchAccountOrThrow(request.senderAccount(), UNKNOWN_SENDER_ACCOUNT);
        var recipientAccount = fetchAccountOrThrow(request.recipientAccount(), UNKNOWN_RECIPIENT_ACCOUNT);
        validateRequest(request, senderAccount, recipientAccount);

        performTransaction(request, senderAccount, recipientAccount);
    }

    private void performTransaction(Request request, Account senderAccount, Account recipientAccount) {
        updateBalances(request, senderAccount, recipientAccount);
        storeTransaction(request, senderAccount, recipientAccount);
    }

    private void storeTransaction(Request request, Account senderAccount, Account recipientAccount) {
        var newTransaction = Transaction.builder()
                .senderAccountId(senderAccount.getId())
                .recipientAccountId(recipientAccount.getId())
                .amount(request.amount())
                .description(request.description())
                .build();
        saveTransactionPort.saveTransaction(newTransaction);
    }

    private void updateBalances(Request request, Account senderAccount, Account recipientAccount) {
        var newSenderBalance = senderAccount.getBalance().subtract(request.amount());
        saveBalancePort.updateBalance(senderAccount.getId(), newSenderBalance);

        var newRecipientBalance = recipientAccount.getBalance().add(request.amount());
        saveBalancePort.updateBalance(recipientAccount.getId(), newRecipientBalance);
    }

    private void validateRequest(Request request, Account senderAccount, Account recipientAccount) {
        if (request.amount().compareTo(senderAccount.getBalance()) > 0) {
            throw new BusinessLogicException(NOT_ENOUGH_FUNDS.getErrorCode());
        }
        if (senderAccount.getId().equals(recipientAccount.getId())) {
            throw new BusinessLogicException(SENDER_AND_RECIPIENT_ARE_SAME.getErrorCode());
        }
    }

    private Account fetchAccountOrThrow(String accountNumber, TransferFundsViolation accountNotFoundViolation) {
        return fetchAccountPort.fetchBy(accountNumber)
                .orElseThrow(() -> new BusinessLogicException(accountNotFoundViolation.getErrorCode()));
    }
}

