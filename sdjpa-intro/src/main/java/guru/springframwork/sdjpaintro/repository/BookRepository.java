package guru.springframwork.sdjpaintro.repository;

import guru.springframwork.sdjpaintro.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
