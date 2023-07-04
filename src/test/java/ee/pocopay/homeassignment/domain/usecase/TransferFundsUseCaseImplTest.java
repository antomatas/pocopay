package ee.pocopay.homeassignment.domain.usecase;

import ee.pocopay.homeassignment.domain.BusinessLogicException;
import ee.pocopay.homeassignment.mock.MockFetchAccountPort;
import ee.pocopay.homeassignment.mock.MockSaveBalancePort;
import ee.pocopay.homeassignment.mock.MockSaveTransactionPort;
import ee.pocopay.homeassignment.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static ee.pocopay.homeassignment.domain.usecase.TransferFundsUseCase.TransferFundsViolation.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferFundsUseCaseImplTest {
    private String senderAccountNumber;
    private BigDecimal senderAccountBalance;
    private Account senderAccount;
    private String recipientAccountNumber;
    private BigDecimal recipientAccountBalance;
    private Account recipientAccount;

    private static final String DESCRIPTION = "The reason is...";
    private static final BigDecimal AMOUNT_TO_TRANSFER = BigDecimal.ONE;

    private MockFetchAccountPort mockFetchAccountPort;
    private MockSaveBalancePort mockSaveBalancePort;
    private MockSaveTransactionPort mockSaveTransactionPort;
    private TransferFundsUseCase transferFundsUseCase;

    @BeforeEach
    void setup() {
        setupMockRepository();
        transferFundsUseCase = new TransferFundsUseCaseImpl(mockFetchAccountPort, mockSaveBalancePort, mockSaveTransactionPort);
    }

    private void setupMockRepository() {
        senderAccountNumber = "senderAccountNr";
        senderAccountBalance = BigDecimal.TEN;
        senderAccount = new Account(UUID.randomUUID(), senderAccountNumber, senderAccountBalance);
        recipientAccountNumber = "recipientAccountNr";
        recipientAccountBalance = BigDecimal.TEN;
        recipientAccount = new Account(UUID.randomUUID(), recipientAccountNumber, recipientAccountBalance);
        var initAccounts = List.of(senderAccount, recipientAccount);
        mockFetchAccountPort = new MockFetchAccountPort(initAccounts);
        mockSaveBalancePort = new MockSaveBalancePort(initAccounts);
        mockSaveTransactionPort = new MockSaveTransactionPort();
    }

    @Nested
    class validates_request_and_throws_exception {
        @Test
        void when_unknown_sender_account() {
            var request = new TransferFundsUseCase.Request("x", BigDecimal.ONE, recipientAccountNumber, DESCRIPTION);

            var result = assertThrows(BusinessLogicException.class,
                    () -> transferFundsUseCase.execute(request));

            assertThat(result.getMessage()).as("error message").isEqualTo(UNKNOWN_SENDER_ACCOUNT.getErrorCode());
        }

        @Test
        void when_unknown_recipient_account() {
            var request = new TransferFundsUseCase.Request(senderAccountNumber, BigDecimal.ONE, "x", DESCRIPTION);

            var result = assertThrows(BusinessLogicException.class,
                    () -> transferFundsUseCase.execute(request));

            assertThat(result.getMessage()).as("error message").isEqualTo(UNKNOWN_RECIPIENT_ACCOUNT.getErrorCode());
        }

        @Test
        void when_sender_balance_too_small() {
            var request = new TransferFundsUseCase.Request(
                    senderAccountNumber,
                    senderAccountBalance.add(BigDecimal.ONE),
                    recipientAccountNumber,
                    DESCRIPTION);

            var result = assertThrows(BusinessLogicException.class,
                    () -> transferFundsUseCase.execute(request));

            assertThat(result.getMessage()).as("error message").isEqualTo(NOT_ENOUGH_FUNDS.getErrorCode());
        }

        @Test
        void when_sender_and_receiver_are_same() {
            var request = new TransferFundsUseCase.Request(
                    senderAccountNumber,
                    AMOUNT_TO_TRANSFER,
                    senderAccountNumber,
                    DESCRIPTION);

            var result = assertThrows(BusinessLogicException.class,
                    () -> transferFundsUseCase.execute(request));

            assertThat(result.getMessage()).as("error message").isEqualTo(SENDER_AND_RECIPIENT_ARE_SAME.getErrorCode());
        }
    }

    @Nested
    class transfers_funds {
        TransferFundsUseCase.Request workingRequest;

        @BeforeEach
        void defineRequest() {
            workingRequest = new TransferFundsUseCase.Request(
                    senderAccountNumber,
                    AMOUNT_TO_TRANSFER,
                    recipientAccountNumber,
                    DESCRIPTION);
        }

        @Test
        void and_updates_sender_balance_correctly() {
            var expectedSenderBalance = senderAccountBalance.subtract(AMOUNT_TO_TRANSFER);

            transferFundsUseCase.execute(workingRequest);

            var senderAccount = mockFetchAccountPort.fetchBy(workingRequest.senderAccount())
                    .orElseThrow(() -> new BusinessLogicException(UNKNOWN_SENDER_ACCOUNT.getErrorCode()));
            assertThat(senderAccount.getBalance()).as("sender account remaining balance").isEqualTo(expectedSenderBalance);
        }

        @Test
        void and_updates_recipient_balance_correctly() {
            var expectedRecipientBalance = recipientAccountBalance.add(AMOUNT_TO_TRANSFER);

            transferFundsUseCase.execute(workingRequest);

            var recipientAccount = mockFetchAccountPort.fetchBy(workingRequest.recipientAccount())
                    .orElseThrow(() -> new BusinessLogicException(UNKNOWN_RECIPIENT_ACCOUNT.getErrorCode()));
            assertThat(recipientAccount.getBalance()).as("recipient account remaining balance").isEqualTo(expectedRecipientBalance);
        }

        @Test
        void and_saves_new_transaction() {
            transferFundsUseCase.execute(workingRequest);

            assertThat(mockSaveTransactionPort.getTransactions().size()).as("transactions amount").isEqualTo(1);
            assertThat(mockSaveTransactionPort.getTransactions().get(0)).satisfies(transaction -> {
                assertAll(
                        () -> assertThat(transaction.getSenderAccountId()).as("sender account id").isEqualTo(senderAccount.getId()),
                        () -> assertThat(transaction.getRecipientAccountId()).as("recipient account id").isEqualTo(recipientAccount.getId()),
                        () -> assertThat(transaction.getAmount()).as("amount").isEqualTo(AMOUNT_TO_TRANSFER),
                        () -> assertThat(transaction.getDescription()).as("description").isEqualTo(DESCRIPTION)
                );
            });
        }
    }

}
