import domain.validators.*;
import domain.*;
import service.*;
import repository.*;
import ui.*;

public class Main {
    public static void main(String[] args) {
        Validator<Client> clientValidator = new ClientValidator();
        Validator<Book> bookValidator = new BookValidator();
        Repository<Long, Client> clientRepository = new InMemoryRepository<>(clientValidator);
        Repository<Long, Book> bookRepository = new InMemoryRepository<>(bookValidator);
        ClientService clientService = new ClientService(clientRepository);
        BookService bookService = new BookService(bookRepository);
        Console console = new Console(clientService, bookService);
        console.runConsole();

        System.out.println("Hello Bookstore!");
    }
}
