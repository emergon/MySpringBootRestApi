package emergon.controller;

import emergon.entity.Book;
import emergon.repository.BookRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {
    
    @Autowired
    private BookRepo bookRepo;
    
    // All books @GetMapping("/books")
    // Get book by id @GetMapping("/books/{id}")
    // Save Book @PostMapping("/books")
    
    // Get Books by Author id @GetMapping("/authors/{authorId}/books")
    @GetMapping("/authors/{authorId}/books")
    public List<Book> getBooksByAuthor(@PathVariable(value = "authorId") int authorId){
        List<Book> books = bookRepo.findByAuthorId(authorId);
        return books;
    }
    // Update a Book of an Author id @PutMapping("/authors/{authorId}/books/{book_id}")
    // Delete a Book from an Author id @DeleteMapping("/authors/{authorId}/books/{book_id}")
}
