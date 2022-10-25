package com.internetbanking.account.controller;

import com.internetbanking.account.dto.client.ClientDto;
import com.internetbanking.account.dto.client.CreateClientDto;
import com.internetbanking.account.dto.client.UpdateClientDto;
import com.internetbanking.account.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/createClient")
    public ResponseEntity<ClientDto> registerClient(@RequestBody @Valid CreateClientDto createClientDto, UriComponentsBuilder uriBuilder) {
        ClientDto clientDto = clientService.registerClient(createClientDto);
        URI uri = uriBuilder.path("/client/createClient")
                .buildAndExpand(createClientDto.getClientId())
                .toUri();
        return ResponseEntity.created(uri).body(clientDto);
    }

    @GetMapping("/listClients")
    public ResponseEntity<Page<ClientDto>> listClients(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<ClientDto> clientDtoList = clientService.listClients(pageable);
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
