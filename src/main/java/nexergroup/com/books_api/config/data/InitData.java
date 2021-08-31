package nexergroup.com.books_api.config.data;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nexergroup.com.books_api.dto.BookDto;
import nexergroup.com.books_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;


/**
 *  Load default file containing books in the JSON format and save them to the database
 */
@Component
public class InitData implements CommandLineRunner {
    BookService bookService;

    @Autowired
    public InitData(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        List<BookDto> bookDtoList = objectMapper.readValue(
                new File("src/main/java/nexergroup/com/books_api/books/books.json"),
                new TypeReference<List<BookDto>>() {});

        bookService.createAll(bookDtoList);
    }
}
