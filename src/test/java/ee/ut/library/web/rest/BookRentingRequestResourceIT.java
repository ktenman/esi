package ee.ut.library.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ee.ut.library.IntegrationTest;
import ee.ut.library.domain.BookRentingRequest;
import ee.ut.library.domain.enumeration.RentingStatus;
import ee.ut.library.repository.BookRentingRequestRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BookRentingRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookRentingRequestResourceIT {

    private static final RentingStatus DEFAULT_STATUS = RentingStatus.OPEN;
    private static final RentingStatus UPDATED_STATUS = RentingStatus.CANCELLED;

    private static final Instant DEFAULT_RENTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RENTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RENTED_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RENTED_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/book-renting-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookRentingRequestRepository bookRentingRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookRentingRequestMockMvc;

    private BookRentingRequest bookRentingRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookRentingRequest createEntity(EntityManager em) {
        BookRentingRequest bookRentingRequest = new BookRentingRequest()
            .status(DEFAULT_STATUS)
            .rentedAt(DEFAULT_RENTED_AT)
            .rentedUntil(DEFAULT_RENTED_UNTIL);
        return bookRentingRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookRentingRequest createUpdatedEntity(EntityManager em) {
        BookRentingRequest bookRentingRequest = new BookRentingRequest()
            .status(UPDATED_STATUS)
            .rentedAt(UPDATED_RENTED_AT)
            .rentedUntil(UPDATED_RENTED_UNTIL);
        return bookRentingRequest;
    }

    @BeforeEach
    public void initTest() {
        bookRentingRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createBookRentingRequest() throws Exception {
        int databaseSizeBeforeCreate = bookRentingRequestRepository.findAll().size();
        // Create the BookRentingRequest
        restBookRentingRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookRentingRequest))
            )
            .andExpect(status().isCreated());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeCreate + 1);
        BookRentingRequest testBookRentingRequest = bookRentingRequestList.get(bookRentingRequestList.size() - 1);
        assertThat(testBookRentingRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBookRentingRequest.getRentedAt()).isEqualTo(DEFAULT_RENTED_AT);
        assertThat(testBookRentingRequest.getRentedUntil()).isEqualTo(DEFAULT_RENTED_UNTIL);
    }

    @Test
    @Transactional
    void createBookRentingRequestWithExistingId() throws Exception {
        // Create the BookRentingRequest with an existing ID
        bookRentingRequest.setId(1L);

        int databaseSizeBeforeCreate = bookRentingRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookRentingRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookRentingRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBookRentingRequests() throws Exception {
        // Initialize the database
        bookRentingRequestRepository.saveAndFlush(bookRentingRequest);

        // Get all the bookRentingRequestList
        restBookRentingRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookRentingRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].rentedAt").value(hasItem(DEFAULT_RENTED_AT.toString())))
            .andExpect(jsonPath("$.[*].rentedUntil").value(hasItem(DEFAULT_RENTED_UNTIL.toString())));
    }

    @Test
    @Transactional
    void getBookRentingRequest() throws Exception {
        // Initialize the database
        bookRentingRequestRepository.saveAndFlush(bookRentingRequest);

        // Get the bookRentingRequest
        restBookRentingRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, bookRentingRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookRentingRequest.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.rentedAt").value(DEFAULT_RENTED_AT.toString()))
            .andExpect(jsonPath("$.rentedUntil").value(DEFAULT_RENTED_UNTIL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBookRentingRequest() throws Exception {
        // Get the bookRentingRequest
        restBookRentingRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBookRentingRequest() throws Exception {
        // Initialize the database
        bookRentingRequestRepository.saveAndFlush(bookRentingRequest);

        int databaseSizeBeforeUpdate = bookRentingRequestRepository.findAll().size();

        // Update the bookRentingRequest
        BookRentingRequest updatedBookRentingRequest = bookRentingRequestRepository.findById(bookRentingRequest.getId()).get();
        // Disconnect from session so that the updates on updatedBookRentingRequest are not directly saved in db
        em.detach(updatedBookRentingRequest);
        updatedBookRentingRequest.status(UPDATED_STATUS).rentedAt(UPDATED_RENTED_AT).rentedUntil(UPDATED_RENTED_UNTIL);

        restBookRentingRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBookRentingRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBookRentingRequest))
            )
            .andExpect(status().isOk());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeUpdate);
        BookRentingRequest testBookRentingRequest = bookRentingRequestList.get(bookRentingRequestList.size() - 1);
        assertThat(testBookRentingRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBookRentingRequest.getRentedAt()).isEqualTo(UPDATED_RENTED_AT);
        assertThat(testBookRentingRequest.getRentedUntil()).isEqualTo(UPDATED_RENTED_UNTIL);
    }

    @Test
    @Transactional
    void putNonExistingBookRentingRequest() throws Exception {
        int databaseSizeBeforeUpdate = bookRentingRequestRepository.findAll().size();
        bookRentingRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookRentingRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookRentingRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookRentingRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookRentingRequest() throws Exception {
        int databaseSizeBeforeUpdate = bookRentingRequestRepository.findAll().size();
        bookRentingRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookRentingRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookRentingRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookRentingRequest() throws Exception {
        int databaseSizeBeforeUpdate = bookRentingRequestRepository.findAll().size();
        bookRentingRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookRentingRequestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookRentingRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookRentingRequestWithPatch() throws Exception {
        // Initialize the database
        bookRentingRequestRepository.saveAndFlush(bookRentingRequest);

        int databaseSizeBeforeUpdate = bookRentingRequestRepository.findAll().size();

        // Update the bookRentingRequest using partial update
        BookRentingRequest partialUpdatedBookRentingRequest = new BookRentingRequest();
        partialUpdatedBookRentingRequest.setId(bookRentingRequest.getId());

        partialUpdatedBookRentingRequest.status(UPDATED_STATUS).rentedAt(UPDATED_RENTED_AT).rentedUntil(UPDATED_RENTED_UNTIL);

        restBookRentingRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookRentingRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookRentingRequest))
            )
            .andExpect(status().isOk());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeUpdate);
        BookRentingRequest testBookRentingRequest = bookRentingRequestList.get(bookRentingRequestList.size() - 1);
        assertThat(testBookRentingRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBookRentingRequest.getRentedAt()).isEqualTo(UPDATED_RENTED_AT);
        assertThat(testBookRentingRequest.getRentedUntil()).isEqualTo(UPDATED_RENTED_UNTIL);
    }

    @Test
    @Transactional
    void fullUpdateBookRentingRequestWithPatch() throws Exception {
        // Initialize the database
        bookRentingRequestRepository.saveAndFlush(bookRentingRequest);

        int databaseSizeBeforeUpdate = bookRentingRequestRepository.findAll().size();

        // Update the bookRentingRequest using partial update
        BookRentingRequest partialUpdatedBookRentingRequest = new BookRentingRequest();
        partialUpdatedBookRentingRequest.setId(bookRentingRequest.getId());

        partialUpdatedBookRentingRequest.status(UPDATED_STATUS).rentedAt(UPDATED_RENTED_AT).rentedUntil(UPDATED_RENTED_UNTIL);

        restBookRentingRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookRentingRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookRentingRequest))
            )
            .andExpect(status().isOk());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeUpdate);
        BookRentingRequest testBookRentingRequest = bookRentingRequestList.get(bookRentingRequestList.size() - 1);
        assertThat(testBookRentingRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBookRentingRequest.getRentedAt()).isEqualTo(UPDATED_RENTED_AT);
        assertThat(testBookRentingRequest.getRentedUntil()).isEqualTo(UPDATED_RENTED_UNTIL);
    }

    @Test
    @Transactional
    void patchNonExistingBookRentingRequest() throws Exception {
        int databaseSizeBeforeUpdate = bookRentingRequestRepository.findAll().size();
        bookRentingRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookRentingRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookRentingRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookRentingRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookRentingRequest() throws Exception {
        int databaseSizeBeforeUpdate = bookRentingRequestRepository.findAll().size();
        bookRentingRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookRentingRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookRentingRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookRentingRequest() throws Exception {
        int databaseSizeBeforeUpdate = bookRentingRequestRepository.findAll().size();
        bookRentingRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookRentingRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookRentingRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookRentingRequest in the database
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBookRentingRequest() throws Exception {
        // Initialize the database
        bookRentingRequestRepository.saveAndFlush(bookRentingRequest);

        int databaseSizeBeforeDelete = bookRentingRequestRepository.findAll().size();

        // Delete the bookRentingRequest
        restBookRentingRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookRentingRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookRentingRequest> bookRentingRequestList = bookRentingRequestRepository.findAll();
        assertThat(bookRentingRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
