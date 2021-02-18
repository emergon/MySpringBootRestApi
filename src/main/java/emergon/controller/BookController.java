package emergon.controller;

import emergon.entity.Author;
import emergon.entity.Book;
import emergon.repository.AuthorRepo;
import emergon.repository.BookRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private AuthorRepo authorRepo;

    // All books @GetMapping("/books")
    @GetMapping
    public List<Book> findAll() {
        return bookRepo.findAll();
    }
    // Get book by id @GetMapping("/books/{id}")
    // Save Book @PostMapping("/books")
//    @PostMapping
//    public ResponseEntity<Book> create(@RequestBody Book book){
//        book = bookRepo.save(book);
//        return ResponseEntity.status(HttpStatus.CREATED).body(book);
//    }

    // Save a Book to a specific Author
    @PostMapping("/authors/{authorId}")
    public ResponseEntity<Book> createBookByAuthor(@RequestBody Book book,
            @PathVariable int authorId) {
        Author author = authorRepo.findById(authorId).get();
        book.setAuthor(author);
        book = bookRepo.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // Get Books by Author id 
    @GetMapping("/authors/{authorId}")
    public List<Book> getBooksByAuthor(@PathVariable(value = "authorId") int authorId) {
        List<Book> books = bookRepo.findByAuthorId(authorId);
        return books;
    }

    // Update a Book of an Author id @PutMapping("/authors/{authorId}/books/{book_id}")
    @PutMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book bookDetails,
            @PathVariable int bookId,
            @PathVariable int authorId) {
        boolean authorExists = authorRepo.existsById(authorId);
        if (!authorExists) {
            return ResponseEntity.notFound().build();
        }
        Book book = bookRepo.findById(bookId).get();
        book.setTitle(bookDetails.getTitle());
        book = bookRepo.save(book);
        return ResponseEntity.status(HttpStatus.OK).body(book);

    }

    // Delete a Book from an Author id @DeleteMapping("/authors/{authorId}/books/{book_id}")
    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<String> deleteBookOfAuthor(
            @PathVariable int bookId,
            @PathVariable int authorId){
        
        //find book with id=bookId and author_id=authorId
        Optional<Book> optionalBook = bookRepo.findByIdAndAuthorId(bookId, authorId);
        if(!optionalBook.isPresent()){//if book !exists return NOT_FOUND
            return ResponseEntity.notFound().build();
        }else{//else delete book
            bookRepo.delete(optionalBook.get());
            return ResponseEntity.ok("Book deleted");
        }
    }
}
