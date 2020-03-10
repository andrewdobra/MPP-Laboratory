package ui;

import domain.*;
import domain.validators.*;
import service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.Scanner;

public class Console {
    private ClientService clientService;
    private BookService bookService;
    private PurchaseService purchaseService;
    private Scanner keyboard = new Scanner(System.in);

    public Console(ClientService clientService, BookService bookService, PurchaseService purchaseService) {
        this.clientService = clientService;
        this.bookService = bookService;
        this.purchaseService = purchaseService;
    }

    public void runConsole() {
        System.out.print("Hello Bookstore!\n\n" +
                "Choose an action:\n" +
                "1. Add Clients\n" +
                "2. Add Books\n" +
                "3. Add Purchases\n" +
                "4. Update Clients\n" +
                "5. Update Books\n" +
                "6. Update Purchases\n" +
                "7. Delete Clients\n" +
                "8. Delete Books\n" +
                "9. Delete Purchases\n" +
                "10. Show all Clients\n" +
                "11. Show all Books\n" +
                "12. Show all Purchases\n" +
                "13. Filter Clients\n" +
                "14. Filter Books\n" +
                "15. Filter Purchases\n" +
                "0. Exit\n" +
                "\n" +
                "Choice: ");

        int choice = keyboard.nextInt();

        while (choice != 0) {
            switch(choice) {
                case 1: addClients(); break;
                case 2: addBooks(); break;
                case 3: addPurchases(); break;
                case 4: updateClients(); break;
                case 5: updateBooks(); break;
                case 6: updatePurchases(); break;
                case 7: delClients(); break;
                case 8: delBooks(); break;
                case 9: delPurchases(); break;
                case 10: printAllClients(); break;
                case 11: printAllBooks(); break;
                case 12: printAllPurchases(); break;
                case 13: filterClients(); break;
                case 14: filterBooks(); break;
                case 15: filterPurchases(); break;
            }

            System.out.print("Hello Bookstore!\n\n" +
                    "Choose an action:\n" +
                    "1. Add Clients\n" +
                    "2. Add Books\n" +
                    "3. Add Purchases\n" +
                    "4. Update Clients\n" +
                    "5. Update Books\n" +
                    "6. Update Purchases\n" +
                    "7. Delete Clients\n" +
                    "8. Delete Books\n" +
                    "9. Delete Purchases\n" +
                    "10. Show all Clients\n" +
                    "11. Show all Books\n" +
                    "12. Show all Purchases\n" +
                    "13. Filter Clients\n" +
                    "14. Filter Books\n" +
                    "15. Filter Purchases\n" +
                    "0. Exit\n" +
                    "\n" +
                    "Choice: ");

            choice = keyboard.nextInt();
        }
    }
    private void filterPurchases() {
        System.out.println("1. Client ID or 2.Book ID: ");
        Integer choice = keyboard.nextInt();
        Long id = keyboard.nextLong();
        Set<Purchase> purchases;
        if (choice == 1)
            purchases = purchaseService.filterPurchasesByClient(id);
        else if (choice == 2)
            purchases = purchaseService.filterPurchasesByBook(id);
        else
            throw new BookstoreException("Invalid choice!");

        System.out.println("Filtered purchases: ");
        purchases.stream().forEach(System.out::println);
    }

    private void filterClients() {
        System.out.println("Pattern: ");
        keyboard.nextLine();
        String pat = keyboard.nextLine();
        System.out.println("Filtered clients (name containing " + pat + "):");
        Set<Client> clients = clientService.filterClientsByName(pat);
        clients.forEach(System.out::println);
    }

    private void printAllPurchases() {
        Set<Purchase> purchases = purchaseService.getAllPurchases();
        purchases.stream().forEach(System.out::println);
        if(purchases.isEmpty())
            System.out.println("There are no purchases");
    }

    private void filterBooks() {
        System.out.println("Pattern: ");
        keyboard.nextLine();
        String pat = keyboard.nextLine();
        System.out.println("Filtered books (name containing " + pat + "):");
        Set<Book> books = bookService.filterBooksByName(pat);
        books.forEach(System.out::println);
    }

    private void printAllClients() {
        Set<Client> clients = clientService.getAllClients();
        clients.forEach(System.out::println);
    }

    private void printAllBooks() {
        Set<Book> books = bookService.getAllBooks();
        books.forEach(System.out::println);
    }

    private void addClients() {
        while (true) {
            Client client = readClient("");

            if (client == null || client.getId() < 0) {
                break;
            }

            try {
                clientService.addClient(client);
            } catch (ValidatorException err) {
                System.out.println(err.toString());
            }
        }
    }
    private void addPurchases() {
        while (true) {
            Purchase purchase = readPurchase("");
            if (purchase == null || purchase.getId() < 0) {
                break;
            }
            try {
                purchaseService.addPurchase(purchase);
            } catch (ValidatorException err) {
                System.out.println(err.toString());
            }
        }
    }

    private void updatePurchases() {
        while (true) {
            Purchase purchase1 = readPurchase("old ");
            if (purchase1 == null || purchase1.getId() < 0) {
                break;
            }
            try {
                Purchase purchase2 = readPurchase("new ");
                purchaseService.delPurchase(purchase1);
                purchaseService.addPurchase(purchase2);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    private void delPurchases() {
        while (true) {
            Purchase purchase = readPurchase("");
            if (purchase == null || purchase.getId() < 0) {
                break;
            }
            try {
                purchaseService.delPurchase(purchase);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }
    private Purchase readPurchase(String s) {
        System.out.println("Input " + s + "purchase {ID, Client ID, Book ID}:");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            Long CID = Long.valueOf(bufferRead.readLine());
            Long BID = Long.valueOf(bufferRead.readLine());

            return new Purchase(id, CID, BID);
        } catch (Exception err) {
            System.out.println(err);
        }
        return null;
    }

    private void addBooks() {
        while (true) {
            Book book = readBook("");

            if (book == null || book.getId() < 0) {
                break;
            }

            try {
                bookService.addBook(book);
            } catch (ValidatorException err) {
                System.out.println(err.toString());
            }
        }
    }

    private void updateBooks() {
        while (true) {
            Book oldBook = readBook("old ");

            if (oldBook == null || oldBook.getId() < 0) {
                break;
            }

            try {
                Book newBook = readBook("new ");
                bookService.delBook(oldBook);
                bookService.addBook(newBook);
            } catch (ValidatorException err) {
                System.out.println(err.toString());
            }
        }
    }

    private void updateClients() {
        while (true) {
            Client oldClient = readClient("old ");
            if (oldClient == null || oldClient.getId() < 0) {
                break;
            }
            try {
                Client newClient = readClient("new ");
                clientService.delClient(oldClient);
                clientService.addClient(newClient);
            } catch (ValidatorException err) {
                System.out.println(err.toString());
            }
        }
    }

    private void delClients() {
        while (true) {
            Client client = readClient("");
            if (client == null || client.getId() < 0) {
                break;
            }
            try {
                clientService.delClient(client);
            } catch (ValidatorException err) {
                System.out.println(err.toString());
            }
        }
    }

    private void delBooks() {
        while (true) {
            Book book = readBook("");
            if (book == null || book.getId() < 0) {
                break;
            }
            try {
                bookService.delBook(book);
            } catch (ValidatorException err) {
                System.out.println(err.toString());
            }
        }
    }

    private Client readClient(String s) {
        System.out.println("Input " + s + "client {id, name}:");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            String name = bufferRead.readLine();

            Client client = new Client(id, name);
            client.setId(id);

            return client;
        } catch (Exception err) {
            System.out.println(err.toString());
        }
        return null;
    }

    private Book readBook(String s) {
        System.out.println("Input " + s + "book {id, name}:");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            String name = bufferRead.readLine();

            Book book = new Book(id, name);
            book.setId(id);

            return book;
        } catch (Exception err) {
            System.out.println(err.toString());
        }
        return null;
    }
}
