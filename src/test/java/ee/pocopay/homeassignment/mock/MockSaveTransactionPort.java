package ee.pocopay.homeassignment.mock;

import ee.pocopay.homeassignment.domain.port.SaveTransactionPort;
import ee.pocopay.homeassignment.model.Transaction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MockSaveTransactionPort implements SaveTransactionPort {

    @Getter
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
