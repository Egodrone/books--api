package nexergroup.com.books_api.repository;


import nexergroup.com.books_api.entity.Book;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface BookRepository extends CrudRepository<Book, String> {

    Optional<Book> findById(String id);
}
