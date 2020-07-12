package test.alfa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.alfa.model.view.CloseRequest;
import test.alfa.model.view.CreditRequest;
import test.alfa.model.view.DebitRequest;
import test.alfa.model.view.Response;
import test.alfa.service.BankAccountService;

/**
 * @author a.trofimov 12.07.2020
 */
@RestController
@RequestMapping("alfa-test/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService service;

    @PutMapping(value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAccount() {
        return new Response(service.createAccount());
    }

    @PostMapping(value = "/close", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response closeAccount(@RequestBody final CloseRequest request) {
        return new Response(service.closeAccount(request.getAccountNumber()));
    }

    @PostMapping(value = "/credit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response credit(@RequestBody final CreditRequest request) {
        return new Response(service.creditOperation(request.getAccountNumber(), request.getCredit()));
    }

    @PostMapping(value = "/debit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response debit(@RequestBody final DebitRequest request) {
        return new Response(service.debitOperation(request.getAccountNumber(), request.getDebit()));
    }

    @GetMapping(value = "/{condition}")
    public Response getAll(@PathVariable("condition") final String condition) {
        return new Response(service.getAccountsList(condition));
    }

}
