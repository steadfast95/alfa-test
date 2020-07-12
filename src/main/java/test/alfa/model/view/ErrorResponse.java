package test.alfa.model.view;

import lombok.Builder;
import lombok.Data;

/**
 * @author a.trofimov 12.07.2020
 */
@Data
@Builder
public class ErrorResponse {

    private int code;

    private String message;
}
