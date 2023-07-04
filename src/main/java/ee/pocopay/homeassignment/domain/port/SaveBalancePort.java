package ee.pocopay.homeassignment.domain.port;

import java.math.BigDecimal;
import java.util.UUID;

public interface SaveBalancePort {

    void updateBalance(UUID accountId, BigDecimal newBalance);
}
