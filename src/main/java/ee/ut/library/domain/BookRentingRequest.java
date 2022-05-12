package ee.ut.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.ut.library.domain.enumeration.RentingStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * A BookRentingRequest.
 */
@Entity
@Table(name = "book_renting_request")
public class BookRentingRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RentingStatus status;

    @Column(name = "rented_at")
    private Instant rentedAt;

    @Column(name = "rented_until")
    private Instant rentedUntil;

    @ManyToOne
    @JsonIgnoreProperties(value = {"bookRentingRequests"}, allowSetters = true)
    private Book book;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookRentingRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public RentingStatus getStatus() {
        return this.status;
    }

    public void setStatus(RentingStatus status) {
        this.status = status;
    }

    public BookRentingRequest status(RentingStatus status) {
        this.setStatus(status);
        return this;
    }

    public Instant getRentedAt() {
        return this.rentedAt;
    }

    public void setRentedAt(Instant rentedAt) {
        this.rentedAt = rentedAt;
    }

    public BookRentingRequest rentedAt(Instant rentedAt) {
        this.setRentedAt(rentedAt);
        return this;
    }

    public Instant getRentedUntil() {
        return this.rentedUntil;
    }

    public void setRentedUntil(Instant rentedUntil) {
        this.rentedUntil = rentedUntil;
    }

    public BookRentingRequest rentedUntil(Instant rentedUntil) {
        this.setRentedUntil(rentedUntil);
        return this;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookRentingRequest book(Book book) {
        this.setBook(book);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookRentingRequest)) {
            return false;
        }
        return id != null && id.equals(((BookRentingRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookRentingRequest{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", rentedAt='" + getRentedAt() + "'" +
            ", rentedUntil='" + getRentedUntil() + "'" +
            "}";
    }
}
