package nexergroup.com.books_api.service;

import nexergroup.com.books_api.dto.BookDto;
import java.util.List;


public interface BookService {
    BookDto create(BookDto bookDto);
    void createAll(List<BookDto> bookDtos);
    boolean delete(String bookId);
    BookDto update(BookDto bookDto);
    List<BookDto> findAllOrderById();
    List<BookDto> findAllSortByContainingId(String id);
    List<BookDto> findAll();
    List<BookDto> findAllSortByContainingAuthor(String author);
    List<BookDto> findAllSortedByContainingTitle(String title);
    List<BookDto> findAllSortedByContainingGenre(String genre);
    List<BookDto> findAllSortedByContainingYear(String year);
    List<BookDto> findAllSortedByContainingYearAndMonth(String year, String month);
    List<BookDto> findAllSortedByContainingYearMonthAndDays(String year, String month, String days);
    List<BookDto> findAllSortedByContainingDescription(String description);
    List<BookDto> findAllSortedByContainingPrice(String price);
}
