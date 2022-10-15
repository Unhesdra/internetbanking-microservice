package com.internetbanking.account.repository;

import com.internetbanking.account.entity.BankClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<BankClient, Long> {

    Optional<BankClient> findByDocumentId(String documentId);

}
