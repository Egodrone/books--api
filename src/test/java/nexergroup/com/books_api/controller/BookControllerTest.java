package nexergroup.com.books_api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nexergroup.com.books_api.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Book book;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        book = new Book();

        book.setId("B14");
        book.setAuthor("Kutner, Joe");
        book.setTitle("Deploying with JRuby");
        book.setGenre("Computer");
        book.setPrice(33.00);
        LocalDate localDate = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse("2012-08-15"));
        book.setPublish_date(localDate);
        book.setDescription("Deploying with JRuby is the missing link between enjoying JRuby and using it in the real world to build high-performance, scalable applications.");
    }

    @DisplayName("Save the book")
    @Test
    public void save_book() throws Exception {
        String bookJsonMessage = objectMapper.writeValueAsString(book);

        MvcResult mvcResult = mockMvc.perform(post("/api/books")
                .content(bookJsonMessage)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201, status);
    }

    @DisplayName("Find all books")
    @Test
    public void find_all_books() throws Exception {
        String bookJsonMessage = objectMapper.writeValueAsString(book);

        mockMvc.perform(post("/api/books")
                .content(bookJsonMessage)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/api/books")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse status = mvcResult.getResponse();
        Assertions.assertEquals(200, status.getStatus());
    }

    @DisplayName("Search books by the author")
    @Test
    public void search_author() throws Exception {
        String bookJsonMessage = objectMapper.writeValueAsString(book);

        mockMvc.perform(post("/api/books")
                .content(bookJsonMessage)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/api/books/author/kut")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse status = mvcResult.getResponse();
        Assertions.assertEquals(200, status.getStatus());

        System.out.println(status.getContentAsString());
    }


}
