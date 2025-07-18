package demo.com.security.config;

import demo.com.security.entity.Role;
import demo.com.security.entity.User;
import demo.com.security.repository.RoleRepository;
import demo.com.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.startup.UserConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public class AdminUserConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig (UserRepository userRepository,
                            BCryptPasswordEncoder passwordEncoder,
                            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Insere as Roles de Admin e User dentro do banco de dados.
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(
                    new Role(Role.Values.ADMIN.getRoleId(), "ADMIN"),
                    new Role(Role.Values.BASIC.getRoleId(), "BASIC")
            ));
            System.out.println("[RolesConfig] Roles iniciais inseridas.");
        }

        // Inserindo os administradores
        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        var userAdmin = userRepository.findByEmail("admin@email.com");

        if (userAdmin.isEmpty()) {
            var user = new User();
            user.setName("Administrator");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setEmail("admin@email.com");
            user.setRoles(Set.of(roleAdmin));
            userRepository.save(user);
            System.out.println(("[AdminUserConfig] Administrador inserido com sucesso!"));
        }
    }
}
