package com.internetbanking.account.repository;

import com.internetbanking.account.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>  {
}
