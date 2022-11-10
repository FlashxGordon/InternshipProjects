package com.sa22.trippy.user_management;

import com.sa22.trippy.exceptions.EmptyInputException;
import com.sa22.trippy.user_management.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepositoryImpl;

    public UserServiceImpl(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Override
    public User findUserById(int userId) {
        if (userRepositoryImpl.findUserById(userId) == null || userId < 0) {
            throw new NoSuchElementException();
        }
        return userRepositoryImpl.findUserById(userId);
    }


    @Override
    public User findUserByEmail(String userEmail) {
        if (userRepositoryImpl.findUserByEmail(userEmail) == null
        ||!userRepositoryImpl.findUserByEmail(userEmail).toString().contains("@")) {
            throw new NoSuchElementException();
        }
        return userRepositoryImpl.findUserByEmail(userEmail);
    }

    @Override
    public User findUserByUserName(String userName) {
        if (userRepositoryImpl.findUserByUserName(userName) == null) {
            throw new NoSuchElementException();
        }
        return userRepositoryImpl.findUserByUserName(userName);
    }


    @Override
    public User userInsert(User user) {
        if (user.getUserName().isEmpty() || (user.getUserName().length() == 0)
                || user.getUserEmail().isEmpty() || user.getUserCity().isEmpty()) {
            throw new EmptyInputException();
        }
        return userRepositoryImpl.userInsert(user);
    }

    public List<User> getAllUsers() {
        return userRepositoryImpl.findAllUsers();
    }

}