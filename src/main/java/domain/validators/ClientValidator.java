package domain.validators;
import domain.Client;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
        if (!(entity != null && entity.getId() >= 0))
            throw new ValidatorException("Client can not be null.");
    }
}