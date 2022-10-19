package com.internetbanking.account.repository;

import com.internetbanking.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a " +
            "FROM Account a " +
            "WHERE a.accountNumber = :accountNumber " +
            "AND a.branch = :branch")
    Optional<Account> findByAccountNumberAndBranch(
            @Param("accountNumber") Integer accountNumber,
            @Param("branch") Integer branch);

    @Query("SELECT MAX(a.accountNumber) " +
            "FROM Account a " +
            "WHERE a.branch = :branch")
    Optional<Integer> findLastAccountNumberByBranch(@Param("branch") Integer branch);

}
