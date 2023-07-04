package ee.pocopay.homeassignment.jdbc.gateway;

import ee.pocopay.homeassignment.domain.port.SaveTransactionPort;
import ee.pocopay.homeassignment.model.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
interface SaveTransactionGateway extends SaveTransactionPort {
    @Insert("""
            INSERT INTO pocopay."transaction"(
                recipient_account_id,
                sender_account_id,
                amount,
                description)
            VALUES(
                #{senderAccountId},
                #{recipientAccountId},
                #{amount},
                #{description});
            """)
    void saveTransaction(Transaction transaction);
}
