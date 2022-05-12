package ee.ut.library.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ee.ut.library.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookRentingRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookRentingRequest.class);
        BookRentingRequest bookRentingRequest1 = new BookRentingRequest();
        bookRentingRequest1.setId(1L);
        BookRentingRequest bookRentingRequest2 = new BookRentingRequest();
        bookRentingRequest2.setId(bookRentingRequest1.getId());
        assertThat(bookRentingRequest1).isEqualTo(bookRentingRequest2);
        bookRentingRequest2.setId(2L);
        assertThat(bookRentingRequest1).isNotEqualTo(bookRentingRequest2);
        bookRentingRequest1.setId(null);
        assertThat(bookRentingRequest1).isNotEqualTo(bookRentingRequest2);
    }
}
