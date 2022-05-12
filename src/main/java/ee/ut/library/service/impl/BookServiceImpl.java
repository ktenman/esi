package ee.ut.library.service.impl;

import ee.ut.library.domain.Book;
import ee.ut.library.repository.BookRepository;
import ee.ut.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Book}.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        log.debug("Request to save Book : {}", book);
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> partialUpdate(Book book) {
        log.debug("Request to partially update Book : {}", book);

        return bookRepository
            .findById(book.getId())
            .map(existingBook -> {
                if (book.getAuthor() != null) {
                    existingBook.setAuthor(book.getAuthor());
                }
                if (book.getTitle() != null) {
                    existingBook.setTitle(book.getTitle());
                }
                if (book.getReleaseDate() != null) {
                    existingBook.setReleaseDate(book.getReleaseDate());
                }
                if (book.getStatus() != null) {
                    existingBook.setStatus(book.getStatus());
                }
                if (book.getCategory() != null) {
                    existingBook.setCategory(book.getCategory());
                }
                if (book.getLanguage() != null) {
                    existingBook.setLanguage(book.getLanguage());
                }

                return existingBook;
            })
            .map(bookRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.deleteById(id);
    }
}
