package test.alfa.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * @author a.trofimov 12.07.2020
 */
@Data
@Configuration
@Validated
@ConfigurationProperties(prefix = "custom.default-values")
@ConditionalOnProperty(value = "enable-default-values", havingValue = "true")
public class BankAccountConfig {

    @NotEmpty
    private boolean forceUpdate;

    @NotEmpty
    private BigDecimal balance;

    @NotEmpty
    private String currency;

    @NotEmpty
    private boolean active;
}
