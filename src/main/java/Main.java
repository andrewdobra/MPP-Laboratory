import domain.validators.*;
import domain.*;
import domain.modules.*;
import service.*;
import repository.*;
import socket.Server;
import ui.*;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        System.out.println("Server (0), Client (1) or Local (2): ");

        int start = new Scanner(System.in).nextInt();

        if (start == 0) {
            Server server = new Server(8085);
        } else if (start == 1) {
            socket.Client client = new socket.Client("127.0.0.1", 8085);
        } else if (start == 2) {
            Validator<Client> clientValidator = new ClientValidator();
            Validator<Book> bookValidator = new BookValidator();
            Validator<Purchase> purchaseValidator = new PurchaseValidator();

            String systemPath = "src\\main\\java\\data\\"; // Linux path
            //String systemPath = "src/main/java/data/"; // Windows path

            Repository<Long, Client> clientFileRepository = new ClientFileRepository(systemPath + "clients.txt");
            Repository<Long, Book> bookFileRepository = new BookFileRepository(systemPath + "books.txt");
            Repository<Long, Purchase> purchaseFileRepository = new PurchaseFileRepository(systemPath + "purchases.txt");

            Repository<Long, Client> clientXMLRepository = new XMLRepository<>(new ClientXML(), systemPath + "clients.xml");//
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
            switch (choice) {
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

            BookService bookService = new BookService(bookRepository, bookValidator);
            ClientService clientService = new ClientService(clientRepository, clientValidator);
            PurchaseService purchaseService = new PurchaseService(purchaseRepository, purchaseValidator, bookService, clientService);

            bookService.setPurchaseService(purchaseService);
            clientService.setPurchaseService(purchaseService);

            Console console = new Console(clientService, bookService, purchaseService);
            console.runConsole();
        }
    }
}
