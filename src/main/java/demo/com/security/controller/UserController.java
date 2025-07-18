package demo.com.security.controller;

import demo.com.security.dto.CreateUserDto;
import demo.com.security.entity.User;
import demo.com.security.repository.UserRepository;
import demo.com.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> createNewUser(@RequestBody CreateUserDto dto) {
        var userId = userService.createNewUser(dto);
        var location = URI.create("/users/" + userId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
