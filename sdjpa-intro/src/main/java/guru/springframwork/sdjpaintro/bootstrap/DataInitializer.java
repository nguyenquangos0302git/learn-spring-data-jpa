package guru.springframwork.sdjpaintro.bootstrap;

import guru.springframwork.sdjpaintro.domain.Book;
import guru.springframwork.sdjpaintro.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Book bookDDD = new Book("Domain Driven Design", "1234", "RandomHouse");
        Book bookSIA = new Book("Spring In Action", "1234", "Oriely");
        bookRepository.save(bookDDD);
        bookRepository.save(bookSIA);

        bookRepository.findAll().forEach(book -> {
            System.out.println(book.getId());
            System.out.println(book.getTitle());
        });

    }
}
