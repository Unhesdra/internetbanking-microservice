package com.internetbanking.account.service;

import com.internetbanking.account.dto.client.ClientDto;
import com.internetbanking.account.dto.client.CreateClientDto;
import com.internetbanking.account.dto.client.UpdateClientDto;
import com.internetbanking.account.entity.Account;
import com.internetbanking.account.entity.BankClient;
import com.internetbanking.account.exception.ClientHasAccountAtBranchException;
import com.internetbanking.account.exception.ClientNotFoundException;
import com.internetbanking.account.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AccountService accountService;

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountService accountService) {
        this.clientRepository = clientRepository;
        this.accountService = accountService;
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

            Optional<Account> optionalAccountSameBranch = client
                    .getAccounts()
                    .stream()
                    .filter(a -> a.getBranch() == clientDto.getBranch())
                    .findFirst();

            if(optionalAccountSameBranch.isPresent()) {
                throw new ClientHasAccountAtBranchException("Client already has an account at this branch!");
            }
        }

        Account account = accountService.createAccount(clientDto.getBranch());

        client.addAccount(account);
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
        client.setFirstName(updateClientDto.getFirstName());
        client.setLastName(updateClientDto.getLastName());

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
            throw new ClientNotFoundException("Client cannot be found in database!");
        }

        return optionalClient.get();
    }

}
