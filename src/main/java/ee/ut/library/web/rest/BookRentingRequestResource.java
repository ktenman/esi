package ee.ut.library.web.rest;

import ee.ut.library.domain.BookRentingRequest;
import ee.ut.library.repository.BookRentingRequestRepository;
import ee.ut.library.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link ee.ut.library.domain.BookRentingRequest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BookRentingRequestResource {

    private static final String ENTITY_NAME = "bookRentingRequest";
    private final Logger log = LoggerFactory.getLogger(BookRentingRequestResource.class);
    private final BookRentingRequestRepository bookRentingRequestRepository;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public BookRentingRequestResource(BookRentingRequestRepository bookRentingRequestRepository) {
        this.bookRentingRequestRepository = bookRentingRequestRepository;
    }

    /**
     * {@code POST  /book-renting-requests} : Create a new bookRentingRequest.
     *
     * @param bookRentingRequest the bookRentingRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookRentingRequest,
     * or with status {@code 400 (Bad Request)} if the bookRentingRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/book-renting-requests")
    public ResponseEntity<BookRentingRequest> createBookRentingRequest(@RequestBody BookRentingRequest bookRentingRequest)
        throws URISyntaxException {
        log.debug("REST request to save BookRentingRequest : {}", bookRentingRequest);
        if (bookRentingRequest.getId() != null) {
            throw new BadRequestAlertException("A new bookRentingRequest cannot already have an ID", ENTITY_NAME,
                "idexists");
        }
        BookRentingRequest result = bookRentingRequestRepository.save(bookRentingRequest);
        return ResponseEntity
            .created(new URI("/api/book-renting-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /book-renting-requests/:id} : Updates an existing bookRentingRequest.
     *
     * @param id                 the id of the bookRentingRequest to save.
     * @param bookRentingRequest the bookRentingRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookRentingRequest,
     * or with status {@code 400 (Bad Request)} if the bookRentingRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookRentingRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/book-renting-requests/{id}")
    public ResponseEntity<BookRentingRequest> updateBookRentingRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BookRentingRequest bookRentingRequest
    ) throws URISyntaxException {
        log.debug("REST request to update BookRentingRequest : {}, {}", id, bookRentingRequest);
        if (bookRentingRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookRentingRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookRentingRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BookRentingRequest result = bookRentingRequestRepository.save(bookRentingRequest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                bookRentingRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /book-renting-requests/:id} : Partial updates given fields of an existing bookRentingRequest,
     * field will ignore if it is null
     *
     * @param id                 the id of the bookRentingRequest to save.
     * @param bookRentingRequest the bookRentingRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookRentingRequest,
     * or with status {@code 400 (Bad Request)} if the bookRentingRequest is not valid,
     * or with status {@code 404 (Not Found)} if the bookRentingRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the bookRentingRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/book-renting-requests/{id}", consumes = {"application/json", "application/merge-patch" +
        "+json"})
    public ResponseEntity<BookRentingRequest> partialUpdateBookRentingRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BookRentingRequest bookRentingRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update BookRentingRequest partially : {}, {}", id, bookRentingRequest);
        if (bookRentingRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookRentingRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookRentingRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookRentingRequest> result = bookRentingRequestRepository
            .findById(bookRentingRequest.getId())
            .map(existingBookRentingRequest -> {
                if (bookRentingRequest.getStatus() != null) {
                    existingBookRentingRequest.setStatus(bookRentingRequest.getStatus());
                }
                if (bookRentingRequest.getRentedAt() != null) {
                    existingBookRentingRequest.setRentedAt(bookRentingRequest.getRentedAt());
                }
                if (bookRentingRequest.getRentedUntil() != null) {
                    existingBookRentingRequest.setRentedUntil(bookRentingRequest.getRentedUntil());
                }

                return existingBookRentingRequest;
            })
            .map(bookRentingRequestRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                bookRentingRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /book-renting-requests} : get all the bookRentingRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookRentingRequests in body.
     */
    @GetMapping("/book-renting-requests")
    public ResponseEntity<List<BookRentingRequest>> getAllBookRentingRequests(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BookRentingRequests");
        Page<BookRentingRequest> page = bookRentingRequestRepository.findAll(pageable);
        HttpHeaders headers =
            PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /book-renting-requests/:id} : get the "id" bookRentingRequest.
     *
     * @param id the id of the bookRentingRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookRentingRequest, or with
     * status {@code 404 (Not Found)}.
     */
    @GetMapping("/book-renting-requests/{id}")
    public ResponseEntity<BookRentingRequest> getBookRentingRequest(@PathVariable Long id) {
        log.debug("REST request to get BookRentingRequest : {}", id);
        Optional<BookRentingRequest> bookRentingRequest = bookRentingRequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bookRentingRequest);
    }

    /**
     * {@code DELETE  /book-renting-requests/:id} : delete the "id" bookRentingRequest.
     *
     * @param id the id of the bookRentingRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/book-renting-requests/{id}")
    public ResponseEntity<Void> deleteBookRentingRequest(@PathVariable Long id) {
        log.debug("REST request to delete BookRentingRequest : {}", id);
        bookRentingRequestRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
