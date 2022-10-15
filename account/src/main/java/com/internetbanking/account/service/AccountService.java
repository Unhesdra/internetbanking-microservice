package com.internetbanking.account.service;

import com.internetbanking.account.dto.account.AccountDto;
import com.internetbanking.account.dto.account.DepositDto;
import com.internetbanking.account.dto.account.DebitDto;
import com.internetbanking.account.dto.account.TransferDto;
import com.internetbanking.account.entity.Account;
import com.internetbanking.account.entity.BankClient;
import com.internetbanking.account.exception.InsuficientFundsException;
import com.internetbanking.account.repository.AccountRepository;
import com.internetbanking.account.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public AccountDto debit(DebitDto debitDto) {
        Account account = checkIfAccountExists(debitDto.getAccountId());
        BankClient client = checkIfClientExists(debitDto.getClientId());

        checkIfAccountBelongsToClient(account, client);

        if(!account.debit(debitDto.getDebitValue())) {
            throw new InsuficientFundsException("Value cannot be debited! Insufficient funds!");
        }
        return new AccountDto(account);
    }

    @Transactional
    public AccountDto deposit(DepositDto depositDto) {
        Account account = checkIfAccountExists(depositDto.getAccountId());
        BankClient client = checkIfClientExists(depositDto.getDocumentId());

        checkIfAccountBelongsToClient(account, client);

        account.deposit(depositDto.getDepositValue());

        return new AccountDto(account);
    }

    @Transactional
    public AccountDto transfer(TransferDto transferDto) {
        Account debitAccount = checkIfAccountExists(transferDto.getDebitAccountId());
        Account receivingAccount = checkIfAccountExists(transferDto.getReceivingAccountNumber(), transferDto.getReceivingBranch());

        BankClient debitClient = checkIfClientExists(transferDto.getDebitClientId());
        BankClient receivingClient = checkIfClientExists(transferDto.getReceivingDocumentId());

        checkIfAccountBelongsToClient(debitAccount, debitClient);
        checkIfAccountBelongsToClient(receivingAccount, receivingClient);

        if(!debitAccount.debit(transferDto.getTransferValue())) {
            throw new RuntimeException("Value cannot be debited! Insufficient funds!");
        }

        receivingAccount.deposit(transferDto.getTransferValue());
        return new AccountDto(receivingAccount);
    }

    private Account checkIfAccountExists(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isEmpty()) {
            throw new RuntimeException("Account cannot be found in database!");
        }

        return optionalAccount.get();
    }

    private Account checkIfAccountExists(Integer accountNumber, Integer branch) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumberAndBranch(accountNumber, branch);
        if(optionalAccount.isEmpty()) {
            throw new RuntimeException("Account cannot be found in database!");
        }

        return optionalAccount.get();
    }

    private BankClient checkIfClientExists(Long clientId) {
        Optional<BankClient> optionalClient = clientRepository.findById(clientId);
        if(optionalClient.isEmpty()) {
            throw new RuntimeException("Client cannot be found in database!");
        }

        return optionalClient.get();
    }

    private BankClient checkIfClientExists(String documentId) {
        Optional<BankClient> optionalClient = clientRepository.findByDocumentId(documentId);
        if(optionalClient.isEmpty()) {
            throw new RuntimeException("Client cannot be found in database!");
        }

        return optionalClient.get();
    }

    private void checkIfAccountBelongsToClient(Account account, BankClient client) {
        Optional<Account> optionalClientAccount = client
                .getAccounts()
                .stream()
                .findFirst()
                .filter(a -> a.getAccountId() == account.getAccountId());

        if(optionalClientAccount.isEmpty()) {
            throw new RuntimeException(("Account does not belong to specified user!"));
        }
    }
}
