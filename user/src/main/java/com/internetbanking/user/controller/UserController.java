package com.internetbanking.user.controller;

import com.internetbanking.user.dto.AccountDto;
import com.internetbanking.user.dto.DebitDto;
import com.internetbanking.user.dto.TransferDto;
import com.internetbanking.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/debit")
    public ResponseEntity<AccountDto> debit(@RequestBody @Valid DebitDto debitDto) {
        return userService.debit(debitDto);
    }

    @PutMapping("/transfer")
    public ResponseEntity<AccountDto> transfer(@RequestBody @Valid TransferDto transferDto) {
        return userService.transfer(transferDto);
    }

}
