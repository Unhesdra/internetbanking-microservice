package com.internetbanking.account.service;

import com.internetbanking.account.dto.account.AccountDto;
import com.internetbanking.account.dto.account.DebitDto;
import com.internetbanking.account.dto.account.DepositDto;
import com.internetbanking.account.dto.account.TransferDto;
import com.internetbanking.account.entity.Account;
import com.internetbanking.account.entity.BankClient;
import com.internetbanking.account.entity.Transaction;
import com.internetbanking.account.exception.AccountDoesNotBelongToClientException;
import com.internetbanking.account.exception.BankAccountNotFoundException;
import com.internetbanking.account.exception.ClientNotFoundException;
import com.internetbanking.account.exception.InsufficientFundsException;
import com.internetbanking.account.repository.AccountRepository;
import com.internetbanking.account.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

import static com.internetbanking.account.entity.TransactionType.DEBIT;
import static com.internetbanking.account.entity.TransactionType.DEPOSIT;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final TransactionService transactionService;

    @Autowired
    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public AccountDto debit(DebitDto debitDto) {
        Account account = checkIfAccountExists(debitDto.getAccountId());
        BankClient client = checkIfClientExists(debitDto.getClientId());

        checkIfAccountBelongsToClient(account, client);

        if(!account.debit(debitDto.getDebitValue())) {
            throw new InsufficientFundsException("Value cannot be debited! Insufficient funds!");
        }

        Transaction transaction = transactionService.registerTransaction(debitDto.getDebitValue(), account, DEBIT);
        account.addTransaction(transaction);

        return new AccountDto(account);
    }

    @Transactional
    public AccountDto deposit(DepositDto depositDto) {
        Account account = checkIfAccountExists(depositDto.getAccountId());
        BankClient client = checkIfClientExists(depositDto.getDocumentId());

        checkIfAccountBelongsToClient(account, client);

        account.deposit(depositDto.getDepositValue());

        Transaction transaction = transactionService.registerTransaction(depositDto.getDepositValue(), account, DEPOSIT);
        account.addTransaction(transaction);

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
            throw new InsufficientFundsException("Value cannot be debited! Insufficient funds!");
        }

        receivingAccount.deposit(transferDto.getTransferValue());

        Transaction debitTransaction = transactionService.registerTransaction(transferDto.getTransferValue(), debitAccount, DEBIT);
        Transaction depositTransaction = transactionService.registerTransaction(transferDto.getTransferValue(), receivingAccount, DEPOSIT);

        debitTransaction.setLinkedTransaction(depositTransaction);
        depositTransaction.setLinkedTransaction(debitTransaction);

        debitAccount.addTransaction(debitTransaction);
        receivingAccount.addTransaction(depositTransaction);

        return new AccountDto(receivingAccount);
    }

    public Account createAccount(Integer branch) {
        Integer lastAccountNumber;
        Optional<Integer> optionalLastAccountNumber = accountRepository.findLastAccountNumberByBranch(branch);
        if(optionalLastAccountNumber.isEmpty()) {
            lastAccountNumber = 0;
        } else {
            lastAccountNumber = optionalLastAccountNumber.get();
        }

        Integer checkDigit = generateCheckDigit(lastAccountNumber + 1);
        Account account = new Account(BigDecimal.ZERO, lastAccountNumber + 1, checkDigit, branch);
        accountRepository.save(account);

        return account;
    }

    private Account checkIfAccountExists(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isEmpty()) {
            throw new BankAccountNotFoundException("Account cannot be found in database!");
        }

        return optionalAccount.get();
    }

    private Account checkIfAccountExists(Integer accountNumber, Integer branch) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumberAndBranch(accountNumber, branch);
        if(optionalAccount.isEmpty()) {
            throw new BankAccountNotFoundException("Account cannot be found in database!");
        }

        return optionalAccount.get();
    }

    private BankClient checkIfClientExists(Long clientId) {
        Optional<BankClient> optionalClient = clientRepository.findById(clientId);
        if(optionalClient.isEmpty()) {
            throw new ClientNotFoundException("Client cannot be found in database!");
        }

        return optionalClient.get();
    }

    private BankClient checkIfClientExists(String documentId) {
        Optional<BankClient> optionalClient = clientRepository.findByDocumentId(documentId);
        if(optionalClient.isEmpty()) {
            throw new ClientNotFoundException("Client cannot be found in database!");
        }

        return optionalClient.get();
    }

    private void checkIfAccountBelongsToClient(Account account, BankClient client) {
        Optional<Account> optionalClientAccount = client
                .getAccounts()
                .stream()
                .filter(a -> a.getAccountId() == account.getAccountId())
                .findFirst();


        if(optionalClientAccount.isEmpty()) {
            throw new AccountDoesNotBelongToClientException(("Account does not belong to specified user!"));
        }
    }

    private Integer generateCheckDigit(Integer accountNumber) {
        int oddPositionSum = 0;
        int evenPositionSum = 0;
        int checkDigit = 0;

        for (int i = 1; i < 8; i++) {
            if (i % 2 == 1) {
                int oddPositionNumber = accountNumber % (int) (Math.pow(10, i));
                accountNumber = accountNumber - oddPositionNumber;
                oddPositionNumber = oddPositionNumber / (int) (Math.pow(10, i - 1));
                oddPositionSum = oddPositionSum + oddPositionNumber;
            }
            else {
                int evenPositionNumber = accountNumber % (int) (Math.pow(10, i));
                accountNumber = accountNumber - evenPositionNumber;
                evenPositionNumber = evenPositionNumber / (int) (Math.pow(10, i - 1));
                evenPositionSum = evenPositionSum + evenPositionNumber;
            }
        }

        oddPositionSum = oddPositionSum * 3;

        checkDigit = oddPositionSum + evenPositionSum;
        checkDigit = checkDigit % 10;

        if (checkDigit == 0) {
            return 0;
        }

        return 10 - checkDigit;
    }
}
