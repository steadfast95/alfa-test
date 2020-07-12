package test.alfa.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author a.trofimov 12.07.2020
 */
@Entity
@Table(name = "bank_account_tab")
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "accountNumber"})
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * Номер счета
     */
    @Column(name = "account_number", nullable = false)
    @Length(min = 10, max = 10, message = "applied length is only 10 numbers")
    private String accountNumber;

    @Column(name = "active", nullable = false)
    private boolean active;

    /**
     * Фактический баланс
     */
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    /**
     * Валюта счета
     */
    @Column(name = "currency", nullable = false)
    private String currency;

}

