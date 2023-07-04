package ee.pocopay.homeassignment.domain.port;

import ee.pocopay.homeassignment.model.Account;

import java.util.Optional;

public interface FetchAccountPort {

    Optional<Account> fetchBy(String accountNumber);
}
