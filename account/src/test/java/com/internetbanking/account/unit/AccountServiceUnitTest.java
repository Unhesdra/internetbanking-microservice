package com.internetbanking.account.unit;

import com.internetbanking.account.dto.account.AccountDto;
import com.internetbanking.account.dto.account.DebitDto;
import com.internetbanking.account.entity.Account;
import com.internetbanking.account.entity.BankClient;
import com.internetbanking.account.exception.InsufficientFundsException;
import com.internetbanking.account.repository.AccountRepository;
import com.internetbanking.account.repository.ClientRepository;
import com.internetbanking.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceUnitTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private AccountService accountService;

    void debitWhenNoAccountCanBeFoundWithGivenAccountId() {

    }

    void debitWhenNoBankClientCanBeFoundWithGivenClientId() {

    }

    @Test
    void debitWhenAccountDoesNotBelongToGivenClient() {
        Account account1 = new Account(new BigDecimal("100"), 100, 1, 1);
        Account account2 = new Account(new BigDecimal("100"), 101, 0, 1);
        BankClient client = new BankClient(1L, "Oexdra", "Massella", "555.555.555-55");
        client.addAccount(account1);
        DebitDto debitDto = new DebitDto(2L, 1L, BigDecimal.TEN);

        when(accountRepository.findById(2L)).thenReturn(Optional.of(account2));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        assertThrows(RuntimeException.class, () -> accountService.debit(debitDto));
    }

    @Test
    void debitWhenInsufficientFundsInAccount() {
        Account account = new Account(new BigDecimal("5"), 100, 1, 1);
        BankClient client = new BankClient(1L, "Oexdra", "Massella", "555.555.555-55");
        client.addAccount(account);
        DebitDto debitDto = new DebitDto(1L, 1L, BigDecimal.TEN);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        assertThrows(InsufficientFundsException.class, () -> accountService.debit(debitDto));
    }

    @Test
    void debitWhenOkTest() {
        Account account = new Account(new BigDecimal("100"), 100, 1, 1);
        BankClient client = new BankClient(1L, "Oexdra", "Massella", "555.555.555-55");
        client.addAccount(account);
        DebitDto debitDto = new DebitDto(1L, 1L, BigDecimal.TEN);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        AccountDto accountDto = accountService.debit(debitDto);

        assertThat(accountDto.getAccountNumber()).isEqualTo(100);
        assertThat(accountDto.getBranch()).isEqualTo(1);
        assertThat(accountDto.getBalance()).isEqualTo(new BigDecimal("90"));
    }

}
