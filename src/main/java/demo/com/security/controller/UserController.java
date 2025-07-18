package demo.com.security.controller;

import demo.com.security.dto.CreateUserDto;
import demo.com.security.entity.User;
import demo.com.security.repository.UserRepository;
import demo.com.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> createNewUser(@RequestBody CreateUserDto dto) {
        var userId = userService.createNewUser(dto);
        var location = URI.create("/users/" + userId);
        return ResponseEntity.created(location).build();
    }
}
