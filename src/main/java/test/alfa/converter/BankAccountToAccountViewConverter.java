package test.alfa.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import test.alfa.model.db.BankAccount;
import test.alfa.model.view.AccountView;

/**
 * @author a.trofimov 12.07.2020
 */
@Component
public class BankAccountToAccountViewConverter implements Converter<BankAccount, AccountView> {
    @Override
    public AccountView convert(BankAccount source) {

        return AccountView.builder()
                .accountNumber(source.getAccountNumber())
                .currency(source.getCurrency())
                .balance(source.getBalance().setScale(2).toString())
                .active(source.isActive())
                .build();

    }
}
