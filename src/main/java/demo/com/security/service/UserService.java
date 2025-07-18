package demo.com.security.service;

import demo.com.security.dto.CreateUserDto;
import demo.com.security.entity.Role;
import demo.com.security.entity.User;
import demo.com.security.repository.RoleRepository;
import demo.com.security.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class UserService {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,  RoleRepository roleRepository,   BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public String createNewUser(CreateUserDto dto) {
        var UserFromDb = userRepository.findByEmail(dto.email());
        var roleBasic = roleRepository.findByName(Role.Values.BASIC.name());

        if (UserFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setEmail(dto.email());
        user.setPassword(bCryptPasswordEncoder.encode(dto.password()));
        user.setName(dto.name());
        user.setRoles(Set.of(roleBasic));

        var newUser = userRepository.save(user);
        return newUser.getUserId();
    }
}
