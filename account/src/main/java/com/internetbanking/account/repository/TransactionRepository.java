package com.internetbanking.account.repository;

import com.internetbanking.account.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long>  {
    @Query("SELECT t FROM Transaction t " +
            "LEFT OUTER JOIN Account a ON t.account = a.accountId " +
            "WHERE a.accountId = :accountId")
    Page<Transaction> findAllTransactionsById(@Param("accountId") Long accountId, Pageable pageable);
}
