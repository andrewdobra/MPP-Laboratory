import domain.validators.*;
import domain.*;
import domain.modules.*;
import service.*;
import repository.*;
import ui.*;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Validator<Client> clientValidator = new ClientValidator();
        Validator<Book> bookValidator = new BookValidator();
        Validator<Purchase> purchaseValidator = new PurchaseValidator();

        String systemPath = "src\\main\\java\\data\\";
        //String systemPath = "src/main/java/data/";

        Repository<Long,Client> clientFileRepository = new ClientFileRepository(systemPath + "clients.txt");
        Repository<Long, Book> bookFileRepository = new BookFileRepository(systemPath + "books.txt");
        Repository<Long, Purchase> purchaseFileRepository = new PurchaseFileRepository(systemPath + "purchases.txt");

        Repository<Long, Client> clientXMLRepository = new XMLRepository<>(new ClientXML(),systemPath + "clients.xml");//
        Repository<Long, Book> bookXMLRepository = new XMLRepository<>(new BookXML(), systemPath + "books.xml");
        Repository<Long, Purchase> purchaseXMLRepository = new XMLRepository<>(new PurchaseXML(), systemPath + "purchases.xml");

        Repository<Long, Client> clientSQLRepository = new DatabaseRepository<>("Clients", new ClientSQL());
        Repository<Long, Book> bookSQLRepository = new DatabaseRepository<>("Books", new BookSQL());
        Repository<Long, Purchase> purchaseSQLRepository = new DatabaseRepository<>("Purchases", new PurchaseSQL());

        Repository<Long, Book> bookRepository;
        Repository<Long, Client> clientRepository;
        Repository<Long, Purchase> purchaseRepository;

        System.out.println("Select repository (1 - text, 2 - modules, 3 - database)");

        int choice = new Scanner(System.in).nextInt();
        switch(choice)
        {
            case 1:
                bookRepository = bookFileRepository;
                clientRepository = clientFileRepository;
                purchaseRepository = purchaseFileRepository;
                break;
            case 2:
                bookRepository = bookXMLRepository;
                clientRepository = clientXMLRepository;
                purchaseRepository = purchaseXMLRepository;
                break;
            default:
                bookRepository = bookSQLRepository;
                clientRepository = clientSQLRepository;
                purchaseRepository = purchaseSQLRepository;

        }

        BookService bookService = new BookService(bookRepository,bookValidator);
        ClientService clientService = new ClientService(clientRepository,clientValidator);
        PurchaseService purchaseService = new PurchaseService(purchaseRepository,purchaseValidator,bookService,clientService);

        Console console = new Console(clientService, bookService, purchaseService);
        console.runConsole();
    }
}
