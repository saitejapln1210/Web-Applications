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
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
 class LibraryServiceTest {

    @MockBean
    UserClient userClient;
    @Mock
    BookClient bookClient;
    @InjectMocks
    LibraryServiceImpl libraryService;
    @Mock
    LibraryRepository libraryRepository;
    @Mock
    ModelMapper modelMapper;
    @Test
    void issueBookTest() throws UserHasAlreadyBookException, UserBookLimitReachedException {
        LibraryDto libraryDto = new LibraryDto(1,"saiteja",1);
        Library library = new Library("saiteja",1);
        Library library1 = new Library("saiteja",2);
        UserDto userDto=new UserDto("saiteja","saiteja@gmail.com","Sai@sivaram1210");
        BookDto bookDto=new BookDto(1,"Physics","epam","saiteja");
        ResponseEntity<UserDto> userResponseEntity=new ResponseEntity<>(userDto, HttpStatus.OK);
        ResponseEntity<BookDto> bookResponseEntity=new ResponseEntity<>(bookDto, HttpStatus.OK);
        when(userClient.getUserByUsername("saiteja")).thenReturn(userResponseEntity);
        when(bookClient.getBookById(1)).thenReturn(bookResponseEntity);
        List<Library> libraryList = new ArrayList<>();
        libraryList.add(library1);
        when(libraryRepository.findAllByUsername("saiteja")).thenReturn(libraryList);
        libraryService.issueBook(libraryDto.getUsername(),1);
        verify(libraryRepository,times(2)).findAllByUsername("saiteja");
    }

    @org.junit.Test(expected = UserBookLimitReachedException.class)
    public void issueBookTestException() throws UserHasAlreadyBookException, UserBookLimitReachedException {
        LibraryDto libraryDto = new LibraryDto(1,"saiteja",1);
        Library library = new Library("saiteja",1);
        Library library1 = new Library("saiteja",2);
        Library library2 = new Library("saiteja",3);
        Library library3 = new Library("saiteja",4);
        Library library4 = new Library("saiteja",5);
        UserDto userDto=new UserDto("saiteja","saiteja@gmail.com","Sai@sivaram1210");
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        ResponseEntity<UserDto> userResponseEntity=new ResponseEntity<>(userDto, HttpStatus.OK);
        ResponseEntity<BookDto> bookResponseEntity=new ResponseEntity<>(bookDto, HttpStatus.OK);
        when(userClient.getUserByUsername("saiteja")).thenReturn(userResponseEntity);
        when(bookClient.getBookById(1)).thenReturn(bookResponseEntity);
        List<Library> libraryList = new ArrayList<>();
        libraryList.add(library1);
        libraryList.add(library2);
        libraryList.add(library3);
        libraryList.add(library4);
        when(libraryRepository.findAllByUsername("saiteja")).thenReturn(libraryList);
        libraryService.issueBook(libraryDto.getUsername(),1);
    }
    @org.junit.Test(expected = UserHasAlreadyBookException.class)
    public void issueBookTestException1() throws UserHasAlreadyBookException, UserBookLimitReachedException {
        LibraryDto libraryDto = new LibraryDto(1,"saiteja",1);
        Library library = new Library("saiteja",1);
        Library library1 = new Library("saiteja",2);
        UserDto userDto=new UserDto("saiteja","saiteja@gmail.com","Sai@sivaram1210");
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        ResponseEntity<UserDto> userResponseEntity=new ResponseEntity<>(userDto, HttpStatus.OK);
        ResponseEntity<BookDto> bookResponseEntity=new ResponseEntity<>(bookDto, HttpStatus.OK);
        when(userClient.getUserByUsername("saiteja")).thenReturn(userResponseEntity);
        when(bookClient.getBookById(1)).thenReturn(bookResponseEntity);
        List<Library> libraryList = new ArrayList<>();
        libraryList.add(library);
        libraryList.add(library1);
        when(libraryRepository.findAllByUsername("saiteja")).thenReturn(libraryList);
        libraryService.issueBook(libraryDto.getUsername(),1);
    }
    @Test
    void deleteTestBook(){
        LibraryDto libraryDto = new LibraryDto(1,"saiteja",1);
        Library library = new Library("saiteja",1);
        UserDto userDto=new UserDto("saiteja","saiteja@gmail.com","Sai@sivaram1210");
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        ResponseEntity<UserDto> userResponseEntity=new ResponseEntity<>(userDto, HttpStatus.OK);
        ResponseEntity<BookDto> bookResponseEntity=new ResponseEntity<>(bookDto, HttpStatus.OK);
        when(userClient.getUserByUsername("saiteja")).thenReturn(userResponseEntity);
        when(bookClient.getBookById(1)).thenReturn(bookResponseEntity);
        doNothing().when(libraryRepository).deleteBook("saiteja",1);
        libraryService.deleteBook("saiteja",1);
        verify(libraryRepository).deleteBook("saiteja",1);
    }

    @Test
    void deleteTestAllBook(){
        LibraryDto libraryDto = new LibraryDto(1,"saiteja",1);
        Library library = new Library("saiteja",1);
        UserDto userDto=new UserDto("saiteja","saiteja@gmail.com","Sai@sivaram1210");
        ResponseEntity<UserDto> userResponseEntity=new ResponseEntity<>(userDto, HttpStatus.OK);
        when(userClient.getUserByUsername("saiteja")).thenReturn(userResponseEntity);
        doNothing().when(libraryRepository).deleteAllBook("saiteja");
        libraryService.deleteAllBook("saiteja");
        verify(libraryRepository).deleteAllBook("saiteja");
    }

    @Test
    void getUserProfileTest(){
        LibraryDto libraryDto = new LibraryDto(1,"saiteja",1);
        Library library = new Library("saiteja",1);
        library.setId(1);
        List<Library> libraryList = new ArrayList<>();
        libraryList.add(library);
        UserDto userDto=new UserDto("saiteja","saiteja@gmail.com","Sai@sivaram1210");
        ResponseEntity<UserDto> userResponseEntity=new ResponseEntity<>(userDto, HttpStatus.OK);
        when(userClient.getUserByUsername("saiteja")).thenReturn(userResponseEntity);
        when(libraryRepository.findAllByUsername("saiteja")).thenReturn(libraryList);
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername("saiteja");
        List<BookDto> bookDtoList=new ArrayList<>();
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        ResponseEntity<BookDto> bookDtoResponseEntity=new ResponseEntity<>(bookDto, HttpStatus.OK);
        when(bookClient.getBookById(1)).thenReturn(bookDtoResponseEntity);
        bookDtoList.add(bookDto);
        userProfile.setBooks(bookDtoList);
        UserProfile userProfile1 = libraryService.getUserProfile("saiteja");
        Assertions.assertEquals("saiteja",library.getUsername());
        Assertions.assertEquals(1,library.getBookId());
        Assertions.assertEquals(1,library.getId());
    }
   @Test
     void isUserAlreadyHasBookException() throws UserHasAlreadyBookException {
        LibraryDto libraryDto = new LibraryDto(1,"saiteja",1);
        Library library = new Library("saiteja",1);
        library.setId(1);
        List<Library> libraryList = new ArrayList<>();
       when(libraryRepository.findAllByUsername("saiteja")).thenReturn(libraryList);
        libraryList.add(library);
        assertThrows(UserHasAlreadyBookException.class,()->libraryService.isUserAlreadyHasBook("saiteja",1));
    }
    @Test
     void isUserInLimitException() {
        Library library = new Library("saiteja",1);
        Library library1 = new Library("saiteja",2);
        Library library2 = new Library("saiteja",3);
        Library library3 = new Library("saiteja",4);
        Library library4 = new Library("saiteja",5);
        List<Library> libraryList = new ArrayList<>();
        libraryList.add(library);
        libraryList.add(library1);
        libraryList.add(library2);
        libraryList.add(library3);
        libraryList.add(library4);
        when(libraryRepository.findAllByUsername("saiteja")).thenReturn(libraryList);
        assertThrows(UserBookLimitReachedException.class,()->libraryService.isUserInLimit("saiteja"));
    }

}
