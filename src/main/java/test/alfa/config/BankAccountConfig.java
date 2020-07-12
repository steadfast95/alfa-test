package test.alfa.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author a.trofimov 12.07.2020
 */
@Data
@Configuration
@Validated
@ConfigurationProperties(prefix = "custom.default-values")
@ConditionalOnProperty(value = "custom.enable-default-values", havingValue = "true")
public class BankAccountConfig {

    @NotNull
    private boolean forceUpdate;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private String currency;

    @NotNull
    private boolean active;
}
