package test.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.alfa.model.db.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author a.trofimov 12.07.2020
 */

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByAccountNumber(String accountNumber);

//    @Query(value = "select * from bank_account_tab ba where ba.active = ':condition'",nativeQuery = true)
//    Set<BankAccount> getAllWithCondition(@Param("condition") final Boolean condition);

    List<BankAccount> findAllByActive(Boolean active);

}
