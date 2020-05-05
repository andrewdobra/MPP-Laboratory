package domain.validators;
import domain.Book;

public class BookValidator implements Validator<Book> {
    @Override
    public void validate(Book entity) throws ValidatorException {
        if (!(entity != null && entity.getId() >= 0))
            throw new ValidatorException("Client can not be null.");
    }
}
