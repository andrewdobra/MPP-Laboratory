import domain.validators.*;
import domain.*;
import domain.xml.BookXML;
import domain.xml.ClientXML;
import domain.xml.PurchaseXML;
import service.*;
import repository.*;
import ui.*;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Validator<Client> clientValidator = new ClientValidator();
        Validator<Book> bookValidator = new BookValidator();
        Validator<Purchase> purchaseValidator = new PurchaseValidator();

        Repository<Long,Client> clientFileRepository = new ClientFileRepository( "src\\data\\clients.txt");
        Repository<Long, Book> bookFileRepository = new BookFileRepository("src\\data\\books.txt");
        Repository<Long, Purchase> purchaseFileRepository = new PurchaseFileRepository("src\\data\\purchases.txt");

        Repository<Long, Client> clientXMLRepository = new XMLRepository(new ClientXML(),"src\\data\\clients.xml");//
        Repository<Long, Book> bookXMLRepository = new XMLRepository(new BookXML(), "src\\data\\books.xml");
        Repository<Long, Purchase> purchaseXMLRepository = new XMLRepository(new PurchaseXML(), "src\\data\\purchases.xml");

//        Repository<Long, Client> clientSQLRepository = new DatabaseRepository<Long,Client>(clientValidator,"Clients",new ClientSQL());
//        Repository<Long, Book> bookSQLRepository = new DatabaseRepository<Long,Book>(bookValidator,"Books",new BookSQL());
//        Repository<Long, Purchase> purchaseSQLRepository = new DatabaseRepository<Long,Purchase>(purchaseValidator,"Purchases",new PurchaseSQL());

        Repository<Long, Book> bookRepository;
        Repository<Long, Client> clientRepository;
        Repository<Long, Purchase> purchaseRepository;

        System.out.println("Select repository (1 - text, 2 - xml, 3 - database)");

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
                bookRepository = bookXMLRepository;
                clientRepository = clientXMLRepository;
                purchaseRepository = purchaseXMLRepository;

        }

        ClientService clientService = new ClientService(clientRepository,clientValidator);
        BookService bookService = new BookService(bookRepository,bookValidator);
        PurchaseService purchaseService = new PurchaseService(purchaseRepository,purchaseValidator,bookService,clientService);
        Console console = new Console(clientService, bookService, purchaseService);
        console.runConsole();
    }
}
