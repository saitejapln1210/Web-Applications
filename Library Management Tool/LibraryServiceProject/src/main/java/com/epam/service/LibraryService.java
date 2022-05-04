package com.epam.service;

import com.epam.dto.LibraryDto;
import com.epam.dto.UserProfile;
import com.epam.entity.Library;
import com.epam.exceptions.UserBookLimitReachedException;
import com.epam.exceptions.UserHasAlreadyBookException;

import java.util.List;
public interface LibraryService {
    LibraryDto issueBook(String username, int bookId) throws UserBookLimitReachedException, UserHasAlreadyBookException;

    LibraryDto getDtoFromLibrary(Library library);

    boolean isUserExist(String username);

    boolean isBookExist(int bookId);

    UserProfile getUserProfile(String username);

    List<Library> getUserBooks(String username);

    boolean isUserInLimit(String username) throws UserBookLimitReachedException;

    void deleteAllBook(String username);

    void deleteBook(String username, int bookId);

    boolean isUserAlreadyHasBook(String username, int bookId) throws UserHasAlreadyBookException;
}
