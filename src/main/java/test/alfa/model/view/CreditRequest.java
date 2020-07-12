package test.alfa.model.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Запрос на списание
 *
 * @author a.trofimov 12.07.2020
 */
@Data
@Accessors(chain = true)
public class CreditRequest {

    private String accountNumber;

    private BigDecimal credit;
}
