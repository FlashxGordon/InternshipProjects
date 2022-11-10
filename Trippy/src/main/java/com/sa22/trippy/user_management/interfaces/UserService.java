package com.sa22.trippy.user_management.interfaces;

import com.sa22.trippy.user_management.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User userInsert(User user);

    User findUserById(int userId);

    User findUserByEmail(String userEmail);

    User findUserByUserName(String userName);


}
