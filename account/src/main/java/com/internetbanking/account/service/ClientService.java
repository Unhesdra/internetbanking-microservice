package com.internetbanking.account.service;

import com.internetbanking.account.dto.client.ClientDto;
import com.internetbanking.account.dto.client.CreateClientDto;
import com.internetbanking.account.dto.client.UpdateClientDto;
import com.internetbanking.account.entity.BankClient;
import com.internetbanking.account.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientDto registerClient(CreateClientDto clientDto) {
        BankClient client = new BankClient(
                clientDto.getFirstName(),
                clientDto.getLastName(),
                clientDto.getDocumentId()
        );

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

}
