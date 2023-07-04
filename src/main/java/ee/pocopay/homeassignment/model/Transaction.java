package ee.pocopay.homeassignment.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Transaction {
    private UUID id;
    private UUID senderAccountId;
    private UUID recipientAccountId;
    private BigDecimal amount;
    private String description;
}
