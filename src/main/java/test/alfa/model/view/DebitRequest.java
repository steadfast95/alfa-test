package test.alfa.model.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * запрос на пополнение
 *
 * @author a.trofimov 12.07.2020
 */
@Data
@Accessors(chain = true)
public class DebitRequest {

    private String accountNumber;

    private BigDecimal debit;
}
