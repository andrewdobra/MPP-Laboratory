package domain.validators;

import domain.Book;
import domain.Client;
import domain.Purchase;
import repository.Repository;

public class PurchaseValidator implements Validator<Purchase>{
    private Repository<Long, Book> bookRepository;
    private Repository<Long, Client> clientRepository;
    public PurchaseValidator(Repository<Long, Book> bookRepository, Repository<Long, Client> clientRepository) {
        this.bookRepository = bookRepository;
        this.clientRepository = clientRepository;
    }

    public void validate(Purchase entity) throws ValidatorException {
        if (entity != null) {
            if ((this.bookRepository.findOne(entity.getBookID()).isPresent()) && (clientRepository.findOne(entity.getClientID()).isPresent()))
                return;
            else
                throw new ValidatorException("ID for book or client is invalid.") ;
        } else
            throw new ValidatorException("Purchase can not be null.");
    }
}