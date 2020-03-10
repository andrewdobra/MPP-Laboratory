import domain.validators.*;
import domain.*;
import service.*;
import repository.*;
import ui.*;

public class Main {
    public static void main(String[] args) {
        Validator<Client> clientValidator = new ClientValidator();
        Validator<Book> bookValidator = new BookValidator();
        Validator<Purchase> purchaseValidator = new PurchaseValidator();
        Repository<Long, Client> clientRepository = new InMemoryRepository<>(clientValidator);
        Repository<Long, Book> bookRepository = new InMemoryRepository<>(bookValidator);
        Repository<Long, Purchase> purchaseRepository = new InMemoryRepository<>(purchaseValidator);
        clientRepository.save(new Client(Long.valueOf(1),"Paul"));
        clientRepository.save(new Client(Long.valueOf(2),"Tudor"));
        bookRepository.save(new Book(Long.valueOf(1),"The best book"));
        bookRepository.save(new Book(Long.valueOf(2),"12 rules"));
        purchaseRepository.save(new Purchase(Long.valueOf(1),Long.valueOf(2),Long.valueOf(1)));
        ClientService clientService = new ClientService(clientRepository);
        BookService bookService = new BookService(bookRepository);
        PurchaseService purchaseService = new PurchaseService(purchaseRepository);


        Console console = new Console(clientService, bookService,purchaseService);
        console.runConsole();

        System.out.println("Hello Bookstore!");
    }
}
