package nexergroup.com.books_api.dto;

import lombok.Data;
import java.time.LocalDate;


@Data
public class BookDto {
    private String id;
    private String author;
    private String title;
    private String genre;
    private LocalDate publish_date;
    private double price;
    private String description;
}
