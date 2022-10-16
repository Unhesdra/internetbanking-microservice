package com.internetbanking.account.integration;

import com.internetbanking.account.controller.AccountController;
import com.internetbanking.account.dto.account.AccountDto;
import com.internetbanking.account.dto.account.DebitDto;
import com.internetbanking.account.entity.Account;
import com.internetbanking.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerIntegrationTest {

    @MockBean
    private AccountService accountService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void debitTest() throws Exception {
        Account account = new Account(BigDecimal.TEN, 100, 1, 1);
        AccountDto testAccountDto = new AccountDto(account);

        when(accountService.debit(any(DebitDto.class))).thenReturn(testAccountDto);

        mockMvc.perform(put("/account/debit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\": \"1\", \"clientId\": \"1\", \"debitValue\": \"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("10"))
                .andExpect(jsonPath("$.accountNumber").value("100"))
                .andExpect(jsonPath("$.branch").value("1"));
    }

}
