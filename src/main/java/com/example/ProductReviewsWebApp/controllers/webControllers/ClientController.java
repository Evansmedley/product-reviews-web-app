package com.example.ProductReviewsWebApp.controllers.webControllers;

import com.example.ProductReviewsWebApp.models.Client;
import com.example.ProductReviewsWebApp.models.ClientsByJaccardDistanceSorted;
import com.example.ProductReviewsWebApp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    private Client getClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }
        return client.get();
    }

    private Client findClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }
        return client.get();
    }

    @GetMapping(value = "/client", produces = "application/json")
    public String getClients(Model model) {
        List<Client> clientList = clientRepository.findAll();
        model.addAttribute("ClientList", clientList);
        return "client";
    }

    @GetMapping(value = "/myaccount", produces = "application/json")
    public String getMyAccount(@CookieValue(value = "activeClientID") String activeClientId, Model model) {
        Client client = getClient(Long.parseLong(activeClientId));
        model.addAttribute("activeClient", client);
        return "myAccount";
    }

    @GetMapping(value = "/client/{id}", produces = "application/json")
    public String getClientById(@PathVariable("id") Long id, @CookieValue(value = "activeClientID") String activeClientId, Model model) {
        if (id == Long.parseLong(activeClientId)) {
            model.addAttribute("activeClient", getClient(Long.parseLong(activeClientId)));
            return "myAccount";
        }
        Client client = this.findClientById(id);
        Client activeClient = this.findClientById(Long.parseLong(activeClientId));
        model.addAttribute("client", client);
        model.addAttribute("activeClient", activeClient);
        return "client-page";
    }

    @GetMapping(value = "/client/username/{name}", produces = "application/json")
    public String getClientByName(@PathVariable("name") String name, @CookieValue(value = "activeClientID") String activeClientId,  Model model) {
        Client client = clientRepository.findByUsername(name);
        Client activeClient = this.findClientById(Long.parseLong(activeClientId));
        model.addAttribute("client", client);
        model.addAttribute("activeClient", activeClient);
        return "client-page";
    }

    @GetMapping(value = "/client/clientsByJaccardDistance", produces = "application/json")
    public String getClientsByJaccardDistance(@CookieValue(value = "activeClientID") String activeClientId, Model model) {
        ClientsByJaccardDistanceSorted clients = new ClientsByJaccardDistanceSorted(clientRepository, Long.parseLong(activeClientId));
        List<Client> clientsSorted = clients.getClientsSortedByJaccardDistance();

        model.addAttribute("clientsByJaccardDistance", clients);
        model.addAttribute("listOfClientsByJaccardDistance", clientsSorted);
        return "clientsByJaccardDistance";
    }
}