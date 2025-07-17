package demo.com.security;

import demo.com.security.entity.Role;
import demo.com.security.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoSeeder
    implements CommandLineRunner {
    // Injetar o RoleRepository
    private final RoleRepository roleRepo;

    public MongoSeeder(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    // CommandLineRunner que insere as duas roles na collection caso não tenha nada inserido.
    @Override
    public void run(String... args) throws Exception {
        if (roleRepo.count() == 0) {
            roleRepo.saveAll(List.of(
                    new Role(Role.Values.ADMIN.getRoleId(), "ADMIN"),
                    new Role(Role.Values.BASIC.getRoleId(), "BASIC")
            ));
            System.out.println("[MongoSeeder] Roles iniciais inseridas.");
        }

        // Dessa forma, vai inserir ADMIN e BASIC em qualquer bds novo, mas não vai re-inserir em um bd antigo.
    }
}
