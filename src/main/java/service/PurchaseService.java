package service;

import domain.Book;
import domain.Client;
import domain.validators.*;
import domain.Purchase;
import repository.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PurchaseService {
    private Repository<Long, Purchase> repository;
    protected Validator<Purchase> validator;
    private BookService bookService;
    private ClientService clientService;

    public PurchaseService(Repository<Long, Purchase> repository, Validator<Purchase> validator, BookService bookService, ClientService clientService) {
        this.repository = repository;
        this.validator = validator;
        this.bookService = bookService;
        this.clientService = clientService;
    }

    public void addPurchase(Purchase purchase) throws ValidatorException {
        try {
            validator.validate(purchase);

            if ((this.bookService.findOne(purchase.getBookID()).isPresent()) && (this.clientService.findOne(purchase.getClientID()).isPresent()))
                repository.save(purchase);
            else
                throw new ValidatorException("ID for book or client is invalid.") ;

        } catch (ValidatorException err){
            System.out.println(err);
        }
    }

    public void delPurchase(Purchase purchase) throws ValidatorException {
        validator.validate(purchase);
        repository.delete(purchase.getId());
    }

    public Set<Purchase> getAllPurchases() {
        Iterable<Purchase> students = repository.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Purchase> filterPurchasesByClient(Long id) {
        Iterable<Purchase> students = repository.findAll();
        Set<Purchase> filteredPurchases= new HashSet<>();
        students.forEach(filteredPurchases::add);
        filteredPurchases.removeIf(purchase -> !purchase.getClientID().equals(id));

        return filteredPurchases;
    }

    public Set<Purchase> filterPurchasesByBook(Long id) {
        Iterable<Purchase> students = repository.findAll();
        Set<Purchase> filteredPurchases= new HashSet<>();
        students.forEach(filteredPurchases::add);
        filteredPurchases.removeIf(purchase -> !purchase.getBookID().equals(id));

        return filteredPurchases;
    }
}