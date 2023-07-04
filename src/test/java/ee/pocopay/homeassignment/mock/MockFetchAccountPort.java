package ee.pocopay.homeassignment.mock;

import ee.pocopay.homeassignment.domain.port.FetchAccountPort;
import ee.pocopay.homeassignment.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockFetchAccountPort implements FetchAccountPort {

    private final List<Account> accounts;

    public MockFetchAccountPort() {
        this(new ArrayList<>());
    }

    public MockFetchAccountPort(List<Account> initAccounts) {
        this.accounts = initAccounts;
    }

    @Override
    public Optional<Account> fetchBy(String accountNumber) {
        return accounts.stream().filter(e -> e.getAccountNumber().equals(accountNumber)).findFirst();
    }
}
