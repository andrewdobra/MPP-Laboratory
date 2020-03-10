package domain.validators;

import domain.Book;
import domain.Client;
import domain.Purchase;
import repository.Repository;

public class PurchaseValidator implements Validator<Purchase>{
    public void validate(Purchase entity) throws ValidatorException {
        if (entity != null) {
            return;
        } else
            throw new ValidatorException("Purchase can not be null.");
    }
}