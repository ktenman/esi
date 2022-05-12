package ee.ut.library.repository;

import ee.ut.library.domain.BookRentingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BookRentingRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRentingRequestRepository extends JpaRepository<BookRentingRequest, Long> {}
