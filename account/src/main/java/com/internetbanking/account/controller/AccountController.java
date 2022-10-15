package com.internetbanking.account.controller;

import com.internetbanking.account.dto.account.AccountDto;
import com.internetbanking.account.dto.account.DepositDto;
import com.internetbanking.account.dto.account.DebitDto;
import com.internetbanking.account.dto.account.TransferDto;
import com.internetbanking.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
