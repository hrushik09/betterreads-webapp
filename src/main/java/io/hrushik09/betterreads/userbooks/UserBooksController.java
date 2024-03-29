package io.hrushik09.betterreads.userbooks;

import io.hrushik09.betterreads.book.Book;
import io.hrushik09.betterreads.book.BookRepository;
import io.hrushik09.betterreads.user.BooksByUser;
import io.hrushik09.betterreads.user.BooksByUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class UserBooksController {
    UserBooksRepository userBooksRepository;
    BooksByUserRepository booksByUserRepository;
    BookRepository bookRepository;

    @PostMapping("/addUserBook")
    public ModelAndView addBookForUser(@RequestBody MultiValueMap<String, String> formData, @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null ||
                (principal.getAttribute("name") == null && principal.getAttribute("login") == null)) {
            return null;
        }
        String bookId = formData.getFirst("bookId");
        Optional<Book> optionalBook = bookRepository.findById(Objects.requireNonNull(bookId));
        if (optionalBook.isEmpty()) {
            return new ModelAndView("redirect:/");
        }

        Book book = optionalBook.get();
        UserBooks userBooks = new UserBooks();
        UserBooksPrimaryKey key = new UserBooksPrimaryKey();
        String userId = principal.getAttribute("login");
        if (userId == null) {
            userId = principal.getAttribute("name");
        }
        key.setUserId(userId);
        key.setBookId(bookId);
        userBooks.setKey(key);

        int rating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("rating")));
        String readingStatus = formData.getFirst("readingStatus");
        userBooks.setStartedDate(LocalDate.parse(Objects.requireNonNull(formData.getFirst("startDate"))));
        userBooks.setCompletedDate(LocalDate.parse(Objects.requireNonNull(formData.getFirst("completedDate"))));
        userBooks.setRating(rating);
        userBooks.setReadingStatus(readingStatus);
        userBooksRepository.save(userBooks);

        BooksByUser booksByUser = new BooksByUser();
        booksByUser.setId(userId);
        booksByUser.setBookId(bookId);
        booksByUser.setBookName(book.getName());
        booksByUser.setCoverIds(book.getCoverIds());
        booksByUser.setAuthorNames(book.getAuthorNames());
        booksByUser.setReadingStatus(readingStatus);
        booksByUser.setRating(rating);
        booksByUserRepository.save(booksByUser);

        return new ModelAndView("redirect:/books/" + bookId);
    }
}
