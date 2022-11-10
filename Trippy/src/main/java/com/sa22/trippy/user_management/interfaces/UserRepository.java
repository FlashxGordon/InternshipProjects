package com.sa22.trippy.user_management.interfaces;

import com.sa22.trippy.user_management.User;
import java.util.List;

public interface UserRepository {

    List<User> findAllUsers();

    User findUserByEmail(String userEmail);

    User findUserByUserName(String userName);

    User findUserById(int userId);

    User userInsert(User user);
}

