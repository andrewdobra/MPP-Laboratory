package service;

import domain.Client;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import repository.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {
    private Repository<Long, Client> repository;
    protected Validator<Client> validator;

    public ClientService(Repository<Long, Client> repository, Validator<Client> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void addClient(Client client) throws ValidatorException {
        validator.validate(client);
        repository.save(client);
    }

    public void delClient(Client client) throws ValidatorException {
        validator.validate(client);
        repository.delete(client.getId());
    }

    public Optional<Client> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return this.repository.findOne(id);
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
