package nexergroup.com.books_api.controller;
import nexergroup.com.books_api.dto.BookDto;
import nexergroup.com.books_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/api/books")
public class BookController {
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    /**
     * Display all books
     *
     * */
    @GetMapping
    public ResponseEntity<List<BookDto>> findAll() {
        List<BookDto> result = bookService.findAll();

        return ResponseEntity.ok(result);
    }


    /**
     * Add a new book
     *
     * */
    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {
        if (bookDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        BookDto result = bookService.create(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    /**
     * Display all books, sorted by id
     *
     * http://localhost:8080/api/books/id returns all sorted by id (B1-B13)
     * */
    @GetMapping("/id")
    public ResponseEntity<List<BookDto>> findAllSortByID() {
        List<BookDto> result = bookService.findAllOrderById();
        Collections.sort(result, Comparator.comparing(BookDto::getId));

        return ResponseEntity.ok(result);
    }


    /**
     * Display all books containing given id parameter, sorted by id
     *
     * http://localhost:8080/api/books/id/1 returns all with id containing '1' sorted by id (B1, B10-13)
     * http://localhost:8080/api/books/id/b returns all with id containing 'b' sorted by id (B1-B13)
     * */
    @GetMapping("/id/{value}")
    public ResponseEntity<List<BookDto>> findAllSortByContainingId(@PathVariable("value") String value) {
        List<BookDto> sortedId = bookService.findAllSortByContainingId(value);

        return (sortedId.size() >= 1 ? ResponseEntity.ok(sortedId) : null);
    }


    /**
     * Display all of the books sorted by the author
     *
     * http://localhost:8080/api/books/author returns all sorted by author (B1-B13)
     * */
    @GetMapping("/author")
    public ResponseEntity<List<BookDto>> findAllSortByAuthor() {
        List<BookDto> result = bookService.findAll();
        Collections.sort(result, Comparator.comparing(BookDto::getAuthor));

        return ResponseEntity.ok(result);
    }


    /**
     * Search by containing the author, sorted by author
     *
     * http://localhost:8080/api/books/author/joe returns all with author containing 'joe' sorted by author (B1)
     * http://localhost:8080/api/books/author/kut returns all with author containing 'kut' sorted by author (B1)
     * */
    @GetMapping("/author/{value}")
    public ResponseEntity<List<BookDto>> findAllSortByContainingAuthor(@PathVariable("value") String value) {

        List<BookDto> sortedResult = bookService.findAllSortByContainingAuthor(value);

        return (sortedResult.size() >= 1 ? ResponseEntity.ok(sortedResult) : null);
    }


    /**
    * Display all of the books sorted by title
    *
    * http://localhost:8080/api/books/title returns all sorted by title (B1-B13)
    * */
    @GetMapping("/title")
    public ResponseEntity<List<BookDto>> findAllSortedByTitle() {
        List<BookDto> result = bookService.findAll();
        Collections.sort(result, Comparator.comparing(BookDto::getTitle));

        return ResponseEntity.ok(result);
    }

    /**
     * Search by the containing title of the book, sorted by title
     *
     * http://localhost:8080/api/books/title/deploy returns all with title containing 'deploy' sorted by title (B1)
     * http://localhost:8080/api/books/title/jruby returns all with title containing 'jruby' sorted by title (B1)
     */
    @GetMapping("/title/{value}")
    public ResponseEntity<List<BookDto>> findAllSortedByContainingTitle(@PathVariable("value") String value) {
        List<BookDto> sortedTitle = bookService.findAllSortedByContainingTitle(value);

        return (sortedTitle.size() >= 1 ? ResponseEntity.ok(sortedTitle) : null);
    }


    /**
     * Display all of the books sorted by Genre
     *
     * https://localhost:8080/api/books/genre returns all sorted by genre (B1-B13)
     * */
    @GetMapping("/genre")
    public ResponseEntity<List<BookDto>> findAllSortedByGenre() {
        List<BookDto> result = bookService.findAll();
        Collections.sort(result, Comparator.comparing(BookDto::getGenre));

        return ResponseEntity.ok(result);
    }

    /**
     * Search by containing genre, sorted by genre
     *
     * https://localhost:8080/api/books/genre/com returns all with genre containing 'com' sorted by genre (B1, B10-13)
     * https://localhost:8080/api/books/genre/ter returns all with genre containing 'ter' sorted by genre (B1, B10-13)
     */
    @GetMapping("/genre/{value}")
    public ResponseEntity<List<BookDto>> findAllSortedByContainingGenre(@PathVariable("value") String value) {
        List<BookDto> sortedByContainingGenre = bookService.findAllSortedByContainingGenre(value);

        return (sortedByContainingGenre.size() >= 1 ? ResponseEntity.ok(sortedByContainingGenre) : null);
    }

    /**
     * All books sorted by price
     *
     * https://localhost:8080/api/books/price returns all sorted by price (B1-B13)
     * */
    @GetMapping("/price")
    public ResponseEntity<List<BookDto>> findAllSortedByPrice() {
        List<BookDto> result = bookService.findAll();
        Collections.sort(result, Comparator.comparing(BookDto::getPrice));

        return ResponseEntity.ok(result);
    }

    /**
     * Search for a specific price och range between two prices, sorted by price
     *
     * https://localhost:8080/api/books/price/33.0 returns all with price '33.0' (B1)
     * https://localhost:8080/api/books/price/30.0&35.0 returns all with price between '30.0' och '35.0' sorted by price (B1, B11)
     */
    @GetMapping("/price/{value}")
    public ResponseEntity<List<BookDto>> findAllSortedByContainingPrice(@PathVariable("value") String value) {
        List<BookDto> equalPriceBooks = bookService.findAllSortedByContainingPrice(value);

        return ResponseEntity.ok(equalPriceBooks);
    }

    /**
     * All books sorted by published date
     *
     * https://localhost:8080/api/books/published returns all sorted by published_date (B1-B13)
     * */
    @GetMapping("/published")
    public ResponseEntity<List<BookDto>> findAllSortedByPublishedDate() {
        List<BookDto> result = bookService.findAll();
        Collections.sort(result, Comparator.comparing(BookDto::getPublish_date));

        return ResponseEntity.ok(result);
    }

    /**
     * Search for a published year, containing the year and sorted by published_date
     *
     * https://localhost:8080/api/books/published/2012 returns all from '2012' sorted by published_date (B13, B1)
     */
    @GetMapping("/published/{value}")
    public ResponseEntity<List<BookDto>> findAllSortedByContainingYear(@PathVariable("value") String value) {
        List<BookDto> sortedByContainingYear = bookService.findAllSortedByContainingYear(value);

        return (sortedByContainingYear.size() >= 1 ? ResponseEntity.ok(sortedByContainingYear) : null);
    }

    /**
    * Search by containing published year and month sorted by published_date
    *
    * https://localhost:8080/api/books/published/2012/8 returns all from '2012-08' sorted by published_date (B1)
    * */
    @GetMapping("/published/{year}/{month}")
    public ResponseEntity<List<BookDto>> findAllSortedByContainingYearAndMonth(@PathVariable("year") String year,
                                                                               @PathVariable("month") String month) {

        List<BookDto> sortedByContainingYearAndMonth = bookService.findAllSortedByContainingYearAndMonth(year, month);

        return (sortedByContainingYearAndMonth.size() >= 1 ? ResponseEntity.ok(sortedByContainingYearAndMonth) : null);
    }

    /**
     * Search for a published year, month and day sorted by published_date
     *
     * https://localhost:8080/api/books/published/2012/8/15 returns all from '2012-08-15' sorted by published_date (B1)
     * */
    @GetMapping("/published/{year}/{month}/{days}")
    public ResponseEntity<List<BookDto>> findAllSortedByContainingYearMonthAndDays(@PathVariable("year") String year,
                                                                               @PathVariable("month") String month,
                                                                               @PathVariable("days") String days) {

        List<BookDto> sortedByContainingYearMonthAndDay = bookService.findAllSortedByContainingYearMonthAndDays(year, month, days);

        return (sortedByContainingYearMonthAndDay.size() >= 1 ? ResponseEntity.ok(sortedByContainingYearMonthAndDay) : null);
    }

    /**
     * All books sorted by description
     *
     * https://localhost:8080/api/books/description returns all sorted by description (B1-B13)
     * */
    @GetMapping("/description")
    public ResponseEntity<List<BookDto>> findAllSortedByDescription() {
        List<BookDto> result = bookService.findAll();
        Collections.sort(result, Comparator.comparing(BookDto::getDescription));

        return ResponseEntity.ok(result);
    }

    /**
     * Search for a result containing description, sorted by description
     *
     * http://localhost:8080/api/books/description/deploy returns all with description containing 'deploy' sorted by description (B1, B13)
     * http://localhost:8080/api/books/description/applications returns all with description containing 'applications' sorted by description (B1)
     */
    @GetMapping("/description/{value}")
    public ResponseEntity<List<BookDto>> findAllSortedByContainingDescription(@PathVariable("value") String value) {
        List<BookDto> sortedByDescription = bookService.findAllSortedByContainingDescription(value);

        return (sortedByDescription.size() >= 1 ? ResponseEntity.ok(sortedByDescription) : null);
    }

    /**
     *
     * Delete book by id
     *
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        if (id.length() < 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        boolean result = bookService.delete(id.toUpperCase(Locale.ROOT));
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     *  Update the book by id
     */
    @PutMapping
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto) {
        if (bookDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        BookDto result = bookService.update(bookDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
