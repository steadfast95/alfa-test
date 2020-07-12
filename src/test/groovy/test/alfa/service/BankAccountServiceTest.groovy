package test.alfa.service

import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification
import test.alfa.TestApp
import test.alfa.model.db.BankAccount
import test.alfa.repository.BankAccountRepository

/**
 * @author a.trofimov 12.07.2020
 */
@SpringBootTest(classes = TestApp)
@AutoConfigureMockMvc
@DirtiesContext
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
class BankAccountServiceTest extends Specification {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository repository;

    def cleanup() {
        repository.deleteAll();
    }

    def "create test"() {
        given:
        def view = bankAccountService.createAccount();

        def all = repository.findAll()
        expect:
        all.size() == 1
        all.get(0).getAccountNumber() == view.getAccountNumber()

    }

    def "get list bank accounts test ALL"() {
        given:
        def bankAccount = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = true
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }
        def bankAccount2 = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = true
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }
        def bankAccount3 = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = true
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }

        when:
        "сохранение"(bankAccount, bankAccount2, bankAccount3)

        then:
        bankAccountService.getAccountsList("all").size() == 3

    }

    def "get list bank accounts test CLOSED"() {
        given:
        def bankAccount = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = true
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }
        def bankAccount2 = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = false
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }
        def bankAccount3 = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = false
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }

        when:
        "сохранение"(bankAccount, bankAccount2, bankAccount3)

        then:
        bankAccountService.getAccountsList("closed").size() == 2

    }

    def "get list bank accounts test ACTIVE"() {
        given:
        def bankAccount = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = true
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }
        def bankAccount2 = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = true
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }
        def bankAccount3 = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = false
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }

        when:
        "сохранение"(bankAccount, bankAccount2, bankAccount3)

        then:
        bankAccountService.getAccountsList("active").size() == 2

    }

    def "close test"() {
        given:
        def bankAccount = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = true
            balance = RandomStringUtils.randomNumeric(1)
            currency = RandomStringUtils.randomAlphabetic(1)
        }

        when:
        "сохранение"(bankAccount)
        bankAccountService.closeAccount(bankAccount.getAccountNumber());

        then:
        def all = repository.findAll()
        all.size() == 1
        !all.get(0).isActive()
    }

    def "credit test"() {
        given:
        def initbalance = new BigDecimal("500");
        def credit = new BigDecimal("200");
        def expectedBalance = initbalance.subtract(credit);

        def bankAccount = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = true
            balance = initbalance
            currency = RandomStringUtils.randomAlphabetic(1)
        }
        when:
        "сохранение"(bankAccount)
        def result = bankAccountService.creditOperation(bankAccount.getAccountNumber(), credit)

        then:
        new BigDecimal(result.getBalance()).compareTo(expectedBalance) == 0

    }

    def "debit test"() {
        given:
        def initbalance = new BigDecimal("500");
        def debit = new BigDecimal("200");
        def expectedBalance = initbalance.add(debit);

        def bankAccount = new BankAccount().tap {
            accountNumber = RandomStringUtils.randomAlphabetic(1)
            active = true
            balance = initbalance
            currency = RandomStringUtils.randomAlphabetic(1)
        }
        when:
        "сохранение"(bankAccount)
        def result = bankAccountService.debitOperation(bankAccount.getAccountNumber(), debit)

        then:
        new BigDecimal(result.getBalance()).compareTo(expectedBalance) == 0
    }

    def "сохранение"(BankAccount... bankAccounts) {
        repository.saveAll(List.of(bankAccounts));
    }

}
