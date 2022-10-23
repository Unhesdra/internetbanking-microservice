package com.internetbanking.account.service;

import com.internetbanking.account.entity.Account;
import com.internetbanking.account.entity.Transaction;
import com.internetbanking.account.entity.TransactionType;
import com.internetbanking.account.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction registerTransaction(BigDecimal value,
                                           Account account,
                                           TransactionType transactionType) {

        Transaction transaction = new Transaction(value, account, transactionType);
        transactionRepository.save(transaction);

        return transaction;
    }


}
