package test.alfa.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.alfa.config.BankAccountConfig;
import test.alfa.converter.BankAccountToAccountViewConverter;
import test.alfa.exceptions.BankAccountAlreadyClosedException;
import test.alfa.exceptions.BankAccountNotFoundException;
import test.alfa.model.db.BankAccount;
import test.alfa.model.view.AccountView;
import test.alfa.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author a.trofimov 12.07.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService {

    private static final String ALL_ACCOUNTS = "all";
    private static final String ACTIVE_ACCOUNTS = "active";
    private static final String CLOSED_ACCOUNTS = "closed";

    @Autowired(required = false)
    private BankAccountConfig config;

    private final BankAccountRepository repository;

    private final BankAccountToAccountViewConverter converter;


    public AccountView createAccount() {
        final var bankAccountBuilder = BankAccount
                .builder()
                .accountNumber(RandomStringUtils.randomNumeric(10));

        Optional.ofNullable(config)
                .ifPresentOrElse(
                        config -> bankAccountBuilder
                                .active(config.isActive())
                                .balance(config.getBalance())
                                .currency(config.getCurrency()),
                        () -> bankAccountBuilder
                                .active(true)
                                .balance(new BigDecimal(RandomStringUtils.randomNumeric(5)))
                                .currency(RandomStringUtils.random(3)));

        return saveAndReturn(bankAccountBuilder.build());
    }

    public AccountView closeAccount(final String accountNumber) {
        var bankAccount = repository.findByAccountNumber(accountNumber).orElseThrow(BankAccountNotFoundException::new);

        if (bankAccount.isActive()) {
            bankAccount.setActive(false);
        } else {
            throw new BankAccountAlreadyClosedException();
        }

        return saveAndReturn(bankAccount);

    }

    public Collection<AccountView> getAccountsList(String condition) {
        return getByCondition(condition).stream().map(converter::convert).collect(Collectors.toSet());
    }

    public AccountView creditOperation(String accountNumber, BigDecimal amount) {
        var bankAccount = repository.findByAccountNumber(accountNumber).orElseThrow(BankAccountNotFoundException::new);
        bankAccount.setBalance(bankAccount.getBalance().subtract(amount));
        return saveAndReturn(bankAccount);
    }

    public AccountView debitOperation(String accountNumber, BigDecimal amount) {
        var bankAccount = repository.findByAccountNumber(accountNumber).orElseThrow(BankAccountNotFoundException::new);
        bankAccount.setBalance(bankAccount.getBalance().add(amount));
        return saveAndReturn(bankAccount);
    }

    private AccountView saveAndReturn(BankAccount bankAccount) {
        return converter.convert(repository.save(bankAccount));
    }

    private Collection<BankAccount> getByCondition(String condition) {
        switch (condition) {
            case ALL_ACCOUNTS:
                return repository.findAll();
            case ACTIVE_ACCOUNTS:
                return repository.findAllByActive(true);
            case CLOSED_ACCOUNTS:
                return repository.findAllByActive(false);
            default:
                throw new RuntimeException("Unexpected condition while getting all bank accounts");
        }
    }

}
