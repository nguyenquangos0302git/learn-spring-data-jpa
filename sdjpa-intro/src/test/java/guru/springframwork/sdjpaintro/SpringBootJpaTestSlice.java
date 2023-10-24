package guru.springframwork.sdjpaintro;

import guru.springframwork.sdjpaintro.domain.Book;
import guru.springframwork.sdjpaintro.repository.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframwork.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringBootJpaTestSlice {

    @Autowired
    BookRepository bookRepository;

//    @Rollback(value = false)
    @Commit
    @Order(1)
    @Test
    void testJapSplce() {
//        bookRepository.save(new Book("My Book", "1234", "Self"));
        long count = bookRepository.count();
        assertThat(count).isGreaterThan(0);
    }

    @Order(2)
    @Test
    void testJapSplceTransacton() {
        long count = bookRepository.count();
        assertThat(count).isGreaterThan(0);
    }

}
