package com.internetbanking.account.controller;

import com.internetbanking.account.dto.client.ClientDto;
import com.internetbanking.account.dto.client.CreateClientDto;
import com.internetbanking.account.dto.client.UpdateClientDto;
import com.internetbanking.account.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/createClient")
    public ResponseEntity<ClientDto> registerClient(@RequestBody @Valid CreateClientDto createClientDto) {
        ClientDto clientDto = clientService.registerClient(createClientDto);
        return ResponseEntity.ok(clientDto);
    }

    @GetMapping("/listClients")
    public ResponseEntity<List<ClientDto>> listClients() {
        List<ClientDto> clientDtoList = clientService.listClients();
        return ResponseEntity.ok(clientDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> listSpecificClient(@PathVariable("id") Long clientId) {
        ClientDto clientDto = clientService.listSpecificClient(clientId);
        return ResponseEntity.ok(clientDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable("id") Long clientId, @RequestBody @Valid UpdateClientDto updateClientDto) {
        ClientDto clientDto = clientService.updateClient(updateClientDto, clientId);
        return ResponseEntity.ok(clientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable("id") Long clientId) {
        ClientDto clientDto = clientService.deleteClient(clientId);
        return ResponseEntity.ok(clientDto);
    }

}
