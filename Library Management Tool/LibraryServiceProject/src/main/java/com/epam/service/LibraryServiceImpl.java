package com.epam.service;
import com.epam.clients.BookClient;
import com.epam.clients.UserClient;
import com.epam.dto.BookDto;
import com.epam.dto.LibraryDto;
import com.epam.dto.UserDto;
import com.epam.dto.UserProfile;
import com.epam.entity.Library;
import com.epam.exceptions.UserBookLimitReachedException;
import com.epam.exceptions.UserHasAlreadyBookException;
import com.epam.repository.LibraryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private UserClient userClient;
    @Autowired
    private BookClient bookClient;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public LibraryDto issueBook(String username, int bookId) throws UserBookLimitReachedException, UserHasAlreadyBookException {
        Library library = new Library(username, bookId);
        if(isUserExist(username) && isBookExist(bookId) && isUserInLimit(username) && !isUserAlreadyHasBook(username,bookId) ){
            libraryRepository.save(library);
        }
        return getDtoFromLibrary(library);
    }
    @Override
    public LibraryDto getDtoFromLibrary(Library library) {
        return modelMapper.map(library, LibraryDto.class);
    }
    @Override
    public boolean isUserExist(String username){
        ResponseEntity<UserDto> userMsg = userClient.getUserByUsername(username);
        UserDto userDto = userMsg.getBody();
        return Objects.nonNull(userDto);
    }
    @Override
    public boolean isBookExist(int bookId){
        ResponseEntity<BookDto> msg = bookClient.getBookById(bookId);
        BookDto bookDto = msg.getBody();
        return Objects.nonNull(bookDto);
    }
    @Override
    public UserProfile getUserProfile(String username) {
        UserProfile userProfile = new UserProfile();
        if(isUserExist(username)){
            List<Library> libraryList = getUserBooks(username);
            userProfile.setUsername(username);
            List<BookDto> bookDtoList = new ArrayList<>();
            for (Library library:libraryList) {
                ResponseEntity<BookDto> msg = bookClient.getBookById(library.getBookId());
                BookDto bookDto = msg.getBody();
                bookDtoList.add(bookDto);
            }
            userProfile.setBooks(bookDtoList);
        }
        return userProfile;
    }
    @Override
    public List<Library> getUserBooks(String username) {
        return libraryRepository.findAllByUsername(username);
    }
    @Override
    public boolean isUserInLimit(String username) throws UserBookLimitReachedException {
        List<Library> libraryList = getUserBooks(username);
        if(libraryList.size() >= 3)
            throw new UserBookLimitReachedException("user cannot have more than three book");
        else
            return true;
    }
    @Override
    public void deleteAllBook(String username) {
        if (isUserExist(username)) {
            libraryRepository.deleteAllBook(username);
        }
    }
    @Override
    public void deleteBook(String username, int bookId) {
        if (isUserExist(username) && isBookExist(bookId)) {
            libraryRepository.deleteBook(username,bookId);
        }
    }
    @Override
    public boolean isUserAlreadyHasBook(String username, int bookId) throws UserHasAlreadyBookException {
        List<Library> libraryList = getUserBooks(username);
        for (Library library:libraryList) {
            if(library.getBookId()==bookId){
                throw new UserHasAlreadyBookException(username+" already have this book.");
            }
        }
        return false;
    }
}
