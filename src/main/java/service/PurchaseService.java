package service;

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

    public PurchaseService(Repository<Long, Purchase> repository, Validator<Purchase> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void addPurchase(Purchase purchase) throws ValidatorException {
        try {
            validator.validate(purchase);
            repository.save(purchase);
        } catch (ValidatorException err){
            System.out.println(err);
        }
    }

    public void delPurchase(Purchase purchase) throws ValidatorException {
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