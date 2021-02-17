package emergon.controller;

import emergon.entity.Author;
import emergon.repository.AuthorRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // https://www.baeldung.com/spring-controller-vs-restcontroller
@RequestMapping("/api/authors")
public class AuthorController {
    
    @Autowired
    private AuthorRepo authorRepo;
    
    //findAll
    //@ResponseBody //https://www.baeldung.com/spring-request-response-body
    @GetMapping
    public List<Author> findAll(){
        return authorRepo.findAll();
    }
    
    //findById
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable(value = "id") int id){
        Optional<Author> optionalAuthor = authorRepo.findById(id);
        Author author = optionalAuthor.get();
        ResponseEntity responseEntity = new ResponseEntity(author, HttpStatus.OK);
        return responseEntity;
    }
    
    //create
    
    //update
    
    //delete
}
