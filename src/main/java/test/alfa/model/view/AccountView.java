package test.alfa.model.view;

import lombok.Builder;
import lombok.Getter;

/**
 * Отражение аккаунта
 *
 * @author a.trofimov 12.07.2020
 */
@Getter
@Builder
public class AccountView {

    private final String accountNumber;

    private final String balance;

    private final String currency;

    private final boolean active;
}
