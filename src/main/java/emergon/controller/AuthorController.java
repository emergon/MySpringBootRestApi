package emergon.controller;

import emergon.entity.Author;
import emergon.repository.AuthorRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // https://www.baeldung.com/spring-controller-vs-restcontroller
@RequestMapping("/api/authors")
public class AuthorController {
    
    @Autowired
    private AuthorRepo authorRepo;
    
    //findAll
    //@ResponseBody //https://www.baeldung.com/spring-request-response-body
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Author> findAll(){
        return authorRepo.findAll();
    }
    
    //findById
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Author> getAuthorById(@PathVariable(value = "id") int id){
        Optional<Author> optionalAuthor = authorRepo.findById(id);
        if(!optionalAuthor.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Author author = optionalAuthor.get();
        ResponseEntity responseEntity = new ResponseEntity(author, HttpStatus.OK);
        return responseEntity;
    }
    
    //create
    @PostMapping
    public Author create(@RequestBody Author author){
        Author savedAuthor = authorRepo.save(author);
        return savedAuthor;
    }
    
    //update
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@RequestBody Author author, @PathVariable(value = "id") int id){
        //find author to update
        Author authorToUpdate = authorRepo.findById(id).get();
        //change author details
        authorToUpdate.setName(author.getName());
        //save author to DB
        authorToUpdate = authorRepo.save(authorToUpdate);
        //return a response
        return ResponseEntity.status(HttpStatus.CREATED).body(authorToUpdate);
    }
    
    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") int id){
        Author authorToDelete = authorRepo.findById(id).get();
        authorRepo.delete(authorToDelete);
        String message = "Author with id:"+id+" successfully deleted!";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
