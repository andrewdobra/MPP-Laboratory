import domain.validators.*;
import domain.*;
import service.*;
import repository.*;
import ui.*;

public class Main {
    public static void main(String[] args) {
        Validator<Client> clientValidator = new ClientValidator();
        Validator<Book> bookValidator = new BookValidator();
        Repository<Long, Client> clientRepository = new InMemoryRepository<>();
        Repository<Long, Book> bookRepository = new BookFileRepository("D:\\MPP-Laboratory-master\\src\\main\\java\\data\\books.txt");
        Repository<Long, Purchase> purchaseRepository = new InMemoryRepository<>();
        Validator<Purchase> purchaseValidator = new PurchaseValidator(bookRepository, clientRepository);
        clientRepository.save(new Client(Long.valueOf(1),"Paul"));
        clientRepository.save(new Client(Long.valueOf(2),"Tudor"));
        purchaseRepository.save(new Purchase(Long.valueOf(1),Long.valueOf(2),Long.valueOf(1)));
        ClientService clientService = new ClientService(clientRepository, clientValidator);
        BookService bookService = new BookService(bookRepository, bookValidator);
        PurchaseService purchaseService = new PurchaseService(purchaseRepository, purchaseValidator);

        Console console = new Console(clientService, bookService,purchaseService);
        console.runConsole();
    }
}
