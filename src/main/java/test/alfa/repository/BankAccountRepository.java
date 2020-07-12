package test.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.alfa.model.db.BankAccount;

import java.util.List;
import java.util.Optional;

/**
 * @author a.trofimov 12.07.2020
 */

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    List<BankAccount> findAllByActive(Boolean active);

}
