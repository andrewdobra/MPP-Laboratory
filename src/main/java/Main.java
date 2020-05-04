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

        Repository<Long, Client> clientRepository = new ClientFileRepository("src\\main\\java\\data\\clients.txt");
        Repository<Long, Book> bookRepository = new BookFileRepository("src\\main\\java\\data\\books.txt");
        Repository<Long, Purchase> purchaseRepository = new PurchaseFileRepository("src\\main\\java\\data\\purchases.txt");

        ClientService clientService = new ClientService(clientRepository, clientValidator);
        BookService bookService = new BookService(bookRepository, bookValidator);
        PurchaseService purchaseService = new PurchaseService(purchaseRepository, purchaseValidator, bookService, clientService);

        Console console = new Console(clientService, bookService,purchaseService);
        console.runConsole();
    }
}
