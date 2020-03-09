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
    private Scanner keyboard = new Scanner(System.in);

    public Console(ClientService clientService, BookService bookService) {
        this.clientService = clientService;
        this.bookService = bookService;
    }

    public void runConsole() {
        System.out.println("Choose an action:\n" +
                "1. Add Clients\n" +
                "2. Add Books\n" +
                "3. Update Clients\n" +
                "4. Update Books\n" +
                "5. Delete Clients\n" +
                "6. Delete Books\n" +
                "7. Show all Clients\n" +
                "8. Show all Books\n" +
                "9. Filter Clients\n" +
                "10. Filter Books\n" +
                "0. Exit\n" +
                "\n" +
                "Choice: ");

        int choice = keyboard.nextInt();

        while (choice != 0) {
            switch(choice) {
                case 1: addClients(); break;
                case 2: addBooks(); break;
                case 3: updateClients(); break;
                case 4: updateBooks(); break;
                case 5: delClients(); break;
                case 6: delBooks(); break;
                case 7: printAllClients(); break;
                case 8: printAllBooks(); break;
                case 9: filterClients(); break;
                case 10: filterBooks(); break;
            }

            System.out.println("Choose an action:\n" +
                    "1. Add Clients\n" +
                    "2. Add Books\n" +
                    "3. Update Clients\n" +
                    "4. Update Books\n" +
                    "5. Delete Clients\n" +
                    "6. Delete Books\n" +
                    "7. Show all Clients\n" +
                    "8. Show all Books\n" +
                    "9. Filter Clients\n" +
                    "10. Filter Books\n" +
                    "0. Exit\n" +
                    "\n" +
                    "Choice: ");

            choice = keyboard.nextInt();
        }
    }

    private void filterClients() {
        System.out.println("Pattern: ");
        keyboard.nextLine();
        String pat = keyboard.nextLine();
        System.out.println("Filtered clients (name containing " + pat + "):");
        Set<Client> clients = clientService.filterClientsByName(pat);
        clients.forEach(System.out::println);
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
