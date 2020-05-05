package domain.validators;

import domain.Purchase;

public class PurchaseValidator implements Validator<Purchase> {
    @Override
    public void validate(Purchase entity) throws ValidatorException {
        if (!(entity != null && entity.getId() >= 0 && entity.getBookID() >= 0 && entity.getClientID() >= 0))
            throw new ValidatorException("Purchase can not be null.");
    }
}