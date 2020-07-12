package test.alfa.model.view;

import lombok.Data;

/**
 * Запрос на закрытие счета
 *
 * @author a.trofimov 12.07.2020
 */
@Data
public class CloseRequest {

    private String accountNumber;
}
