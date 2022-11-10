package com.sa22.trippy.user_management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/all_users")
    private @ResponseBody ResponseEntity<List<?>> getAllUsers() {
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }


    @GetMapping("/user_id/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable int userId) {
        User userRetrieved = userServiceImpl.findUserById(userId);
        return new ResponseEntity<User>(userRetrieved, HttpStatus.OK);
    }


    @GetMapping("/user_email/{userEmail}")
    public ResponseEntity<?> findUserByEmail(@PathVariable String userEmail) {
        User userRetrieved = userServiceImpl.findUserByEmail(userEmail);
        return new ResponseEntity<User>(userRetrieved, HttpStatus.OK);
    }


    @GetMapping("/user_name/{userName}")
    public ResponseEntity<?> findUserByUserName(@PathVariable String userName) {
        User userRetrieved = userServiceImpl.findUserByUserName(userName);
        return new ResponseEntity<User>(userRetrieved, HttpStatus.OK);
    }

    @PostMapping("/new_user")
    public ResponseEntity<?> userInsert(@RequestBody User user) {
        User userInserted = userServiceImpl.userInsert(user);
        return new ResponseEntity<User>(userInserted, HttpStatus.CREATED);

    }


}