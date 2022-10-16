package com.internetbanking.account.service;

import com.internetbanking.account.dto.client.ClientDto;
import com.internetbanking.account.dto.client.CreateClientDto;
import com.internetbanking.account.dto.client.UpdateClientDto;
import com.internetbanking.account.entity.Account;
import com.internetbanking.account.entity.BankClient;
import com.internetbanking.account.repository.AccountRepository;
import com.internetbanking.account.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    public ClientDto registerClient(CreateClientDto clientDto) {
        Optional<BankClient> optionalClient = clientRepository.findByDocumentId(clientDto.getDocumentId());

        BankClient client;
        if(optionalClient.isEmpty()) {
            client = new BankClient(
                    clientDto.getClientId(),
                    clientDto.getFirstName(),
                    clientDto.getLastName(),
                    clientDto.getDocumentId()
            );
        } else {
            client = optionalClient.get();
        }

        Integer lastAccountNumber = accountRepository.findLastAccountNumberByBranch(clientDto.getBranch());
        if(lastAccountNumber == null) {
            lastAccountNumber = 0;
        }

        Integer checkDigit = generateCheckDigit(lastAccountNumber + 1);
        Account account = new Account(BigDecimal.ZERO, lastAccountNumber + 1, checkDigit, clientDto.getBranch());

        client.addAccount(account);
        accountRepository.save(account);
        clientRepository.save(client);

        return new ClientDto(client);
    }

    public List<ClientDto> listClients() {
        List<BankClient> clientList = clientRepository.findAll();
        List<ClientDto> clientDtoList = clientList
                .stream()
                .map(client -> new ClientDto(client))
                .collect(Collectors.toList());

        return clientDtoList;
    }

    public ClientDto listSpecificClient(Long clientId) {
        BankClient client = getClientFromDatabase(clientId);

        ClientDto clientDto = new ClientDto(client);
        return clientDto;
    }

    @Transactional
    public ClientDto updateClient(UpdateClientDto updateClientDto, Long clientId) {
        BankClient client = getClientFromDatabase(clientId);
        client.setDocumentId(updateClientDto.getDocumentId());

        return new ClientDto(client);
    }

    public ClientDto deleteClient(Long clientId) {
        BankClient client = getClientFromDatabase(clientId);
        ClientDto clientDto =  new ClientDto(client);

        clientRepository.delete(client);

        return clientDto;
    }

    private BankClient getClientFromDatabase(Long clientId) {
        Optional<BankClient> optionalClient = clientRepository.findById(clientId);
        if(optionalClient.isEmpty()) {
            throw new RuntimeException("Client cannot be found in database!");
        }

        return optionalClient.get();
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
