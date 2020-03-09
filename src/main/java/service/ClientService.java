package service;

import domain.Client;
import domain.validators.ValidatorException;
import repository.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {
    private Repository<Long, Client> repository;

    public ClientService(Repository<Long, Client> repository) {
        this.repository = repository;
    }

    public void addClient(Client student) throws ValidatorException {
        repository.save(student);
    }

    public void delClient(Client client) throws ValidatorException {
        repository.delete(client.getId());
    }

    public Set<Client> getAllClients() {
        Iterable<Client> students = repository.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Client> filterClientsByName(String s) {
        Iterable<Client> students = repository.findAll();
        Set<Client> filteredClients= new HashSet<>();

        students.forEach(filteredClients::add);
        filteredClients.removeIf(student -> !student.getName().contains(s));

        return filteredClients;
    }
}
