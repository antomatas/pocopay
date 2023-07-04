package ee.pocopay.homeassignment.jdbc.gateway;

import ee.pocopay.homeassignment.domain.port.SaveBalancePort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper
@Repository
interface SaveBalanceGateway extends SaveBalancePort {

    @Update("""
            UPDATE balance
            SET balance = #{newBalance}
            WHERE account_id = #{accountId}
            """)
    void updateBalance(UUID accountId, BigDecimal newBalance);
}
