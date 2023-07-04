package ee.pocopay.homeassignment.domain.port;

import ee.pocopay.homeassignment.model.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public interface SaveTransactionPort {

    void saveTransaction(Transaction transaction);
}
