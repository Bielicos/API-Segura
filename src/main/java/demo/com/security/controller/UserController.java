package demo.com.security.controller;

import demo.com.security.dto.CreateUserDto;
import demo.com.security.entity.User;
import demo.com.security.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createNewUser(@RequestBody CreateUserDto dto) {

    }
}
