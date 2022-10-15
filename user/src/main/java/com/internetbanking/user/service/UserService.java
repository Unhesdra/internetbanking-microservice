package com.internetbanking.user.service;

import com.internetbanking.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Transactional
    public ResponseEntity<AccountDto> debit(DebitDto debitDto) {

        RequestDebitDto requestDto = new RequestDebitDto(debitDto.getAccountId(), 1L, debitDto.getDebitValue());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RequestDebitDto> entity = new HttpEntity<>(requestDto, headers);
        ResponseEntity<AccountDto> accountDtoResponse = restTemplate.exchange(
                "http://localhost:9001/account/debit", HttpMethod.PUT, entity, AccountDto.class);

        return accountDtoResponse;
    }

    @Transactional
    public ResponseEntity<AccountDto> transfer(TransferDto transferDto) {

        RequestTransferDto requestDto = new RequestTransferDto(
                transferDto.getDebitAccountId(),
                transferDto.getDepositAccountNumber(),
                transferDto.getDepositBranch(),
                transferDto.getDepositDocumentId(),
                transferDto.getTransferValue(), 2L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RequestTransferDto> entity = new HttpEntity<>(requestDto, headers);
        ResponseEntity<AccountDto> accountDtoResponse = restTemplate.exchange(
                "http://localhost:9001/account/debit", HttpMethod.PUT, entity, AccountDto.class);

        return accountDtoResponse;

    }
}
