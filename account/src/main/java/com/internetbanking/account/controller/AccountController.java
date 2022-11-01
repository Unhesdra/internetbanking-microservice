package com.internetbanking.account.controller;

import com.internetbanking.account.dto.account.*;
import com.internetbanking.account.dto.transaction.TransactionDto;
import com.internetbanking.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/debit")
    public ResponseEntity<AccountDto> debit(@RequestBody @Valid DebitDto debitDto) {
        AccountDto accountDto = accountService.debit(debitDto);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestBody @Valid DepositDto depositDto) {
        AccountDto accountDto = accountService.deposit(depositDto);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/transfer")
    public ResponseEntity<AccountDto> transfer(@RequestBody @Valid TransferDto transferDto) {
        AccountDto accountDto = accountService.transfer(transferDto);
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping("/statement")
    public ResponseEntity<Page<TransactionDto>> statement(@RequestBody @Valid StatementDto statementDto,
                                                          @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<TransactionDto> transferDtoPage = accountService.getAccountTransaction(statementDto, pageable);
        return ResponseEntity.ok(transferDtoPage);
    }

}
