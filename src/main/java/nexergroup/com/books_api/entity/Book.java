package nexergroup.com.books_api.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
public class Book {
    @Id
    @Column(nullable = false, length = 200)
    private String id;

    @Column(nullable = false, length = 200)
    private String author;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 200)
    private String genre;

    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private LocalDate publish_date;

    @Column(nullable = false, length = 10)
    private double price;

    @Column(nullable = false, length = 2000)
    private String description;
}
