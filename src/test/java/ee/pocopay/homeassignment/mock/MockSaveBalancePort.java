package ee.pocopay.homeassignment.mock;

import ee.pocopay.homeassignment.domain.port.SaveBalancePort;
import ee.pocopay.homeassignment.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockSaveBalancePort implements SaveBalancePort {

    private final List<Account> accounts;

    public MockSaveBalancePort() {
        this(new ArrayList<>());
    }

    public MockSaveBalancePort(List<Account> initAccounts) {
        this.accounts = initAccounts;
    }

    @Override
    public void updateBalance(UUID accountId, BigDecimal newBalance) {
        var account = accounts.stream().filter(e -> e.getId().equals(accountId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Retrieving account for test that doesn't exist"));
        account.setBalance(newBalance);
    }
}
