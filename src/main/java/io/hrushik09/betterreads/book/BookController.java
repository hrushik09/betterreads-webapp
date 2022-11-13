package io.hrushik09.betterreads.book;

import io.hrushik09.betterreads.userbooks.UserBooks;
import io.hrushik09.betterreads.userbooks.UserBooksPrimaryKey;
import io.hrushik09.betterreads.userbooks.UserBooksRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@AllArgsConstructor
@Controller
public class BookController {
    BookRepository bookRepository;
    UserBooksRepository userBooksRepository;

    @GetMapping("/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            String coverImageUrl = "/images/no-image.png";
            Book book = optionalBook.get();
            if (book.getCoverIds() != null && book.getCoverIds().size() > 0) {
                String COVER_IMAGE_ROOT = "https://covers.openlibrary.org/b/id/";
                coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-L.jpg";
            }
            model.addAttribute("coverImage", coverImageUrl);
            model.addAttribute("book", book);

            if (principal != null &&
                    (principal.getAttribute("name") != null || principal.getAttribute("login") != null)) {
                String userId = principal.getAttribute("login");
                if (userId == null) {
                    userId = principal.getAttribute("name");
                }

                model.addAttribute("loginId", userId);
                UserBooksPrimaryKey key = new UserBooksPrimaryKey();
                key.setBookId(bookId);
                key.setUserId(userId);
                Optional<UserBooks> userBooks = userBooksRepository.findById(key);
                if (userBooks.isPresent()) {
                    model.addAttribute("userBooks", userBooks.get());
                } else {
                    model.addAttribute("userBooks", new UserBooks());
                }
            }
            return "book";
        }
        return "book-not-found";
    }
}
