package ee.ut.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.ut.library.domain.enumeration.BookStatus;
import ee.ut.library.domain.enumeration.Category;
import ee.ut.library.domain.enumeration.Language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties(value = {"book"}, allowSetters = true)
    private Set<BookRentingRequest> bookRentingRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book author(String author) {
        this.setAuthor(author);
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Book title(String title) {
        this.setTitle(title);
        return this;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Book releaseDate(LocalDate releaseDate) {
        this.setReleaseDate(releaseDate);
        return this;
    }

    public BookStatus getStatus() {
        return this.status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Book status(BookStatus status) {
        this.setStatus(status);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Book category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Book language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public Set<BookRentingRequest> getBookRentingRequests() {
        return this.bookRentingRequests;
    }

    public void setBookRentingRequests(Set<BookRentingRequest> bookRentingRequests) {
        if (this.bookRentingRequests != null) {
            this.bookRentingRequests.forEach(i -> i.setBook(null));
        }
        if (bookRentingRequests != null) {
            bookRentingRequests.forEach(i -> i.setBook(this));
        }
        this.bookRentingRequests = bookRentingRequests;
    }

    public Book bookRentingRequests(Set<BookRentingRequest> bookRentingRequests) {
        this.setBookRentingRequests(bookRentingRequests);
        return this;
    }

    public Book addBookRentingRequest(BookRentingRequest bookRentingRequest) {
        this.bookRentingRequests.add(bookRentingRequest);
        bookRentingRequest.setBook(this);
        return this;
    }

    public Book removeBookRentingRequest(BookRentingRequest bookRentingRequest) {
        this.bookRentingRequests.remove(bookRentingRequest);
        bookRentingRequest.setBook(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", title='" + getTitle() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", category='" + getCategory() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
