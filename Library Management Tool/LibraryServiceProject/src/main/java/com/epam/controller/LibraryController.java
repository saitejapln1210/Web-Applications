package com.epam.controller;
import com.epam.dto.LibraryDto;
import com.epam.exceptions.UserBookLimitReachedException;
import com.epam.exceptions.UserHasAlreadyBookException;
import com.epam.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;
    @PostMapping(value = "/users/{username}/books/{book-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LibraryDto> issueBook(@PathVariable(value = "username") String username,@PathVariable(value = "book-id") int bookId) throws UserBookLimitReachedException, UserHasAlreadyBookException {
        return new ResponseEntity<>(libraryService.issueBook(username,bookId), HttpStatus.OK);
    }
    @DeleteMapping(value = "/users/{username}/books/{book-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBook(@PathVariable(value = "username") String username,@PathVariable(value = "book-id") int bookId) {
        libraryService.deleteBook(username,bookId);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }
}
