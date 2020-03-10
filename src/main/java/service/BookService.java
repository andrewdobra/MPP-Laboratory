package service;

import domain.Book;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import repository.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookService {
    private Repository<Long, Book> repository;
    protected Validator<Book> validator;

    public BookService(Repository<Long, Book> repository, Validator<Book> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void addBook(Book book) throws ValidatorException {
        validator.validate(book);
        repository.save(book);
    }

    public void delBook(Book book) throws ValidatorException {
        repository.delete(book.getId());
    }

    public Optional<Book> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return this.repository.findOne(id);
    }

    public Set<Book> getAllBooks() {
        Iterable<Book> students = repository.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Book> filterBooksByName(String s) {
        Iterable<Book> students = repository.findAll();
        Set<Book> filteredBooks= new HashSet<>();

        students.forEach(filteredBooks::add);
        filteredBooks.removeIf(student -> !student.getName().contains(s));

        return filteredBooks;
    }
}