package ee.pocopay.homeassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private UUID id;
    private String accountNumber;
    private BigDecimal balance;
}
