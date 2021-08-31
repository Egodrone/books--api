package nexergroup.com.books_api.service;


import nexergroup.com.books_api.dto.BookDto;
import nexergroup.com.books_api.entity.Book;
import nexergroup.com.books_api.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    ModelMapper modelMapper;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Description: Creates a new book.
     */
    @Override
    public BookDto create(BookDto bookDto) {
        if (bookDto == null) throw new IllegalArgumentException("BookDto should not be null");
        if (bookDto.getId().isEmpty()) throw new IllegalArgumentException("BookDto ID can't be empty");
        bookDto.setId(bookDto.getId().toUpperCase(Locale.ROOT));

        Book bookEntity = modelMapper.map(bookDto, Book.class);
        Book savedBookEntity = bookRepository.save(bookEntity);

        return modelMapper.map(savedBookEntity, BookDto.class);
    }

    /**
     * Creates books from the JSON file located in the books package when application starts
     */
    @Override
    public void createAll(List<BookDto> bookDtos) {
        List<Book> bookObj = bookDtos.stream().map(bookDto -> modelMapper.map(bookDto,Book.class)).collect(Collectors.toList());
        bookRepository.saveAll(bookObj);
    }

    /**
     * Description: Finds all available books
     * @return List<BookDto>
     */
    @Override
    public List<BookDto> findAll() {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);

        return bookList.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findAllSortByContainingAuthor(String author) {
        List<BookDto> allBooks = findAll();
        List<BookDto> sortedResult = new ArrayList<>();

        for(BookDto e: allBooks) {
            if(e.getAuthor().toLowerCase(Locale.ROOT).contains(author.toLowerCase(Locale.ROOT))) {
                sortedResult.add(e);
            }
        }

        Collections.sort(sortedResult, Comparator.comparing(BookDto::getAuthor));

        return sortedResult;
    }

    @Override
    public List<BookDto> findAllSortedByContainingTitle(String title) {
        List<BookDto> allBooks = findAll();
        List<BookDto> sortedTitle = new ArrayList<>();

        for(BookDto e: allBooks) {
            if(e.getTitle().toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT))) {
                sortedTitle.add(e);
            }
        }

        if(sortedTitle.size() >= 1 ) {
            Collections.sort(sortedTitle, Comparator.comparing(BookDto::getTitle));
        }

        return sortedTitle;
    }

    @Override
    public List<BookDto> findAllSortedByContainingGenre(String genre) {
        List<BookDto> allBooks = findAll();
        List<BookDto> sortedByContainingGenre = new ArrayList<>();

        for(BookDto e: allBooks) {
            if(e.getGenre().toLowerCase(Locale.ROOT).contains(genre.toLowerCase(Locale.ROOT))) {
                sortedByContainingGenre.add(e);
            }
        }

        Collections.sort(sortedByContainingGenre, Comparator.comparing(BookDto::getGenre));

        return sortedByContainingGenre;
    }

    @Override
    public List<BookDto> findAllSortedByContainingYear(String year) {

        List<BookDto> allBooks = findAll();
        List<BookDto> sortedByContainingYear = new ArrayList<>();

        for(BookDto e: allBooks) {
            if(e.getPublish_date().toString().toLowerCase(Locale.ROOT).contains(year.toLowerCase(Locale.ROOT))) {
                sortedByContainingYear.add(e);
            }
        }

        Collections.sort(sortedByContainingYear, Comparator.comparing(BookDto::getPublish_date));

        return sortedByContainingYear;
    }

    @Override
    public List<BookDto> findAllSortedByContainingYearAndMonth(String year, String month) {
        List<BookDto> allBooks = findAll();
        List<BookDto> sortedByContainingYearAndMonth = new ArrayList<>();

        for(BookDto e: allBooks) {
            if(e.getPublish_date().toString().contains(year.concat("-" + month))) {
                sortedByContainingYearAndMonth.add(e);
            }
        }

        Collections.sort(sortedByContainingYearAndMonth, Comparator.comparing(BookDto::getPublish_date));

        return sortedByContainingYearAndMonth;
    }

    @Override
    public List<BookDto> findAllSortedByContainingYearMonthAndDays(String year, String month, String days) {
        List<BookDto> allBooks = findAll();
        List<BookDto> sortedByContainingYearMonthAndDay = new ArrayList<>();

        for(BookDto e: allBooks) {
            if(e.getPublish_date().toString()
                    .contains(year.concat("-" + month + "-" + days))) {
                sortedByContainingYearMonthAndDay.add(e);
            }
        }

        Collections.sort(sortedByContainingYearMonthAndDay, Comparator.comparing(BookDto::getPublish_date));

        return sortedByContainingYearMonthAndDay;
    }

    @Override
    public List<BookDto> findAllSortedByContainingDescription(String description) {
        List<BookDto> allBooks = findAll();
        List<BookDto> sortedByDescription = new ArrayList<>();

        for(BookDto e: allBooks) {
            if(e.getDescription().toLowerCase(Locale.ROOT).contains(description.toLowerCase(Locale.ROOT))) {
                sortedByDescription.add(e);
            }
        }

        Collections.sort(sortedByDescription, Comparator.comparing(BookDto::getDescription));

        return sortedByDescription;
    }

    @Override
    public boolean delete(String bookId) {
        if (bookId.length() < 1) throw new IllegalArgumentException("Invalid book id");
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            bookRepository.delete(bookOptional.get());

            return true;
        }

        return false;
    }

    @Override
    public BookDto update(BookDto bookDto) {
        if (bookDto == null) throw new IllegalArgumentException("BookDto should not be null");
        if (bookDto.getId().isEmpty()) throw new IndexOutOfBoundsException("Invalid id number");

        Optional<Book> bookOptional = bookRepository.findById(bookDto.getId());
        if (bookOptional.isPresent()) {
            return modelMapper.map(bookRepository.save(modelMapper.map(bookDto, Book.class)), BookDto.class);
        }

        return null;
    }

    @Override
    public List<BookDto> findAllOrderById() {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);

        return bookList.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findAllSortByContainingId(String id) {
        List<BookDto> allBooks = findAll();
        List<BookDto> sortedId = new ArrayList<>();

        for(BookDto e: allBooks) {
            if(e.getId().toLowerCase(Locale.ROOT).contains(id.toLowerCase(Locale.ROOT))) {
                sortedId.add(e);
            }
        }

        if(sortedId.size() >= 1 ) {
            Collections.sort(sortedId, Comparator.comparing(BookDto::getId));
        }

        return sortedId;
    }

    @Override
    public List<BookDto> findAllSortedByContainingPrice(String price) {
        List<BookDto> allBooks = findAll();
        List<BookDto> equalPriceBooks = new ArrayList<>();

        boolean stringParsed = false;
        double valueDouble = 0.0;
        double valueDoubleSecond = 0.0;

        try {
            valueDouble = Double.parseDouble(price);
            stringParsed = true;
        } catch(Exception e) {
            System.out.println("Couldn't parse string to double");
        }
        boolean priceExists = false;

        if(!price.isEmpty() && stringParsed) {
            for(BookDto e: allBooks) {
                if (e.getPrice() == valueDouble) {
                    equalPriceBooks.add(e);
                    priceExists = true;
                }
            }
        }

        if(price.contains("&")) {
            String[] arrOfStr = price.split("&");
            if(arrOfStr.length == 2) {
                try {
                    valueDouble = Double.parseDouble(arrOfStr[0]);
                    valueDoubleSecond = Double.parseDouble(arrOfStr[1]);
                    stringParsed = true;
                } catch(Exception e) {
                    System.out.println("Couldn't parseDouble");
                }
            }

            if(stringParsed) {
                for(BookDto e: allBooks) {
                    if (e.getPrice() >= valueDouble && e.getPrice() <= valueDoubleSecond) {
                        equalPriceBooks.add(e);
                        priceExists = true;
                    }
                }
            }
        }

        if (priceExists) {
            Collections.sort(equalPriceBooks, Comparator.comparing(BookDto::getPrice));
            return equalPriceBooks;
        } else {
            return null;
        }
    }
}
