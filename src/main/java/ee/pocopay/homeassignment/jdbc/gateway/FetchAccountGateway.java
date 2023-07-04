package ee.pocopay.homeassignment.jdbc.gateway;

import ee.pocopay.homeassignment.domain.port.FetchAccountPort;
import ee.pocopay.homeassignment.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
interface FetchAccountGateway extends FetchAccountPort {

    @Select("""
            select
                a.id as id,
                a.account_number as account_number,
                b.balance as balance
            from account a
            join balance b
            on a.id = b.account_id
            where a.account_number = #{accountNumber}
            """)
    @Results(id = "AccountResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "accountNumber", column = "account_number"),
            @Result(property = "balance", column = "balance")
    })
    Optional<Account> fetchBy(String accountNumber);
}
